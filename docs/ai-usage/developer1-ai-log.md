# Developer 1 - AI Usage Log

This log records the use of Artificial Intelligence as support during the
development of the Product module for the GameZoneUnicesar project.

## Entry 1 - Product Class Hierarchy

**Tool used:** ChatGPT

**Reason for use:** Understand the structure required for the Product module.

**Problem faced:** The project required an abstract Product class with common
attributes and two specialized product types: VideoGame and Console.

**Support received:** ChatGPT was used to clarify the use of inheritance,
abstract classes, encapsulation, constructors, getters, setters and method
overriding in Java.

**Decision taken:** Product was defined as an abstract class containing the
common product information. VideoGame and Console were implemented as concrete
subclasses with their specific attributes and behavior.

**Student validation:** The implementation was reviewed and compiled
successfully before continuing with the persistence layer.

---

## Entry 2 - Product Persistence

**Tool used:** ChatGPT

**Reason for use:** Understand how to persist Product objects using files.

**Problem faced:** Product information had to remain available between
application executions, and the persistence layer needed to handle both
VideoGame and Console objects.

**Support received:** ChatGPT was used to explain how a CSV file could store
product information and how a type identifier could distinguish between
different product subclasses.

**Decision taken:** ProductRepository was implemented using a CSV file. A type
identifier is stored in each record so the corresponding concrete product can
be reconstructed when the data is loaded.

**Student validation:** Products were saved and loaded from the CSV file to
verify that their information was preserved correctly.

---

## Entry 3 - Product Repository Troubleshooting

**Tool used:** ChatGPT

**Reason for use:** Review and troubleshoot the product persistence
implementation.

**Problem faced:** During development, there was an issue related to writing
product information to the CSV file.

**Support received:** ChatGPT was used to review the persistence logic and
identify the part responsible for writing the product records to the file.

**Decision taken:** The file-writing logic was corrected so that the generated
product records were properly stored in the CSV file.

**Student validation:** The persistence functionality was tested again after
the correction, and the project was compiled successfully.

---

## Entry 4 - Product Service

**Tool used:** ChatGPT

**Reason for use:** Review the business logic required for managing products.

**Problem faced:** The product service needed to register products, prevent
duplicate identifiers, list products and update inventory while validating
stock quantities.

**Support received:** ChatGPT was used to clarify how these validations and
operations could be organized in the service layer.

**Decision taken:** ProductService was implemented as the intermediary between
the application and ProductRepository. It validates duplicate identifiers,
negative stock quantities and attempts to update products that do not exist.

**Student validation:** The service was compiled and tested with VideoGame and
Console objects, including stock updates and validation cases.

---

## Entry 5 - JavaDoc Documentation

**Tool used:** ChatGPT

**Reason for use:** Understand and apply the JavaDoc requirements of the
project.

**Problem faced:** The Product module needed documentation for its classes and
public methods in English.

**Support received:** ChatGPT was used to clarify the purpose of JavaDoc and
the use of tags such as `@param`, `@return` and `@throws`.

**Decision taken:** JavaDoc was added directly to the Java source files of the
Product module. Class descriptions, constructors and public methods were
documented in English.

**Student validation:** The project was compiled successfully after adding the
JavaDoc documentation.

---

## Entry 6 - Git and GitHub Workflow

**Tool used:** ChatGPT

**Reason for use:** Understand the Git and GitHub workflow required by the
project.

**Problem faced:** The Product module had to be developed in a feature branch
and integrated into the develop branch through a Pull Request.

**Support received:** ChatGPT was used to clarify the purpose of feature
branches, Pull Requests, cross-review, approvals and the merge process.

**Decision taken:** The Product work was developed in the
`feature/developer1-products` branch and integrated into `develop` through a
Pull Request.

**Student validation:** Git status, branches, commits and the Pull Request
were checked to verify that the changes were correctly pushed and integrated.

---

## Student Responsibility

Artificial Intelligence was used as a support tool for understanding concepts,
reviewing implementation decisions, troubleshooting problems and clarifying
Git and Java functionality. The final implementation was reviewed, adapted
and validated by the student through compilation and testing.