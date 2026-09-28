package com.gamezone.persistence;
 
import java.time.LocalDate;
 
/*
 * [A2] Registro plano de una garantía tal como se guarda en
 * data/warranties.csv: contiene únicamente valores simples (tipo,
 * identificador, id del producto, id de la venta y fecha de inicio),
 * sin referencias a objetos Sale ni Product.
 *
 * Existe para romper la dependencia circular
 * SaleService -> WarrantyService -> WarrantyRepository -> SaleService:
 * el repositorio ya no necesita resolver ventas ni productos al cargar,
 * solo devuelve estos registros, y es WarrantyService quien los
 * convierte en objetos Warranty completos.
 */
public class WarrantyRecord {
 
    public static final String TYPE_BASIC = "BASIC";
    public static final String TYPE_EXTENDED = "EXTENDED";
 
    private String type;
    private String id;
    private String productId;
    private String saleId;
    private LocalDate startDate;
    
    /* Crea un registro con los valores leídos de una línea del archivo. */
    public WarrantyRecord(String type, String id, String productId, String saleId, LocalDate startDate) {
        this.type = type;
        this.id = id;
        this.productId = productId;
        this.saleId = saleId;
        this.startDate = startDate;
    }
    
    /* Retorna el discriminador de tipo (BASIC o EXTENDED). */
    public String getType() {
        return type;
    }
 
    /* Retorna el identificador de la garantía. */
    public String getId() {
        return id;
    }
 
    /* Retorna el identificador del producto cubierto. */
    public String getProductId() {
        return productId;
    }
 
    /* Retorna el identificador de la venta asociada. */
    public String getSaleId() {
        return saleId;
    }
 
    /* Retorna la fecha de inicio de la garantía. */
    public LocalDate getStartDate() {
        return startDate;
    }
}
