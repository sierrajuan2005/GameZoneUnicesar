AI Usage Log - leader (GameZone Module)
Summary

This document records the contributions made by Developer 2 during the development of the GameZone project, with the support of AI tools. The AI was mainly used to clarify programming concepts, review implementation decisions, solve errors, and support Git/GitHub workflows.

Entry 1

Date: 2026-09-06
Tool used: AI Assistant (ChatGPT)
Reason for use: Understanding the project architecture, service layer, DAO/persistence, and file handling in Java.
Problem encountered: I needed to understand the responsibilities of the different layers before continuing with the implementation.
Prompt used: "Explícame la capa service en programación y archivos y la capa dao, explícame todo de archivos en programación Java POO."
Solution and decision: The AI explained the responsibilities of the model, service, and DAO/persistence layers, as well as how Java file handling works. I used this explanation to better organize the responsibilities of the classes in GameZone and understand how the layers should communicate.


Entry 2

Date: 2026-09-07
Tool used: AI Assistant (ChatGPT)
Reason for use: Understanding the save() method before continuing with the rest of the repository.
Problem encountered: I wanted to understand one repository operation in detail instead of implementing all the methods without understanding their purpose.
Prompt used: "¿Me ayudas con el primero con el de save? de ahí yo me guío."
Solution and decision: The AI explained the save() method step by step, showing how a Sale object is converted into text and stored in the file. I used this method as a reference to understand the other repository operations.

Entry 3

Date: 2026-09-08
Tool used: AI Assistant (ChatGPT)
Reason for use: Resolving compilation and implementation errors.
Problem encountered: Several errors appeared while integrating the repositories and services.
Prompt used: "Cannot resolve method 'loadAll' in 'SaleRepository'"
Solution and decision: The AI helped me review the structure of SaleRepository and understand why loadAll() was not being recognized. I checked the method declaration and its use in the project.

Problem encountered: A reference to a repository that was not being recognized appeared.
Prompt used: "Cannot resolve symbol 'ProductRepository' mira"
Solution and decision: I reviewed the imports and dependencies and identified that ProductRepository was not required in that part of the implementation.

Problem encountered: An object was being created without the required constructor arguments.
Prompt used: "Expected 2 arguments but found 0"
Solution and decision: The AI explained the constructor error and helped me review how the corresponding class was being instantiated.


Entry 4

Date: 2026-09-09
Tool used: AI Assistant (ChatGPT)
Reason for use: Integrating ConsoleUI, Main, services, and repositories.
Problem encountered: I needed to connect the different layers so that the application could be executed from Main and interacted with through the console.
Prompt used: "Ayúdame con el primero..." / consultas realizadas durante la integración de ConsoleUI y Main.
Solution and decision: The AI helped me understand how Main should connect the repositories with the services and how ConsoleUI should communicate with the services. I implemented the menus for products, people, and sales and used the services to execute the corresponding operations.

Entry 5

Date: 2026-09-09
Tool used: AI Assistant (ChatGPT)
Reason for use: Adding Javadoc documentation to the project.
Problem encountered: I wanted to document the important parts of the code without adding unnecessary comments to every method.
Prompt used: "¿Me puedes agregar los comentarios Java Docs?"
Solution and decision: The AI helped me add concise Javadoc to the main classes, constructors, and important public methods. I decided not to document every getter, setter, or private helper method so that the code would remain readable.

Entry 6

Date: 2026-09-10
Tool used: AI Assistant (ChatGPT)
Reason for use: Managing branches and integrating changes from Git.
Problem encountered: I needed to bring changes from develop into my current branch.
Prompt used: "¿Cómo es que traigo los cambios que están en develop a mi rama?"
Solution and decision: The AI explained how to fetch the latest remote changes and merge origin/develop into my current branch. I used this workflow to keep my branch updated.

Problem encountered: I only wanted to bring specific files from another branch instead of merging everything.
Prompt used: "¿Y si solo quiero traer unos documentos en específico se puede?"
Solution and decision: The AI showed me how to restore specific files from another branch using git restore --source, which allowed me to integrate only the files I needed.

Entry 7

Date: 2026-09-10
Tool used: AI Assistant (ChatGPT)
Reason for use: Resolving conflicts in a GitHub Pull Request.
Problem encountered: GitHub showed merge conflicts between my branch and develop.
Prompt used: "¿Cómo hago ahí?"
Solution and decision: After reviewing the conflict shown in GitHub, the AI explained how to update my branch with develop, identify the conflict markers, manually choose the correct code, and then complete the merge with git add, git commit, and git push. This helped me understand that Git conflicts require manually deciding which changes should remain.

Entry 8

Date: 2026-09-10
Tool used: AI Assistant (ChatGPT)
Reason for use: Preparing the Pull Request and documenting the implemented functionality.
Problem encountered: I needed to organize the Pull Request description and clearly explain the changes and tests performed in the project.
Prompt used: "Ayúdame a hacer el PR."
Solution and decision: The AI helped me structure the Pull Request description, including the implemented functionality, the changes made to the different layers, and the tests performed. I documented tests such as compiling the project, running Main, registering products and people, creating sales, and verifying that stock was correctly decreased.


