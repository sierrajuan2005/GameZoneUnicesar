
# GRASP Analysis: Guiding Questions

## 1. Criteria for a verdict and minimum evidence

A fragment is a **violation** when the code shows a clear problem with a specific cost, such as changing several classes when adding a new product type. A pattern is **correct** when the responsible class and method can be identified and the code shows the pattern working, such as `Main` creating objects. A pattern is **not applicable** when the code has no situation where it could be used, such as Controller in `Sale`, which does not receive system events.

To accept a finding, the report must include the class, method, exact lines in the audited `develop` commit, an explanation of the problem or correct use, and its concrete consequence. Findings without evidence or with an incorrect pattern are rejected. A pattern that is correctly applied must not be reported as a violation.

## 2. Findings on the boundary between two members' code

- **Stock handling (V-05):** `SaleService#registerSale` and `ProductService#restoreStock` (Leader) compute stock values that belong to `Product`/`Accessory`, and call `updateStock` in `ProductService` (Developer 1) and `AccessoryService` (Developer 2). It is recorded once in the consolidated audit; Developer 1 and Developer 2 each add the stock operations on their side and the Leader replaces the arithmetic in `SaleService` and `restoreStock`.
- **Product type checks (V-04):** the `Accessory`/`Console` checks live in `SaleService` and `ConsoleUI` (Leader), while the hierarchy belongs to Developer 1. One finding, with the polymorphic operation created by Developer 1 and the callers adapted by the Leader.
- **Object creation in the UI (V-06):** `ConsoleUI` (Leader) creates entities that `ProductService` (Developer 1) and `PersonService` (Developer 2) should create.
- **Layer inversion (B-01):** `ReturnRepository` receives services in its constructor (visible in `Main`). The class belongs to Developer 2, who is responsible for confirming it in their report; the Leader only adapts the wiring in `Main`.
- **Direct use of `SaleRepository` (B-02):** `WarrantyService` (Developer 2) receives `SaleRepository` (Leader) directly.

Each case is registered once in `consolidated-audit.md`, with the part of the correction assigned to each member. To make sure none was missed, every constructor call in `Main` and every cross-class `instanceof` or `new` was checked against the responsibility table, and the other two reports were compared with this one.

## 3. Code that breaks more than one pattern

Yes. To avoid duplicate findings, each fragment is registered **once**, under the pattern that guides its correction. Any related pattern is mentioned in the same finding.

- **V-01, V-02, V-08, V-09 (High Cohesion + Pure Fabrication):** the class does too many things, and the solution is to move some responsibilities into a new class. High Cohesion is the guiding pattern because it identifies the problem; Pure Fabrication describes the solution.
- **V-03 and V-04 (Polymorphism + Protected Variations):** type conditionals are the symptom and the lack of a protected change point is the consequence. Guiding pattern: Polymorphism, because the correction replaces the conditional.
- **V-07 (Controller + Information Expert):** the UI makes decisions that belong to the services that own the data. Guiding pattern: Controller.

Criterion to choose the guiding pattern: the one whose correction removes the problem at its root. The related pattern is mentioned in the finding but gets no separate ID.

## 4. Non-regression strategy

All 31 menu operations are verified (products 3, people 3, sales 5, accessories 6, warranties 4, returns 5, promotions 5), plus the exit and the invalid-option paths.

1. **Reference run.** On `develop`, before the first correction, each operation is run with a fixed input script (the same keystrokes every time) from a clean `data/` folder with the same initial files. The console output and the resulting data files are saved as reference.
2. **Inputs per operation.** A valid case and at least one error case per module (not found, business rule, invalid data), so the differentiated messages are also compared.
3. **Re-run.** After each Pull Request the affected operations are run again with the same scripts; at the end of the phase all of them. Output and data files are compared line by line (`diff`).
4. **Non-deterministic values.** Two values change on every run: the sale identifier (`"SALE-" + System.currentTimeMillis()`) and everything that depends on `LocalDate.now()` (warranty dates, return eligibility). Before comparing, the sale identifier is normalized, and the scripts use dates relative to the same day.
5. **Compilation.** `mvn clean compile` must pass before each Pull Request.

Evidence shown at the end: the reference and final results recorded in `docs/grasp-refactor-log.md` (operation, input, expected result, obtained result) and the empty `diff` of outputs and data files.

## 5. Order of the corrections and coordination

The corrections are done from the simplest to the riskiest, starting with changes that do not depend on other members.

1. **Violations contained in one class and owned by one member:** V-01, V-08 and V-09 (Leader), then V-02 (Leader). They do not require agreements with anyone.
2. **V-03:** after V-02, because it changes the code that V-02 extracts.
3. **Boundary violations (V-04, V-05, V-06, V-07):** the owners add first the new methods (additive change that breaks nobody): Developer 1 and Developer 2 on `Product`, `ProductService`, `AccessoryService` and `PersonService`. Only then does the Leader replace the callers in `SaleService`, `ConsoleUI` and `Main`, and finally the old code is removed.

Coordination in the shared branch: each member touches only files under their own responsibility; before each push, `git pull origin feature/grasp-refactor`; commits are small and pushed immediately; the Leader announces which phase of a boundary violation is next so the other member does not edit the same file at the same time. No force push and no commits directly to `develop`.
