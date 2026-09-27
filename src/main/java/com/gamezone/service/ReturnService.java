package com.gamezone.service;

import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.persistence.ReturnRepository;
 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
 
/*
 * Contiene las reglas de negocio del módulo de devoluciones: registrar
 * una nueva devolución (con sus validaciones de plazo y pertenencia),
 * consultar devoluciones por distintos criterios, y generar el balance
 * mensual combinando información de ventas y devoluciones.
 *
 * Esta es la única clase del módulo autorizada para invocar a
 * ReturnRepository. Depende también de SaleService (para validar la
 * venta original y calcular el total de ventas del balance) y de
 * ProductService (para incrementar el stock de los productos
 * devueltos), respetando el sentido de dependencias de la arquitectura
 * en capas.
 */
public class ReturnService {
    
    private static final int RETURN_WINDOW_DAYS = 30;
 
    private final ReturnRepository repository;
    private final SaleService saleService;
    private final ProductService productService;
    private final List<Return> returns;
 
    /*
     * Crea el servicio, inyectando sus dependencias y cargando las
     * devoluciones previamente persistidas.
     */
    public ReturnService(ReturnRepository repository, SaleService saleService, ProductService productService) {
        this.repository = repository;
        this.saleService = saleService;
        this.productService = productService;
        this.returns = repository.loadAll();
    }
    
        /*
     * Registra una nueva devolución sobre la venta indicada.
     *
     * Antes de crear la devolución se aplican tres validaciones, en este
     * orden:
     *   1. La venta original debe existir.
     *   2. La venta debe seguir dentro del plazo de 30 días para
     *      devoluciones (se reutiliza Sale.canBeReturned(), en lugar de
     *      calcular la diferencia de fechas aquí de nuevo).
     *   3. Cada producto que se quiere devolver debe pertenecer
     *      efectivamente a esa venta.
     *
     * Si alguna validación falla, se lanza IllegalArgumentException con
     * un mensaje claro en español, ya que ese mensaje puede llegar a
     * mostrarse directamente al usuario final en el menú de consola.
     *
     * Si todas las validaciones pasan: se crea la devolución con la
     * fecha actual, se calcula el monto reembolsado, se incrementa el
     * stock de cada producto devuelto invocando
     * ProductService.restoreStock (reutilizando la lógica existente en
     * lugar de duplicarla), se persiste la lista actualizada de
     * devoluciones, y se retorna la devolución creada.
     */
    public Return registerReturn(String saleId, List<String> productIds, String reason) {
        Sale sale = saleService.findSaleById(saleId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "There is no sale with the identifier " + saleId));
 
        if (!sale.canBeReturned()) {
            throw new IllegalArgumentException(
                    "the sale " + saleId + " The deadline has already passed." + RETURN_WINDOW_DAYS
                            + " days to register returns");
        }
 
        List<Product> returnedProducts = new ArrayList<>();
        for (String productId : productIds) {
            Product product = findProductInSale(sale, productId);
            returnedProducts.add(product);
        }
 
        String returnId = generateReturnId();
        Return returnItem = new Return(returnId, LocalDate.now(), sale, returnedProducts, reason, 0.0);
        returnItem.calculateRefundAmount();
 
        for (Product product : returnedProducts) {
            productService.restoreStock(product.getId(), 1);
        }
 
        returns.add(returnItem);
        repository.saveAll(returns);
 
        return returnItem;
    }
    
    /*
     * Retorna todas las devoluciones registradas en el sistema.
     */
    public List<Return> viewAllReturns() {
        return Collections.unmodifiableList(returns);
    }
 
    /*
     * Filtra las devoluciones cuya venta asociada pertenece al cliente
     * indicado.
     */
    public List<Return> viewReturnsByCustomer(String customerId) {
        return returns.stream()
                .filter(returnItem -> returnItem.getSale().getCustomer().getId().equals(customerId))
                .collect(Collectors.toList());
    }
    /*
     * Filtra las devoluciones asociadas a una venta específica.
     */
    public List<Return> viewReturnsBySale(String saleId) {
        return returns.stream()
                .filter(returnItem -> returnItem.getSale().getId().equals(saleId))
                .collect(Collectors.toList());
    }
}
