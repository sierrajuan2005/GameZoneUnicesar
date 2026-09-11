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