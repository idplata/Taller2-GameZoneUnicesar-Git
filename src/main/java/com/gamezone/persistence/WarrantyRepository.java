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
            throw new RuntimeException("No se pudo crear el directorio de datos", e);
        }
    }
    
    
}
