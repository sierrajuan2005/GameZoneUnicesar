## Return Analysis

## 1. What type of relationship exists between Return and Sale?

The relationship between `Return` and `Sale` is an **association**. A return refers to an existing sale, but `Return` does not inherit from `Sale`, because they represent different concepts in the system.

It is not composition because a `Sale` does not depend on a `Return` to exist, and a sale can exist without having a return. It is also not aggregation because the return is not simply a collection or container of sales. Instead, the `Return` class maintains a reference to the `Sale` associated with the return.

Therefore, the relationship is an association because a return is linked to a previously existing sale.

## 2. How is the return of only some products represented?

A return does not necessarily include every product from the original sale. For this reason, the `Return` class should contain an attribute that stores the products being returned.

The returned products can be represented using a collection, such as a `List<Product>`. This list contains only the products that the customer wants to return from the original sale.

For example, if a sale contains three products and the customer returns only two of them, the `returnedProducts` attribute will contain only those two products. This allows the system to distinguish between the complete original sale and the specific products included in the return.

## 3. Where should the 30-day validation be implemented?

The 30-day return rule should be implemented in the **service layer**, because it is a business rule. The service layer is responsible for validating whether an operation is allowed before modifying the system's data.

The system must compare the date of the original sale with the date of the return. In Java, this difference can be calculated using the `java.time` API. For example, `ChronoUnit.DAYS.between()` can be used to calculate the number of days between two dates.

This validation should be performed by the return service before registering the return. If more than 30 days have passed, the service should reject the operation.

## 4. How is stock increased when products are returned?

When a product is returned, the system should reuse the existing stock update method from Workshop 1 instead of implementing new stock-increase logic.

The existing `increaseStock()` method can be reused to increase the available quantity of each returned product. This method should be invoked from the return service, because the service layer is responsible for coordinating the return operation and applying its business rules.

Reusing the existing method is important because it avoids duplicating business logic. It also ensures that stock is updated consistently throughout the system. If the stock logic were duplicated, changes made to one implementation could cause inconsistencies or errors in another part of the application.

## 5. Where should the monthly balance report be located?

The monthly balance report should be implemented in a **service class**, because it requires business logic that combines information from different modules of the system.

The service responsible for generating the report needs access to both sales and returns information. Therefore, it should have dependencies on the `SaleService` and `ReturnService`, or on the corresponding repositories/services that provide this information.

The report can use sales to calculate the total income for the selected month and returns to account for the value of returned products. By combining this information, the service can generate the monthly balance.

Placing this functionality in the service layer is consistent with the layered architecture because the UI should only request and display the report, while the service layer is responsible for processing and consolidating the business information required to generate it.
