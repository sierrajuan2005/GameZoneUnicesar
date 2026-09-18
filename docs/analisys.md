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

## Promotion Module 

1. How does inheritance and polymorphism allow the system to handle different types of promotions uniformly?

Inheritance allows the different promotion types to share the common attributes and behavior defined in the abstract Promotion class. PercentageDiscount, CategoryDiscount, and BulkPurchaseDiscount extend Promotion, so they inherit attributes such as the identifier, name, start date, and end date.

Polymorphism allows the system to treat all these different promotion types as Promotion objects while each subclass provides its own implementation of calculateDiscount(Sale sale). Therefore, PromotionService does not need to know the specific type of every promotion. It can iterate through a list of Promotion objects and call calculateDiscount() on each one, allowing Java to execute the appropriate implementation for each promotion.

2. Why is calculateDiscount(Sale sale) abstract instead of having a default implementation?

calculateDiscount(Sale sale) is abstract because each type of promotion calculates its discount using different rules. A percentage promotion applies a percentage to the sale total, a category promotion applies the percentage only to products from a specific category, and a bulk purchase promotion depends on the number of products in the sale.

There is no single calculation that would correctly represent all promotion types. Making the method abstract forces every concrete promotion class to provide its own implementation and ensures that the specific discount logic remains inside the corresponding promotion class.

3. Where should the logic for selecting the best promotion live, and why?

The logic for selecting the best promotion should live in PromotionService, specifically in the findBestPromotionFor(Sale sale) method.

PromotionService is responsible for managing promotion-related operations, so it is the appropriate layer to retrieve the active promotions, calculate the discount offered by each one, compare the monetary discounts, and return the promotion that provides the highest discount.

This also keeps the responsibilities separated. The model classes calculate their own discounts, the repository handles persistence, and the service coordinates the promotion-selection process.

4. What changes are needed in Sale and generateReceipt() to show the applied promotion and final total?

Sale needs two additional attributes:

private String appliedPromotionName;
private double discountAmount;

These attributes allow the sale to store which promotion was applied and the monetary value of the discount.

The receipt must then display the sale subtotal, the applied promotion and its discount, and the final total after the discount. The final amount can be calculated as:

Final Total = Subtotal - Discount

The presentation of this information should remain in the appropriate presentation layer, while Sale stores the promotion information required by the sale.

5. How should the system validate that a promotion is active based on the sale date?

The Promotion class provides the isActive(LocalDate date) method to verify whether a promotion is valid for a specific date. The promotion is active when the date is not before the start date and is not after the end date.

In other words, the validity period is inclusive:

return !date.isBefore(startDate) && !date.isAfter(endDate);

This means a promotion can be applied on its start date and on its end date, but not before or after that period. The promotion service uses the active promotions when determining which promotion can be applied to a sale.