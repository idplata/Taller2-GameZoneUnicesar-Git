# Taller2-GameZoneUnicesar-Git
Taller 2 - Programación Orientada a Objetos - Programación III UPC

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