# AI Usage Log — Leader

## Entry 1

**Date:** September 26, 2026
**Time:** 7:00 Pm 
**Tool:** ChatGPT
**Phase and branch:** A3 — `refactor/unified-sale-registration`

**Objective:**
Handle products and accessories within the same sale.

**Query:**

> A sale can now contain products and accessories. How can I handle both types of items in the same flow without duplicating the entire validation process?

**Response:**
The AI suggested resolving each item by checking its type and using the corresponding service to validate stock and update the inventory.

**Decision:**
The approach was implemented to maintain a single sales flow and delegate inventory management according to the item type.

**Related commit:**
`refactor: unify sale registration flow`

---

## Entry 2

**Date:** September 26, 2026
**Time:** 7:17 pm
**Tool:** ChatGPT
**Phase and branch:** A3 — `refactor/unified-sale-registration`

**Objective:**
Verify how the promotion should be applied in the new flow.

**Query:**

> After resolving all the products and calculating the subtotal, I need to use `findBestPromotionFor(sale)`. Does it make sense to apply the discount before calculating the warranties?

**Response:**
Yes. The AI explained that the promotion should be calculated using the items' subtotal, and the cost of extended warranties should be added afterward.

**Decision:**
This order was maintained so that the warranties would not affect the base used to calculate the discount.

**Related commit:**
`refactor: unify sale registration flow`

---

## Entry 3

**Date:** September 26, 2026
**Time:** 8:00
**Tool:** ChatGPT
**Phase and branch:** A3 — `refactor/unified-sale-registration`

**Objective:**
Integrate basic and extended warranties without affecting the total calculation.

**Query:**

> Consoles generate a basic warranty, and some of them can have an extended warranty. How should this fit into the flow after applying the promotion?

**Response:**
The AI indicated that warranties should be generated after calculating the discount and that only the additional cost of extended warranties should be added to the final total.

**Decision:**
The proposal was accepted and the final calculation was reorganized according to this sequence.

**Related commit:**
`refactor: unify sale registration flow`

---

## Entry 4

**Date:** September 27, 2026
**Time:** 10:00 P.M
**Tool:** ChatGPT
**Phase and branch:** A3 — `refactor/unified-sale-registration`

**Objective:**
Update the receipt to represent the new integrated sales flow.

**Query:**

> `Sale.generateReceipt()` currently shows the basic sale information. Now that we have discounts and extended warranties, what information should it include so that the receipt represents the actual final amount?

**Response:**
The AI recommended displaying the subtotal, applied promotion and discount, extended warranty cost, and final total.

**Decision:**
`generateReceipt()` was modified to include these values.

**Related commit:**
`refactor: unify sale registration flow`

---

## Entry 5

**Date:** September 27, 2026
**Time:** 8:56 P.M
**Tool:** ChatGPT
**Phase and branch:** A3 — `refactor/unified-sale-registration`

**Objective:**
Adapt the sales menu to the new integrated flow.

**Query:**

> The sales menu currently works mainly with products. How should I modify it so that it also allows users to select accessories and asks about an extended warranty when appropriate?

**Response:**
The AI recommended keeping the interaction in `ConsoleMenu`, allowing both types of items to be selected and asking about an extended warranty only for consoles.

**Decision:**
The approach was accepted and the menu flow was modified without moving business logic into the interface.

**Related commit:**
`refactor: unify sale registration flow`

---

## Entry 6

**Date:** September 27, 2026
**Time:** 1:15 P.M
**Tool:** ChatGPT
**Phase and branch:** A8 — `docs/integration-documentation`

**Objective:**
Review the information required for the integrated class diagram.

**Query:**

> For `integrated-class-diagram.md`, I need to represent the four modules together. What should I review to make sure the dependencies between UI, service, persistence, and model remain consistent?

**Response:**
The AI recommended checking the dependencies between the four layers and representing the relationships of each module within a single structure.

**Decision:**
This review was used to build the integrated Mermaid diagram.

**Related commit:**
`docs: update integrated class diagram`

---

## Entry 7

**Date:** September 27, 2026
**Time:** 2:37 P.M
**Tool:** ChatGPT
**Phase and branch:** A9 — `develop`

**Objective:**
Perform the final review before publishing the integrated version.

**Query:**

> All the integration adjustments have been merged. As the team leader, what should I verify in `develop` before opening the Pull Request to `main`?

**Response:**
The AI recommended verifying the complete sales and returns flow, the functionality of the four modules, the tests, and that no integration errors remained.

**Decision:**
This review was used as the final verification before preparing the PR.

**Related commit:**
N/A

---

## Entry 11

**Date:** September 27, 2026
**Time:** 1:17 p.m.
**Tool:** ChatGPT
**Phase and branch:** A9 — `develop`

**Objective:**
Prepare the integrated version for publication.

**Query:**

> After verifying `develop`, what is the correct workflow to publish the integrated version while following the Git rules defined in the requirement?

**Response:**
The AI indicated that a Pull Request should be opened from `develop` to `main`, reviewed and approved by another team member, and then merged.

**Decision:**
The established workflow was followed and the Pull Request from `develop` to `main` was prepared.

**Related commit:**
N/A
