package com.gamezone.persistence;

import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
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
 * Se encarga de guardar y cargar las devoluciones (Return) en el archivo
 * data/returns.csv.
 *
 * Return hace referencia a una venta (Sale) y a una lista de productos
 * (Product), así que para reconstruir un objeto Return al cargar el
 * archivo es necesario resolver esas referencias. Por eso este
 * repositorio recibe SaleService y ProductService por constructor: los
 * usa únicamente para buscar la venta y los productos por su
 * identificador durante la carga, nunca para aplicar reglas de negocio.
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
    
    
    /*
     * Crea el repositorio, guardando las dependencias necesarias para
     * resolver referencias a Sale y Product, y asegurando que exista el
     * directorio de datos.
     */
    public ReturnRepository(SaleService saleService, ProductService productService) {
        this.saleService = saleService;
        this.productService = productService;
        try {
            Files.createDirectories(Paths.get(DATA_DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio de datos", e);
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
            throw new RuntimeException("Error al guardar las devoluciones", e);
        }
    }
}
