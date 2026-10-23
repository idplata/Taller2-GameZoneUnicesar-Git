package com.gamezone.persistence;

import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.service.AccesoryService;
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
import java.util.NoSuchElementException;

/*
 * Se encarga de guardar y cargar las devoluciones (Return) en el archivo
 * data/returns.csv.
 *
 * Return hace referencia a una venta (Sale) y a una lista de ítems
 * devueltos (Product, que incluye tanto productos regulares como
 * accesorios), así que para reconstruir un objeto Return al cargar el
 * archivo es necesario resolver esas referencias. Por eso este
 * repositorio recibe SaleService, ProductService y AccesoryService por
 * constructor: los usa únicamente para buscar la venta y los ítems por
 * su identificador durante la carga, nunca para aplicar reglas de
 * negocio.
 *
 * El AccesoryService se agregó como parte del ajuste A4 (devolución de
 * accesorios): un id guardado en la columna de ítems puede corresponder
 * a un accesorio, y debe resolverse contra el catálogo de accesorios.
 *
 * Esta es la única clase del módulo de devoluciones autorizada para
 * acceder al sistema de archivos; todo acceso debe pasar por
 * ReturnService.
 */
public class ReturnRepository {

    private static final String DATA_DIRECTORY = "data";
    private static final String RETURNS_FILE = DATA_DIRECTORY + "/returns.csv";
    private static final String FIELD_SEPARATOR = ";";
    private static final String PRODUCT_ID_SEPARATOR = ",";

    private final SaleService saleService;
    private final ProductService productService;
    private final AccesoryService accesoryService;   // A4: para resolver accesorios al cargar

    /*
     * Crea el repositorio, guardando las dependencias necesarias para
     * resolver referencias a Sale, Product y Accesory, y asegurando que
     * exista el directorio de datos.
     */
    public ReturnRepository(SaleService saleService,
                            ProductService productService,
                            AccesoryService accesoryService) {
        this.saleService = saleService;
        this.productService = productService;
        this.accesoryService = accesoryService;
        try {
            Files.createDirectories(Paths.get(DATA_DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException("Could not create the data directory.", e);
        }
    }

    /*
     * Guarda la lista completa de devoluciones, sobrescribiendo el
     * archivo anterior. La escritura se hace de forma atómica: primero
     * se escribe a un archivo temporal, y solo si eso tiene éxito se
     * mueve para reemplazar el archivo real, evitando dejar un archivo
     * corrupto si el proceso se interrumpe.
     */
    public void saveAll(List<Return> returns) {
        Path target = Paths.get(RETURNS_FILE);
        Path tempFile = Paths.get(RETURNS_FILE + ".tmp");

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new java.io.FileOutputStream(tempFile.toFile()), StandardCharsets.UTF_8))) {
            for (Return returnItem : returns) {
                writer.write(toCsvLine(returnItem));
                writer.newLine();
            }
            writer.flush();
            Files.move(tempFile, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error saving returns", e);
        }
    }

    /*
     * Carga la lista de devoluciones desde el archivo de datos,
     * resolviendo la venta original y los ítems devueltos (productos o
     * accesorios) a través de SaleService, ProductService y
     * AccesoryService. Si el archivo no existe todavía, se devuelve una
     * lista vacía.
     */
    public List<Return> loadAll() {
        List<Return> returns = new ArrayList<>();
        Path path = Paths.get(RETURNS_FILE);
        if (!Files.exists(path)) {
            return returns;
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new java.io.FileInputStream(RETURNS_FILE), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                returns.add(fromCsvLine(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading returns", e);
        }
        return returns;
    }

    /*
     * Convierte una devolución en una línea CSV. Como una devolución
     * puede incluir varios ítems (productos o accesorios), sus
     * identificadores se guardan separados por coma dentro del mismo
     * campo.
     */
    private String toCsvLine(Return returnItem) {
        StringBuilder productIds = new StringBuilder();
        for (Product product : returnItem.getReturnedProducts()) {
            if (productIds.length() > 0) {
                productIds.append(PRODUCT_ID_SEPARATOR);
            }
            productIds.append(product.getId());
        }

        return String.join(FIELD_SEPARATOR,
                returnItem.getId(),
                returnItem.getDate().toString(),
                returnItem.getSale().getid(),
                productIds.toString(),
                returnItem.getReason(),
                String.valueOf(returnItem.getRefundAmount()));
    }

    /*
     * Interpreta una línea CSV y reconstruye el objeto Return
     * correspondiente, buscando la venta original y cada ítem devuelto
     * (producto regular o accesorio) a través de los servicios
     * inyectados.
     */
    private Return fromCsvLine(String line) {
        String[] fields = line.split(FIELD_SEPARATOR, -1);
        String id = fields[0];
        LocalDate date = LocalDate.parse(fields[1]);
        String saleId = fields[2];
        String[] productIds = fields[3].split(PRODUCT_ID_SEPARATOR, -1);
        String reason = fields[4];
        double refundAmount = Double.parseDouble(fields[5]);

        Sale sale = saleService.findSaleById(saleId)
                .orElseThrow(() -> new RuntimeException(
                        "Sale not found: " + saleId + " referenced in a saved return"));

        List<Product> returnedProducts = new ArrayList<>();
        for (String productId : productIds) {
            Product resolved = resolveItem(productId);
            if (resolved != null) {
                returnedProducts.add(resolved);
            }
        }

        return new Return(id, date, sale, returnedProducts, reason, refundAmount);
    }

    /*
     * Resuelve un id guardado como producto regular o como accesorio.
     * Es la contraparte en persistencia de la restauración polimórfica
     * que hace ReturnService: como ambas jerarquías comparten Product
     * como raíz, un ítem devuelto puede venir de cualquiera de los dos
     * catálogos.
     */
    private Product resolveItem(String itemId) {
        Product product = productService.findProductById(itemId);
        if (product != null) {
            return product;
        }
        try {
            return accesoryService.findById(itemId);
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}