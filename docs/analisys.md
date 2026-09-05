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

The allowed dependencies are:

```text
User Interface → Services

Services → Model

Services → Persistence

Persistence → Model
```

These dependencies allow each layer to communicate with the layer it needs while keeping responsibilities separated. This helps reduce coupling and makes the system easier to maintain, modify, and expand.
