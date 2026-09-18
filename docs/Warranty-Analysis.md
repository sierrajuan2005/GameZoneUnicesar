# Warranty Module

## 1. How is the hierarchy of warranty classes designed when they have common attributes but different duration, coverage, and cost?

All warranties share common attributes such as an identifier, associated product, associated sale, start date, and end date. However, each type of warranty has specific rules regarding its duration and additional cost. Therefore, the main class should be an abstract **Warranty** class, from which **BasicWarranty** and **ExtendedWarranty** are derived.

The common attributes and behaviors are defined in the **Warranty** class, while the specific behaviors are implemented in each subclass. The abstract method `getDurationInMonths()` allows each warranty type to define its own duration without duplicating the common code. `BasicWarranty` returns 6 months, while `ExtendedWarranty` returns 12 months. This design uses **inheritance and polymorphism** to reuse common functionality while allowing each subclass to implement its specific rules.

## 2. Where should the rule that only consoles receive an automatic basic warranty be located, and what Java mechanism is used to verify the product type?

This rule should be located in the **service layer**, specifically in the process of registering a sale, because it is a business rule. When the products included in a sale are processed, the system must determine whether each product is a console.

Java's `instanceof` operator can be used to verify the actual type of the product. If the product is an instance of **Console**, the system calls `WarrantyService.assignBasicWarranty()` to automatically create the basic warranty. Video games do not receive this automatic warranty.

This responsibility belongs in the service layer because the service layer manages business logic, while the model represents the domain objects and the persistence layer is responsible for storing information.

## 3. How is the expiration date calculated for each warranty subclass? Should this calculation be performed in the constructor or in a separate method?

The expiration date should be calculated in the constructor of the abstract **Warranty** class. The constructor receives the start date and uses the abstract method `getDurationInMonths()` to determine how many months must be added.

`BasicWarranty` returns 6 months, while `ExtendedWarranty` returns 12 months. Therefore, the same calculation can be reused by both subclasses without duplicating code.

This calculation should be performed in the constructor because the expiration date must be automatically established when a warranty is created. The subclasses are only responsible for defining their specific duration through the abstract method.

## 4. Where is the additional cost of the extended warranty calculated and applied during the sale registration process?

The additional cost of the extended warranty should be calculated and applied in **SaleService.registerSale**, during the sale registration process.

The method should receive an additional parameter, such as `List<String> productIdsWithExtendedWarranty`, containing the products selected by the user for extended warranty coverage. When processing each product, the system checks whether it is included in this list.

If the product has an extended warranty, `WarrantyService.assignExtendedWarranty()` is called. The additional cost returned by `getAdditionalCost()` is then added to the total of the sale. The extended warranty calculates this cost as 10% of the product price, while the basic warranty has no additional cost.

This keeps the warranty-specific cost calculation inside the warranty class and the integration of the warranty with the sale inside `SaleService`.

## 5. Where should the method for finding warranties that are about to expire be located, and what dependencies does it require?

The method `listWarrantiesExpiringSoon(int daysAhead)` should be located in the **WarrantyService** class because filtering warranties according to their expiration dates is a business operation.

`WarrantyService` requires a dependency on **WarrantyRepository** to obtain the registered warranties. It then iterates through the warranties and filters those whose expiration date falls within the specified number of upcoming days, such as the next 30 days.

This location is consistent with the layered architecture because the repository is responsible for persistence and retrieving data, while the service layer is responsible for applying business rules and performing operations. The user interface only needs to request the information from the service and display the results.