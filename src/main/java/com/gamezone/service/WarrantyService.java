package com.gamezone.service;

import com.gamezone.model.BasicWarranty;
import com.gamezone.model.ExtendedWarranty;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Warranty;
import com.gamezone.persistence.SaleRepository;
import com.gamezone.persistence.WarrantyRecord;
import com.gamezone.persistence.WarrantyRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * Contiene las reglas de negocio del módulo de garantías: asignar
 * garantías básicas o extendidas a un producto de una venta, consultar
 * la garantía de un producto específico, listar todas las garantías o
 * solo las vigentes, y listar las garantías próximas a vencer.
 *
 * Esta es la única clase del módulo autorizada para invocar a
 * WarrantyRepository. La decisión de CUÁNDO se debe asignar una
 * garantía (por ejemplo, solo si el producto es una consola, o solo si
 * el vendedor solicitó garantía extendida) no se toma aquí, sino en
 * SaleService.registerSale (capa de integración, a cargo del Líder
 * Técnico); este servicio solo sabe cómo crear y persistir una garantía
 * una vez que ya se decidió asignarla.
 *
 * [A2] Tras la corrección de la dependencia circular, este servicio es
 * el encargado de resolver las referencias a Sale y Product a partir de
 * los identificadores que devuelve WarrantyRepository en forma de
 * WarrantyRecord. Por eso recibe SaleRepository y ProductService por
 * constructor: el repositorio ya no depende de ellos, y el ciclo
 * SaleService -> WarrantyService -> WarrantyRepository -> SaleService
 * queda roto.
 */
public class WarrantyService {

    private final WarrantyRepository repository;
    private final List<Warranty> warranties;

    /*
     * [A2] Crea el servicio, carga los registros planos del repositorio
     * y los convierte en objetos Warranty resolviendo la venta y el
     * producto a partir de sus identificadores.
     */
    public WarrantyService(WarrantyRepository repository,
                           SaleRepository saleRepository,
                           ProductService productService) {
        this.repository = repository;
        this.warranties = new ArrayList<>();
        loadWarranties(saleRepository, productService);
    }

    /*
     * Crea una garantía básica para el producto y la venta indicados,
     * la persiste inmediatamente y la retorna. Se usa cuando una venta
     * incluye una consola, sin costo adicional para el cliente.
     */
    public BasicWarranty assignBasicWarranty(Product product, Sale sale, LocalDate startDate) {
        BasicWarranty warranty = new BasicWarranty(generateWarrantyId(), product, sale, startDate);
        addAndSave(warranty);
        return warranty;
    }

    /*
     * Crea una garantía extendida para el producto y la venta
     * indicados, la persiste inmediatamente y la retorna. Se usa cuando
     * el vendedor decide ofrecerla opcionalmente; el costo adicional
     * (10% del precio del producto) lo calcula la propia garantía a
     * través de getAdditionalCost(), y es responsabilidad de quien
     * invoque este método sumarlo al total de la venta.
     */
    public ExtendedWarranty assignExtendedWarranty(Product product, Sale sale, LocalDate startDate) {
        ExtendedWarranty warranty = new ExtendedWarranty(generateWarrantyId(), product, sale, startDate);
        addAndSave(warranty);
        return warranty;
    }

    /*
     * Busca la garantía asociada a un producto específico dentro de una
     * venta específica. Retorna null si no existe ninguna garantía
     * registrada para esa combinación.
     */
    public Warranty findWarrantyByProduct(String productId, String saleId) {
        return warranties.stream()
                .filter(warranty -> warranty.getProduct().getId().equals(productId)
                        && warranty.getSale().getid().equals(saleId))
                .findFirst()
                .orElse(null);
    }

    /*
     * Retorna todas las garantías registradas en el sistema.
     */
    public List<Warranty> listAllWarranties() {
        return Collections.unmodifiableList(warranties);
    }

    /*
     * Retorna las garantías que están vigentes hoy (la fecha actual
     * cae entre la fecha de inicio y la fecha de fin de la garantía).
     */
    public List<Warranty> listActiveWarranties() {
        LocalDate today = LocalDate.now();
        return warranties.stream()
                .filter(warranty -> warranty.isActive(today))
                .collect(Collectors.toList());
    }

    /*
     * Retorna las garantías cuya fecha de fin está dentro de los
     * próximos "daysAhead" días a partir de hoy. Se consideran solo las
     * garantías que todavía no han vencido (fecha de fin no anterior a
     * hoy), para no incluir garantías que ya expiraron.
     */
    public List<Warranty> listWarrantiesExpiringSoon(int daysAhead) {
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(daysAhead);

        return warranties.stream()
                .filter(warranty -> !warranty.getEndDate().isBefore(today)
                        && !warranty.getEndDate().isAfter(limit))
                .collect(Collectors.toList());
    }

    /*
     * [A2] Convierte cada WarrantyRecord en la subclase concreta de
     * Warranty, buscando la venta en SaleRepository y el producto en
     * ProductService a partir de sus identificadores. Si la venta o el
     * producto referenciados ya no existen, se lanza una excepción para
     * detectar temprano archivos corruptos o inconsistentes.
     */
    private void loadWarranties(SaleRepository saleRepository, ProductService productService) {
        Map<String, Sale> salesById = new HashMap<>();
        for (Sale sale : saleRepository.findAllSales()) {
            salesById.put(sale.getid(), sale);
        }

        for (WarrantyRecord record : repository.loadAll()) {
            Sale sale = salesById.get(record.getSaleId());
            if (sale == null) {
                throw new RuntimeException("The sale was not found " + record.getSaleId()
                        + " referenced in the warranty " + record.getId());
            }

            Product product = productService.findById(record.getProductId());
            if (product == null) {
                throw new RuntimeException("Product not found " + record.getProductId()
                        + " referenced in the warranty " + record.getId());
            }

            if (WarrantyRecord.TYPE_BASIC.equals(record.getType())) {
                warranties.add(new BasicWarranty(record.getId(), product, sale, record.getStartDate()));
            } else if (WarrantyRecord.TYPE_EXTENDED.equals(record.getType())) {
                warranties.add(new ExtendedWarranty(record.getId(), product, sale, record.getStartDate()));
            } else {
                throw new IllegalArgumentException(
                        "Unknown guarantee type in the file: " + record.getType());
            }
        }
    }

    /*
     * Agrega una garantía a la lista en memoria y persiste
     * inmediatamente la lista actualizada.
     */
    private void addAndSave(Warranty warranty) {
        warranties.add(warranty);
        repository.saveAll(warranties);
    }

    /*
     * Genera un identificador simple y secuencial para una nueva
     * garantía, basado en la cantidad de garantías ya registradas.
     */
    private String generateWarrantyId() {
        return "W" + String.format("%03d", warranties.size() + 1);
    }
}