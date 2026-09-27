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
}
