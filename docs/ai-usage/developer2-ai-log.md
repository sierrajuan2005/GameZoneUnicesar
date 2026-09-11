# AI Usage Log - Developer 2 (Person Module)

## Summary
This document records the contributions made by Developer 2 in the Person module of the GameZone project, assisted by AI tools.

---

### Entry 1
*Date:* 2026-09-06  
*Tool used:* AI Assistant (Copilot)  
*Reason for use:* Designing the service layer and its integration with the repository.  
*Problem encountered:* How to structure PersonService to properly delegate persistence operations to PersonRepository.  
*Prompt used:* "How should I design PersonService to handle add, search, list, and delete operations?"  
*Solution and decision:* Implemented PersonService with a PersonRepository attribute, constructors (default and dependency injection), and methods addPerson, getAllPeople, findPersonByIdentification, and removePerson.

---

### Entry 2
*Date:* 2026-09-07  
*Tool used:* AI Assistant (Copilot)  
*Reason for use:* Improving error handling in service methods.  
*Problem encountered:* How to handle cases where a person is not found by identification.  
*Prompt used:* "What is a good way to handle null results in service methods?"  
*Solution and decision:* Changed the return type of findPersonByIdentification to Optional<Person> to avoid null pointer exceptions and improve clarity.

---

### Entry 3
*Date:* 2026-09-07  
*Tool used:* AI Assistant (Copilot)  
*Reason for use:* Optimizing repository search methods.  
*Problem encountered:* Deciding between linear search or using a map for faster lookups.  
*Prompt used:* "What is the best way to implement findByIdentification in a repository?"  
*Solution and decision:* Kept a List<Person> for simplicity, but considered HashMap<String, Person> as a future optimization.

---

### Entry 4
*Date:* 2026-09-07  
*Tool used:* AI Assistant (Copilot)  
*Reason for use:* Ensuring atomic commits and a clean Git history.  
*Problem encountered:* How to logically split commits when adding constructors, getters/setters, and methods.  
*Prompt used:* "What is best practice for atomic commits in Java projects?"  
*Solution and decision:* Structured commits by functionality (constructor, getters/setters, repository methods, service methods) to maintain clarity in the history.

---

### Entry 5
*Date:* 2026-09-07  
*Tool used:* AI Assistant (Copilot)  
*Reason for use:* Initial configuration of IntelliJ IDEA and project directory structure.  
*Problem encountered:* How to correctly organize the path src/main/java/com.gamezone/... so IntelliJ recognizes packages.  
*Prompt used:* "Show me how to configure IntelliJ IDEA for Java projects and create the path src/main/java/com.gamezone with subfolders."  
*Solution and decision:* Configured IntelliJ IDEA to mark src/main/java as *Source Root*, created the root package com.gamezone, and organized subfolders. The project compiled and ran without package conflicts.

---

### Entry 6
*Date:* 2026-09-10  
*Tool used:* AI Assistant (Copilot)  
*Reason for use:* Resolving conflicts when pushing documentation commits (Javadoc).  
*Problem encountered:* Local repository did not match remote develop branch, preventing push.  
*Prompt used:* "How do I fix my commit not pushing because my local branch does not match develop?"  
*Solution and decision:*
- Pulled changes from develop using git pull origin develop.
- Manually reviewed files to resolve differences.
- Recommitted with git add ., git commit -m "docs: add Javadoc to classes", and pushed.
- Javadoc commit was successfully aligned with develop.

---

### Entry 7
*Date:* 2026-09-10  
*Tool used:* AI Assistant (Copilot)  
*Reason for use:* Documenting classes with Javadoc for clarity and maintainability.  
*Problem encountered:* Keeping Javadoc style consistent across service and main classes.  
*Prompt used:* "Generate concise Javadoc for PersonService and Main."  
*Solution and decision:* Added Javadoc to PersonService and Main, following the same format used in model and repository classes.

---

### Entry 8
*Date:* 2026-09-11 (Pull Request #5: Integration of Person module)  
*Tool used:* AI Assistant (Copilot)  
*Reason for use:* Fixing issues when creating the Pull Request in GitHub.  
*Problem encountered:* The PR was mistakenly opened against main instead of develop, causing integration conflicts.  
*Prompt used:* "How do I change the base branch of a PR in GitHub if I set it to main, but it should be developed?"  
*Solution and decision:*
- Identified the error in base branch selection.
- Explained that the fix must be done directly in GitHub.
- Edited the PR → changed base from main to develop.
- Verified comparison branch was feature/person-module.
- PR was correctly pointed to develop, keeping workflow clean.