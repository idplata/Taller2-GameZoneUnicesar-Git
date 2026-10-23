package com.gamezone.model;
 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Representa la devolución de uno o más productos comprados en una venta
 * anterior. La devolución puede ser parcial: solo incluye los productos que
 * el cliente devuelve, no necesariamente todos los de la venta.
 *
 * Return NO hereda de Sale: la devolución hace referencia a una venta ya
 * existente (asociación), pero tiene su propio ciclo de vida, su propio
 * identificador y su propia fecha. Por eso la venta original se guarda como
 * referencia y no se ofrece un setter para ella (relación inmutable).
 *
 * Esta clase no contiene lógica de acceso a archivos ni reglas que dependan
 * de otros módulos (plazo de 30 días, pertenencia, stock): eso corresponde a
 * ReturnService.
 */
public class Return {
 
    private final String id;
    private final LocalDate date;
    private final Sale sale;
    private final List<Product> returnedProducts;
    private final String reason;
    private double refundAmount;
 
    /*
     * Crea una devolución con todos sus datos. Valida que el identificador, la
     * fecha, la venta y los productos devueltos existan, y que la lista de
     * productos no esté vacía, porque una devolución sin productos no tiene
     * sentido. Los mensajes están en español porque pueden llegar al usuario.
     */
    public Return(String id, LocalDate date, Sale sale,
                  List<Product> returnedProducts, String reason, double refundAmount) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("The return identifier is mandatory.");
        }
        if (date == null) {
            throw new IllegalArgumentException("The return date is mandatory.");
        }
        if (sale == null) {
            throw new IllegalArgumentException("The original sale is mandatory.");
        }
        if (returnedProducts == null || returnedProducts.isEmpty()) {
            throw new IllegalArgumentException("You must return at least one product.");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("The reason for the return is mandatory..");
        }
        if (refundAmount < 0) {
            throw new IllegalArgumentException("The reimbursed amount cannot be negative..");
        }
        this.id = id;
        this.date = date;
        this.sale = sale;
        this.returnedProducts = new ArrayList<>(returnedProducts);
        this.reason = reason;
        this.refundAmount = refundAmount;
    }
 
    
    public String getId() {
        return id;
    }
 
    public LocalDate getDate() {
        return date;
    }
 
    public Sale getSale() {
        return sale;
    }
 
    public List<Product> getReturnedProducts() {
        return Collections.unmodifiableList(returnedProducts);
    }
 
    public String getReason() {
        return reason;
    }
 
    public double getRefundAmount() {
        return refundAmount;
    }
 
    /*
     * Calcula el monto reembolsado sumando los precios de los productos
     * devueltos, lo asigna al atributo refundAmount y retorna el valor
     * calculado.
     */
    public double calculateRefundAmount() {
        double total = 0;
        for (Product product : returnedProducts) {
            total += product.getPrice();
        }
        this.refundAmount = total;
        return total;
    }
 
    /*
     * Genera el recibo de la devolución como texto en español: identificador,
     * fecha, referencia a la venta original, productos devueltos con su
     * precio, motivo y monto reembolsado.
     */
    public String generateReturnReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("===== RETURN RECEIPT =====\n");
        receipt.append("Id: ").append(id).append("\n");
        receipt.append("Date: ").append(date).append("\n");
        receipt.append("Original Sale: ").append(sale.getid()).append("\n");
        receipt.append("Returned products:\n");
        for (Product product : returnedProducts) {
            receipt.append(String.format("  - %s ($%,.2f)%n", product.getTitle(), product.getPrice()));
        }
        receipt.append("Reason: ").append(reason).append("\n");
        receipt.append(String.format("Reimbursed amount: $%,.2f%n", refundAmount));
        receipt.append("=====================================");
        return receipt.toString();
    }
}
 