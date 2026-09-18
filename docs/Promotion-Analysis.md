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