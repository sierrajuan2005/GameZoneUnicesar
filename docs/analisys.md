# Guiding Questions for the Analysis

## 1. What attributes are common to all people who interact with the store, and which ones are specific to each type of person? How is this distinction reflected in a class hierarchy?

All people who interact with the store share attributes such as name, identification number, and phone number. Depending on their role, they have specific attributes. For example, a customer has attributes such as email and purchase history, while a seller has an employee code and an assigned work shift. For the class hierarchy, the main class is **Person**, from which **Customer** and **Seller** are derived.

## 2. Should there be a class that represents a "generic person" without specifying their role? Why or why not? What implication does this decision have regarding the possibility of instantiating that class?

**Person** should be an abstract class because we do not need to create people without a specific role. Each person in the system must be either a **Customer** or a **Seller**. Therefore, the **Person** class cannot be instantiated directly, which prevents creating a generic person. However, we can create instances of concrete classes such as **Customer** or **Seller**.

## 3. What characteristics are common to all products sold by the store, regardless of their type? What characteristics are specific to each type of product?

The common attributes of all products are the identification number, title, price, and quantity available in the inventory. Each type of product also has its own specific attributes. Video games have a platform, genre, and age rating. Consoles have a brand, model, and generation.

## 4. Each type of product must be able to provide a description that includes its particular characteristics. How should this behavior be declared in the base class to guarantee that all subclasses implement it in their own way? What object-oriented programming mechanism allows this?

The **Product** class should have a method for the description. By using **polymorphism**, we can call the description method, and depending on the type of product, it will display the appropriate information. This method can be declared as an **abstract method** in the base class so that each subclass implements it in its own way.

## 5. A sale involves a customer, a seller, and one or more products. What type of relationships exist between the class that represents the sale and the other classes in the system? Are these relationships inheritance, association, composition, or another type? Justify your answer.

A **Sale** has association relationships with **Customer**, **Seller**, and **Product**. They are not inheritance relationships because a Sale is not a type of Customer, Seller, or Product. Instead, it is related to them to represent a transaction. It is also not considered composition because customers, sellers, and products exist independently of a sale and can participate in other sales.

## 6. Should the sale be responsible for calculating its own total, or should this responsibility belong to another class? Explain your decision.

The **Sale** class should be responsible for calculating the total value because it represents the transaction and contains the products purchased. The total is calculated by adding the prices of the products included in the sale.

## 7. How can the design guarantee that a sale cannot be registered without at least one product? At what point in the system should this rule be validated?

To guarantee that a sale cannot be registered without products, we must validate that there is at least one product before completing the sale. This rule should be validated in the **service layer**.

## 8. How is the automatic inventory update reflected in the design when a sale is registered? Which classes are involved in this operation?

When a sale is registered, we verify that the requested product exists in the inventory. If it exists, its quantity must be automatically reduced from the inventory when the sale is registered.

The classes involved in this operation are **Sale**, **Product**, the **Sale Service**, and the **Persistence** layer, since after making the changes, the updated information must be saved.

## 9. The system must be organized into four layers: model, persistence, services, and user interface. What type of classes belong to each layer? What criterion is used to decide which layer a class should belong to?

The **Model** layer contains the entities and their business-related behaviors. The **Persistence** layer contains the classes responsible for saving and retrieving files or information. The **Service** layer contains the business logic, business rules, and system operations. Finally, the **User Interface** layer contains the classes responsible for interacting with the user.

## 10. Why should the logic for saving and retrieving data from files not be inside the domain classes? What problems are created when these responsibilities are mixed?

Domain classes should not be responsible for saving or retrieving information because their responsibility is to represent entities and their own business behaviors. If these responsibilities are mixed, greater coupling is created, making the system more difficult to maintain and modify.

## 11. What dependencies are allowed between the layers, and which ones are prohibited? Justify the purpose of the allowed dependencies.

## 




The allowed dependencies are:

```text
User Interface → Services

Services → Model

Services → Persistence

Persistence → Model
```

These dependencies allow each layer to communicate with the layer it needs while keeping responsibilities separated. This helps reduce coupling and makes the system easier to maintain, modify, and expand.


# Integration Analysis — Requirement 6

This document describes each integration adjustment (A1–A7) applied
to merge the four independently-validated modules (accessories,
promotions, warranties, returns) into a single, coherent system.

## A1 — Category discount for accessories

**Cause:** `CategoryDiscount` (Requirement 2) was implemented before
the accessory module (Requirement 1) existed as a sellable category,
so it only recognized `"VIDEOGAME"` and `"CONSOLE"` as valid targets.
Once accessories became sellable products, a promotion could not
target them.

