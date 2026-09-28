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
}