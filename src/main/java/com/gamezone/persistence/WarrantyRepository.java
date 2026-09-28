package com.gamezone.persistence;
 
import com.gamezone.model.BasicWarranty;
import com.gamezone.model.ExtendedWarranty;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Warranty;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
 
/*
 * Se encarga de guardar y cargar las garantías (Warranty) en el archivo
 * data/warranties.csv.
 *
 * Como Warranty es abstracta y tiene dos subclases concretas
 * (BasicWarranty y ExtendedWarranty) que solo difieren en su
 * comportamiento (no agregan atributos propios según el enunciado),
 * cada línea del archivo incluye un discriminador de tipo (BASIC o
 * EXTENDED) para saber qué subclase reconstruir al cargar.
 *
 * La fecha de fin de cada garantía no se guarda en el archivo, porque
 * el constructor de Warranty ya la recalcula automáticamente a partir
 * de la fecha de inicio y de la duración de cada tipo. Guardarla sería
 * duplicar un dato que siempre se puede derivar.
 *
 * Esta clase recibe SaleService y ProductService por constructor
 * únicamente para resolver las referencias a la venta y al producto
 * asociados de cada garantía al momento de cargar el archivo; nunca
 * para aplicar reglas de negocio. Es la única clase del módulo de
 * garantías autorizada para acceder al sistema de archivos.
 */
public class WarrantyRepository {
 
    private static final String DATA_DIRECTORY = "data";
    private static final String WARRANTIES_FILE = DATA_DIRECTORY + "/warranties.csv";
    private static final String SEPARATOR = ";";
 
    private static final String TYPE_BASIC = "BASIC";
    private static final String TYPE_EXTENDED = "EXTENDED";
 
    private final SaleService saleService;
    private final ProductService productService;
 
    /*
     * Crea el repositorio, guardando las dependencias necesarias para
     * resolver referencias a Sale y Product, y asegurando que exista el
     * directorio de datos.
     */
    public WarrantyRepository(SaleService saleService, ProductService productService) {
        this.saleService = saleService;
        this.productService = productService;
        try {
            Files.createDirectories(Paths.get(DATA_DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException("The data directory could not be created.", e);
        }
    }
    
    /*
     * Guarda la lista completa de garantías, sobrescribiendo el archivo
     * anterior. La escritura se hace de forma atómica: primero se
     * escribe a un archivo temporal, y solo si eso tiene éxito se mueve
     * para reemplazar el archivo real, evitando dejar un archivo
     * corrupto si el proceso se interrumpe.
     */
    public void saveAll(List<Warranty> warranties) {
        Path target = Paths.get(WARRANTIES_FILE);
        Path tempFile = Paths.get(WARRANTIES_FILE + ".tmp");
 
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new java.io.FileOutputStream(tempFile.toFile()), StandardCharsets.UTF_8))) {
            for (Warranty warranty : warranties) {
                writer.write(toCsvLine(warranty));
                writer.newLine();
            }
            writer.flush();
            Files.move(tempFile, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error saving warranties", e);
        }
    }
    
    /*
     * Carga la lista de garantías desde el archivo de datos,
     * reconstruyendo la subclase concreta correcta de cada línea según
     * su discriminador de tipo, y resolviendo la venta y el producto
     * asociados a través de los servicios inyectados. Si el archivo no
     * existe todavía, se devuelve una lista vacía.
     */
    public List<Warranty> loadAll() {
        List<Warranty> warranties = new ArrayList<>();
        Path path = Paths.get(WARRANTIES_FILE);
        if (!Files.exists(path)) {
            return warranties;
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new java.io.FileInputStream(WARRANTIES_FILE), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                warranties.add(fromCsvLine(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading warranties", e);
        }
        return warranties;
    }
    
        /*
     * Convierte una garantía en una línea CSV, precedida por el
     * discriminador de tipo que identifica su subclase concreta.
     */
    private String toCsvLine(Warranty warranty) {
        String type;
        if (warranty instanceof BasicWarranty) {
            type = TYPE_BASIC;
        } else if (warranty instanceof ExtendedWarranty) {
            type = TYPE_EXTENDED;
        } else {
            throw new IllegalArgumentException(
                    "Unknown warranty type: " + warranty.getClass().getSimpleName());
        }
 
        return String.join(SEPARATOR,
                type,
                warranty.getId(),
                warranty.getProduct().getId(),
                warranty.getSale().getId(),
                warranty.getStartDate().toString());
    }
    
    /*
     * Interpreta una línea CSV y reconstruye la subclase concreta
     * correcta de Warranty, resolviendo el producto y la venta
     * asociados a través de los servicios inyectados. La fecha de fin
     * se recalcula automáticamente dentro del constructor de Warranty.
     */
    private Warranty fromCsvLine(String line) {
        String[] fields = line.split(SEPARATOR, -1);
        String type = fields[0];
        String id = fields[1];
        String productId = fields[2];
        String saleId = fields[3];
        LocalDate startDate = LocalDate.parse(fields[4]);
 
        Product product = productService.findProductById(productId)
                .orElseThrow(() -> new RuntimeException(
                        "Product not found" + productId + " referenced in a stored guarantee"));
        Sale sale = saleService.findSaleById(saleId)
                .orElseThrow(() -> new RuntimeException(
                        "The sale was not found." + saleId + " referenced in a stored guarantee"));
 
        switch (type) {
            case TYPE_BASIC:
                return new BasicWarranty(id, product, sale, startDate);
            case TYPE_EXTENDED:
                return new ExtendedWarranty(id, product, sale, startDate);
            default:
                throw new IllegalArgumentException("Unknown guarantee type in the file: " + type);
        }
    }
}