**Solution:** `CategoryDiscount.calculateDiscount` now also checks
`product instanceof Accessory` when `targetCategory.equals("ACCESSORY")`.
`PromotionService.registerCategoryDiscount` validates the target
category against the three allowed values, rejecting anything else.
One additional promotion of this new category was preloaded in
`data/promotions.csv`.

## A2 — Circular dependency between sales and warranties

**Cause:** `SaleService` depends on `WarrantyService` to generate
warranties when registering a sale. `WarrantyService` depended on
`WarrantyRepository`. `WarrantyRepository`, in its original design,
depended on `SaleService` to resolve the `Sale` referenced by each
warranty when loading them from disk — closing a dependency cycle
(`SaleService → WarrantyService → WarrantyRepository → SaleService`)
that a dependency-injection based `Main` cannot construct.

**Solution:** `WarrantyRepository` was reduced to persisting and
loading only raw identifiers (product id, sale id, type, dates), with
no dependency on any service. The responsibility of resolving the
actual `Sale` (through `SaleRepository`, not `SaleService`) and
`Product` (through `ProductService`) references was moved to
`WarrantyService`, which now receives `WarrantyRepository`,
`SaleRepository`, and `ProductService` in its constructor. This
removes the cycle entirely, since `SaleRepository` has no dependency
on any service.

## A3 — Unified sale registration flow

**Cause:** Requirements 1, 2, and 4 each modified `SaleService.registerSale`
independently, assuming their own extension was the only one
present. Combined, this produced an inconsistent and undocumented
order between stock validation, promotion discounts, and warranty
costs — for example, calculating the applied promotion's discount
*after* adding the extended warranty cost would incorrectly discount
the warranty itself, since a promotion should only ever apply to the
products' subtotal.

**Solution:** `registerSale` was rewritten around one explicit,
documented order: validate at least one item exists; validate stock
for every item, resolving whether it is a `Product` or an `Accessory`;
find and apply the best promotion (based purely on the items'
subtotal); generate warranties for every console and sum any extended
warranty cost; update inventory, delegating to `ProductService` or
`AccessoryService` depending on the concrete item type; and finally
persist the sale. `Sale` gained a `getFinalTotal()` method
(`subtotal - discountAmount + extendedWarrantyCost`) so this formula
is defined in exactly one place, reused by both `generateReceipt()`
and the monthly balance report.

## A4 — Returning accessories

**Cause:** The return module (Requirement 3) only knew how to restore
stock through `ProductService`, since accessories did not exist yet
when it was built. A returned accessory's stock could not be restored.

**Solution:** `ReturnService` now checks whether a returned item is an
`Accessory` and, if so, delegates to a new `AccessoryService.restoreStock`
method (mirroring `ProductService.restoreStock`) instead of
`ProductService.restoreStock`. `ReturnRepository` was extended to
resolve accessory references the same way it already resolved
product references.

## A5 — Proportional refund

**Cause:** The original `Return.calculateRefundAmount()` summed the
full list price of each returned item, ignoring any promotion
discount that had been applied to the original sale. This overpaid
the customer whenever the sale had a discount.

**Solution:** `calculateRefundAmount()` now computes the sale's
discount ratio (`discountAmount / subtotal`) and applies it to each
returned item's price individually, so the refund reflects the same
proportional discount the customer actually paid. `generateReturnReceipt()`
was updated to show each item's list price, its proportional
discount, and its refunded amount.

## A6 — Monthly balance breakdown

**Cause:** `generateMonthlyBalance` originally used `Sale.calculateTotal()`
(the plain subtotal) to sum a month's sales, ignoring both promotion
discounts and extended warranty income — producing a balance that did
not reflect the store's real net income.

**Solution:** `ReturnService` gained two new methods,
`calculateMonthlySales(month, year)` and `calculateMonthlyReturns(month, year)`,
both used internally by `generateMonthlyBalance`. The sales figure now
uses `sale.getFinalTotal()` (introduced in A3) instead of the plain
subtotal, so promotions and warranty income are correctly reflected.
The console menu was updated to display all three figures (sales,
returns, and net balance) instead of only the final number.

## A7 — Cancelling a warranty when its console is returned

**Cause:** If a customer returned a console, its associated warranty
(basic or extended) remained active and stored, even though the
console it covered was no longer with the customer — an inconsistent
state, and a missed refund for any extended warranty cost the
customer had paid.

**Solution:** `WarrantyService` gained a `cancelWarranties(productId, saleId)`
method that removes every warranty matching that product and sale,
and returns the refundable amount (zero for a basic warranty, the
additional cost for an extended one). `ReturnService.registerReturn`
calls this method for every returned `Console`, and `Return` gained
an `addWarrantyRefund(amount)` method so this value is folded into
the final `refundAmount` and shown separately in the return receipt.
This branch was started only after A5 was merged, since both modify
the same refund calculation.



