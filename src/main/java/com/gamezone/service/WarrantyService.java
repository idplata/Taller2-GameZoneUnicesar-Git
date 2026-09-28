package com.gamezone.service;
 
import com.gamezone.model.BasicWarranty;
import com.gamezone.model.ExtendedWarranty;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Warranty;
import com.gamezone.persistence.WarrantyRepository;
 
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
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
 */
public class WarrantyService {
 
    private final WarrantyRepository repository;
    private final List<Warranty> warranties;
 
    /*
     * Crea el servicio y carga las garantías previamente persistidas.
     */
    public WarrantyService(WarrantyRepository repository) {
        this.repository = repository;
        this.warranties = repository.loadAll();
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
                        && warranty.getSale().getId().equals(saleId))
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