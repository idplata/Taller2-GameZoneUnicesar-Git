# Taller2-GameZoneUnicesar-Git
Taller 2 - Programación Orientada a Objetos - Programación III UPC
# ANGEL MIGUEL PIRELA BUENO
# IRINA DANAETH PLATA SUÁREZ


Sistema de información por consola para **GameZone Unicesar**, una tienda de videojuegos, consolas y accesorios. Gestiona clientes, vendedores, inventario, ventas, promociones, devoluciones y garantías.

## Tecnologías

- Java (`maven.compiler.release` configurado en el `pom.xml`)
- Maven
- Persistencia en archivos (`.csv` y `.dat`), sin base de datos

## Arquitectura en cuatro capas

La dependencia siempre va en un solo sentido: `ui → service → persistence → model`.

| Capa | Paquete | Responsabilidad |
|---|---|---|
| Interfaz | `com.gamezone.ui` | Menú de consola. No contiene reglas de negocio. |
| Servicio | `com.gamezone.service` | Reglas de negocio y coordinación entre módulos. |
| Persistencia | `com.gamezone.persistence` | Lectura y escritura de archivos. |
| Modelo | `com.gamezone.model` | Entidades del dominio, sin acceso a archivos. |

## Módulos

### Personas
- Registro y listado de clientes y vendedores.

### Productos
- Videojuegos y consolas, con control de stock.

### Accesorios
- Cables, controles y memorias.
- Listado general, por tipo y los compatibles con una consola.

### Ventas
- Registro de una venta con cliente, vendedor y productos o accesorios; valida el stock disponible.
- Aplica automáticamente la mejor promoción vigente.
- Genera la garantía básica de cada consola y permite agregar garantía extendida.
- Recibo con subtotal, descuento, costo de la garantía extendida y total final.
- Historial por cliente, historial por vendedor, listado de ventas y detalle de una venta.
- Los ingresos y los historiales usan el total final, con el descuento ya restado.

### Promociones
- **Tipos:**
  - *Porcentaje:* descuento porcentual sobre el total de la venta.
  - *Categoría:* descuento porcentual solo sobre los productos de una categoría (`VIDEOGAME` o `CONSOLE`).
  - *Compra al por mayor:* descuento porcentual sobre el total cuando la venta incluye una cantidad mínima de productos.
- **Vigencia:** cada promoción tiene fecha de inicio y de fin. Solo se aplican las vigentes en la fecha de la venta.
- **Selección:** se calcula el descuento de cada promoción vigente y se aplica solo la que otorga el mayor descuento en dinero. Las promociones no son acumulables.
- **Diseño:** `Promotion` es una clase abstracta con el método `calculateDiscount(Sale)`; cada subclase lo implementa con su propia regla. La selección de la mejor promoción vive en `PromotionService`.
- **Consultas:** todas las promociones y las vigentes en la fecha actual.

### Devoluciones
- Un cliente puede devolver uno o más productos de una venta, sin que la devolución tenga que ser total.
- **Plazo:** hasta 30 días calendario después de la fecha de la venta.
- **Pertenencia:** los productos devueltos deben pertenecer a la venta indicada.
- **Inventario:** el stock de los productos devueltos se incrementa automáticamente.
- **Reembolso:** suma de los precios de los productos devueltos.
- **Consultas:** todas las devoluciones, por cliente y por venta.
- **Balance mensual:** ventas del mes, devoluciones del mes y balance neto para el mes y año indicados.

### Garantías
- **Garantía básica:** se genera automáticamente al vender una consola. Cubre defectos de fábrica durante 6 meses y no tiene costo adicional. Los videojuegos no incluyen garantía.
- **Garantía extendida (opcional):** el vendedor puede agregarla a cada consola al registrar la venta. Cubre defectos de fábrica y daños accidentales durante 12 meses y cuesta el 10% del precio del producto, que se suma al total de la venta.
- **Vigencia:** la fecha de fin se calcula automáticamente según el tipo de garantía.
- **Consultas:** garantía de un producto dentro de una venta, todas las garantías, las vigentes hoy y las próximas a vencer (indicando los días de anticipación).

## Menú principal

| Opción | Función |
|---|---|
| 1 | Registrar cliente |
| 2 | Listar clientes |
| 3 | Listar vendedores |
| 4 | Registrar videojuego |
| 5 | Registrar consola |
| 6 | Listar productos |
| 7 | Menú de accesorios |
| 8 | Registrar venta |
| 9 | Ver todas las ventas |
| 10 | Historial de un cliente |
| 11 | Historial de un vendedor |
| 12 | Detalle de una venta |
| 13 | Gestión de promociones |
| 14 | Gestión de devoluciones |
| 15 | Balance mensual |
| 16 | Gestión de garantías |
| 0 | Salir |

## Archivos de datos

Se guardan en la carpeta `data/`:

| Archivo | Contenido |
|---|---|
| `customers.csv` / `sellers.csv` | Clientes y vendedores |
| `products.dat` | Videojuegos y consolas |
| `accessories.csv` | Accesorios |
| `sales.dat` | Ventas |
| `promotions.csv` | Promociones |
| `returns.csv` | Devoluciones |
| `warranties.csv` | Garantías |

Los archivos de devoluciones y garantías se crean al registrar la primera operación.

## Cómo ejecutar

1. Abrir el proyecto en NetBeans, o compilar con `mvn clean compile` si Maven está instalado.
2. Ejecutar la clase `com.gamezone.ui.main`.

## Flujo de trabajo con Git

Se usa Git Flow:

- Cada requerimiento se desarrolla en una rama `feature/...` creada desde `develop` (por ejemplo `feature/promotion-module`, `feature/return-module`, `feature/warranty-module`).
- Commits atómicos con mensajes en inglés, siguiendo Conventional Commits (`feat:`, `fix:`, `docs:`, `chore:`).
- Cada commit se sube de inmediato con `git push`.
- Los cambios llegan a `develop` mediante Pull Request revisado; no se hacen commits directos a `main` ni a `develop`.
- Al fusionar, la rama feature se elimina del repositorio remoto.

## Convenciones de código

- Identificadores, comentarios y JavaDoc en inglés.
- Mensajes visibles al usuario en el menú de consola, en español.
- Atributos privados, con acceso por getters y setters.
- Sin lógica de acceso a archivos en las clases del modelo.

## Documentación

En la carpeta `docs/`:

- `class-diagram.md`, `hierarchy-diagram.md` y `layer-diagram.md`: diagramas del sistema.
- `ai-usage/`: registro del uso de IA.
- Análisis y diagramas de clases de cada requerimiento (`*-analysis.md` y `*-class-diagram.md`), a medida que se entregan.
- **Consultas:**
  - Listado de todas las promociones registradas.
  - Listado de las promociones vigentes hoy.
  - Detalle de una venta con su descuento.
- **Menú:**
  - Opción 12: Ver detalle de una venta.
  - Opción 13: Gestión de promociones (registrar los tres tipos, listar todas y listar vigentes).
- **Persistencia:** `data/promotions.csv`.
- **Reportes:** los ingresos y los historiales de cliente y vendedor usan el total final, con el descuento ya restado.
