package com.gamezone.model;
 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa la devolución de uno o más productos de una venta previa.
 * La devolución puede ser parcial: solo contiene los productos que el
 * cliente realmente devuelve, no necesariamente todos los de la venta.
 * <p>
 * Pertenece a la capa de modelo, por lo que no contiene lógica de acceso
 * a archivos.
 */
public class Return {
 
    private final String id;
    private final LocalDate date;
    private final Sale sale;
    private final List<Product> returnedProducts;
    private final String reason;
    private double refundAmount;
 
    /**
     * Crea una devolución.
     *
     * @param id               identificador único de la devolución
     * @param date             fecha en que se registra la devolución
     * @param sale             venta original a la que pertenecen los productos
     * @param returnedProducts productos devueltos (al menos uno)
     * @param reason           motivo de la devolución
     * @param refundAmount     monto reembolsado al cliente
     * @throws IllegalArgumentException si algún dato obligatorio falta o es inválido
     */
    public Return(String id, LocalDate date, Sale sale,
                  List<Product> returnedProducts, String reason, double refundAmount) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El identificador de la devolución es obligatorio.");
        }
        if (date == null) {
            throw new IllegalArgumentException("La fecha de la devolución es obligatoria.");
        }
        if (sale == null) {
            throw new IllegalArgumentException("La venta original es obligatoria.");
        }
        if (returnedProducts == null || returnedProducts.isEmpty()) {
            throw new IllegalArgumentException("Debe devolver al menos un producto.");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("El motivo de la devolución es obligatorio.");
        }
        if (refundAmount < 0) {
            throw new IllegalArgumentException("El monto reembolsado no puede ser negativo.");
        }
        this.id = id;
        this.date = date;
        this.sale = sale;
        this.returnedProducts = new ArrayList<>(returnedProducts);
        this.reason = reason;
        this.refundAmount = refundAmount;
    }
 
    /** @return el identificador de la devolución */
    public String getId() {
        return id;
    }
 
    /** @return la fecha en que se registró la devolución */
    public LocalDate getDate() {
        return date;
    }
 
    /** @return la venta original a la que hace referencia la devolución */
    public Sale getSale() {
        return sale;
    }
 
    /** @return una vista no modificable de los productos devueltos */
    public List<Product> getReturnedProducts() {
        return Collections.unmodifiableList(returnedProducts);
    }
 
    /** @return el motivo de la devolución */
    public String getReason() {
        return reason;
    }
 
    /** @return el monto reembolsado al cliente */
    public double getRefundAmount() {
        return refundAmount;
    }
 
    /**
     * Suma los precios de los productos devueltos, asigna el resultado al
     * monto reembolsado y lo retorna.
     *
     * @return el monto reembolsado calculado
     */
    public double calculateRefundAmount() {
        double total = 0;
        for (Product product : returnedProducts) {
            total += product.getPrice();
        }
        this.refundAmount = total;
        return total;
    }
 
    /**
     * Genera un comprobante formateado en español con el detalle de la
     * devolución.
     *
     * @return el texto del comprobante
     */
    public String generateReturnReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("===== COMPROBANTE DE DEVOLUCIÓN =====\n");
        receipt.append("Identificador: ").append(id).append("\n");
        receipt.append("Fecha: ").append(date).append("\n");
        receipt.append("Venta original: ").append(sale.getid()).append("\n");
        receipt.append("Productos devueltos:\n");
        for (Product product : returnedProducts) {
            receipt.append(String.format("  - %s ($%,.2f)%n", product.getTitle(), product.getPrice()));
        }
        receipt.append("Motivo: ").append(reason).append("\n");
        receipt.append(String.format("Monto reembolsado: $%,.2f%n", refundAmount));
        receipt.append("=====================================");
        return receipt.toString();
    }
}
 