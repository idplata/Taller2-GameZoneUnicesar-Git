# Accessory Module - Analysis (Requirement 1 & 5)

## 1. Purpose
Extend the GameZone catalog (video games and consoles) with accessories: **cables**, **controllers** and **memory cards**. An accessory can be listed, filtered by type, filtered by compatible console, and sold in the same sale as any other product.

## 2. Domain model
| Class | Role | Own attributes |
|---|---|---|
| `Accesory` (abstract) | Common behaviour of every accessory; extends `Product` | `compatibleConsoles` (ids of consoles) |
| `Cable` | Video/data cable | `length`, `connectorType` |
| `Controller` | Game controller | `connectionType` |
| `Memory` | Memory card / drive | `gigabytes`, `memoryType` |

**Why `Accesory` extends `Product`.** An accessory has an id, title, price and stock, exactly like a video game or a console. Extending `Product` lets a `Sale` hold accessories in the same list, reuses the existing getters, and avoids duplicating stock logic. The price of the extra fields is that code which needs the concrete type must check `instanceof Accesory` (done in `SaleService`).

**Why `Accesory` is abstract.** An accessory is always a cable, a controller or a memory; there is no generic accessory. The abstract method `getAccessoryType()` returns `"CABLE"`, `"CONTROLLER"` or `"MEMORY"` and each subclass overrides `getDescription()` to append its own attributes (`@Override`).

**Compatibility by id.** `compatibleConsoles` stores console ids (`List<String>`), not `Console` objects: the accessory does not need to load or keep consoles in memory, and the CSV stays flat.

## 3. Business rules
- Every accessory has a unique id, a price and an initial stock.
- Stock changes through `AccesoryService.updateStock(id, delta)`; a negative result is rejected with `IllegalStateException`.
- `findById` throws `NoSuchElementException` when the accessory does not exist, so the caller decides how to report it.
- Listings: all accessories, by type (case-insensitive), and those compatible with a given console id.
- Integration (Req 5): Returned accessories must restore stock through `AccesoryService.updateStock` (A4). Promotions by category can target `"ACCESSORY"` (A1).

## 4. Layers and responsibilities
| Layer | Class | Responsibility |
|---|---|---|
| ui | `main` (accessory submenu) | Reads data, calls the service, prints results. No business rules. |
| service | `AccesoryService` | Registration, queries, stock updates; persists after every change. Only class allowed to call the repository. |
| persistence | `AccesoryRepository` | Reads and writes `data/accessories.csv`. |
| model | `Accesory`, `Cable`, `Controller`, `Memory` | Data and behaviour, no file access. |

## 5. Persistence format
`data/accessories.csv`, `;`-separated, one accessory per line. The first field is a type discriminator (`CABLE`, `CONTROLLER`, `MEMORY`) that tells the loader which subclass to build; the compatible consoles are joined with `|`.
