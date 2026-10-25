# Taller2-GameZoneUnicesar-Git
Taller 2 - Programación Orientada a Objetos - Programación III UPC

## Módulo de devoluciones

- **Registrar devolución:** el cliente devuelve uno o más productos de una venta, dentro de los 30 días calendario posteriores a la venta. El sistema valida que la venta exista y que los productos pertenezcan a ella.
- **Inventario:** al registrar la devolución, el stock de los productos devueltos se incrementa automáticamente.
- **Reembolso:** el monto se calcula como la suma de los precios de los productos devueltos.
- **Consultas:** todas las devoluciones, por cliente y por venta.
- **Balance mensual:** ventas del mes, devoluciones del mes y balance neto, para el mes y año indicados.
- **Persistencia:** `data/returns.csv`.
- **Menú:** opción 14 (Gestión de devoluciones) y opción 15 (Balance mensual).
## Módulo de promociones

- **Tipos de promoción:**
  - *Porcentaje:* descuento porcentual sobre el total de la venta.
  - *Categoría:* descuento porcentual solo sobre los productos de una categoría (`VIDEOGAME` o `CONSOLE`).
  - *Compra al por mayor:* descuento porcentual sobre el total cuando la venta incluye una cantidad mínima de productos.
- **Vigencia:** cada promoción tiene fecha de inicio y de fin. Solo se aplican las vigentes en la fecha de la venta; las vencidas o futuras quedan registradas pero no se aplican.
- **Aplicación automática:** al registrar una venta, el sistema calcula el descuento de cada promoción vigente y aplica solo la que otorga el mayor descuento en dinero. Las promociones no son acumulables. Si ninguna aplica, la venta se cobra sin descuento.
- **Recibo:** muestra el subtotal, el descuento aplicado (con el nombre de la promoción) y el total final.
- **Consultas:**
  - Listado de todas las promociones registradas.
  - Listado de las promociones vigentes hoy.
  - Detalle de una venta con su descuento.
- **Menú:**
  - Opción 12: Ver detalle de una venta.
  - Opción 13: Gestión de promociones (registrar los tres tipos, listar todas y listar vigentes).
- **Persistencia:** `data/promotions.csv`.
- **Reportes:** los ingresos y los historiales de cliente y vendedor usan el total final, con el descuento ya restado.
