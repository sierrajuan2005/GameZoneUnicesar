# Exception Handling Analysis

## 1. Checked or unchecked?

`GameZoneException` extends `RuntimeException` (unchecked).

- Most of these errors cannot be fixed where they happen. Only the user
  interface knows what to do with them: show a message.
- With a checked exception, every method between the UI and the repository
  would need `throws` or a `try/catch` just to pass the error up.
- Because it is unchecked, the method signatures of services and repositories
  do not change. To avoid forgetting an error, each method documents its
  exceptions with `@throws` in the JavaDoc.

## 2. Why a custom hierarchy with a common root?

`IllegalArgumentException` does not say what kind of problem happened. With
`GameZoneException` and its four subclasses we can:

- Catch each type and show a different message to the user.
- Catch the root class when the type does not matter.
- Add useful data, such as an error code, the resource and its identifier, or
  the file and the operation.

Design principles applied: single responsibility (one exception per kind of
error), open/closed (new types can be added without changing the old ones) and
separation of concerns (each layer handles its own errors).

## 3. Where are validators called? Can they access persistence?

Validators are called in the **service layer**, at the start of each operation,
before any business logic runs or any data is changed. This fits the
architecture: the service coordinates the operation, so it decides when to
validate, and the UI and the model stay simple.

Validators **cannot** access persistence. They would mix validation with file
access and break the dependency direction. When a validator needs stored data,
the service loads it and passes it as a parameter, for example the list of
existing people for `validateUniquePerson`.

## 4. How do we make sure the behavior is preserved?

- We only changed the type of the exceptions. The same conditions that failed
  before still fail now.
- We worked in small commits and compiled after each one.
- We ran the menu operations by hand with valid data and checked they work
  as before.
- We triggered one error of each type to check its message.
- Another team member reviewed every pull request.

Evidence:

| Scenario | Expected result |
|---|---|
| Normal operations with valid data | Same behavior as before |
| Unknown ID | `No se encontró el recurso solicitado: ...` |
| Quantity above stock | `No se puede completar la operación: ...` |
| Negative price | `Los datos ingresados no son válidos: ...` |
| Unreadable data file | `Ocurrió un problema al acceder a los datos del sistema. Contacte al administrador.` |

Screenshots are attached to the pull request.

## 5. One try with several catch, cascading catch, or a general catch?

We use **cascading catch blocks**, one per exception type, with
`catch (GameZoneException)` as the last one.

- A single general catch gives the same message for every error, and the
  requirement asks for different messages.
- A multi-catch (`A | B`) joins types into one block, so they would share a
  message.
- With the cascade, each block is short and has one job. The four subclasses
  are siblings, so their order does not matter, but the root class must go last
  or the code does not compile.
- `PersistenceException` does not show `getMessage()`, because it contains
  technical details the user must not see.