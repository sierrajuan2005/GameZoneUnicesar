TEAM.md
Información del equipo
Integrante	Nombre completo	Código estudiantil	Rol asignado
1	Juan Diego Sierra Teran 1049927990	Líder Técnico
2	Alejandro Enrique Ochoa Silvera	5962871 PPT	Desarrollador 1
3	Eusid junior carrillo romero  1003236619	Desarrollador 2
Roles y módulos asignados
Líder Técnico — Juan Sierra
Módulo asignado: Ventas (atraviesa las capas de modelo, persistencia, servicio) + interfaz de usuario y clase principal, integrando los tres módulos.
Rama feature: feature/sale-module
Desarrollador 1 — 5962871 PPT
Módulo asignado: Productos (modelo, persistencia y servicio)
Rama feature: feature/product-module
Desarrollador 2 — Eusid junior carrillo romero
Módulo asignado: Personas (modelo, persistencia y servicio)
Rama feature: feature/person-module
Distribución de clases
Clase	Capa	Responsable
Person (abstracta)	Modelo	Desarrollador 2
Customer	Modelo	Desarrollador 2
Seller	Modelo	Desarrollador 2
PersonRepository	Persistencia	Desarrollador 2
PersonService	Servicio	Desarrollador 2
Product (abstracta)	Modelo	Desarrollador 1
VideoGame	Modelo	Desarrollador 1
Console	Modelo	Desarrollador 1
ProductRepository	Persistencia	Desarrollador 1
ProductService	Servicio	Desarrollador 1
Sale	Modelo	Líder Técnico
SaleRepository	Persistencia	Líder Técnico
SaleService	Servicio	Líder Técnico
ConsoleUI	Interfaz de usuario	Líder Técnico
Main	Principal	Líder Técnico
Actividades comprometidas por integrante
Líder Técnico — Juan Sierra
Crear el repositorio del proyecto en GitHub con la configuración inicial (README, .gitignore, licencia).
Configurar las ramas del proyecto (main y develop) y activar la protección de las ramas principales.
Configurar el proyecto Maven con el pom.xml inicial y la estructura de paquetes de las cuatro capas.
Elaborar el archivo TEAM.md con la información del equipo, los roles asignados y la distribución de clases.
Implementar la clase del dominio de ventas (Sale) con sus atributos, constructor y métodos básicos.
Implementar el método de cálculo del total de la venta (calculateTotal).
Implementar la clase de persistencia del módulo de ventas (SaleRepository).
Implementar la clase de servicio del módulo de ventas (SaleService) con las reglas de validación (mínimo un producto, verificación de stock, actualización de inventario).
Implementar la estructura básica de la clase de interfaz de usuario (menú principal).
Implementar los submenús de la interfaz de usuario para cada uno de los tres módulos.
Implementar la clase principal de la aplicación (Main) con la carga inicial de datos y la inyección de dependencias.
Revisar e integrar los Pull Requests de los desarrolladores en la rama de integración.
Elaborar el README.md final del proyecto con las instrucciones de compilación y ejecución.
Desarrollador 1 — Alejandro Enrique Ochoa Silvera
Crear la rama feature correspondiente al módulo de productos.
Implementar la clase base abstracta de la jerarquía de productos (Product) con sus atributos comunes, constructor y métodos comunes.
Declarar el método abstracto de descripción que las clases derivadas deberán implementar.
Implementar la primera clase derivada (VideoGame) con sus atributos particulares y la implementación del método de descripción.
Implementar la segunda clase derivada (Console) con sus atributos particulares y la implementación del método de descripción.
Implementar la clase de persistencia del módulo de productos (ProductRepository) con los métodos de guardado y carga desde archivos.
Implementar la clase de servicio del módulo de productos (ProductService) con los métodos de registro, listado y actualización de stock.
Documentar todas las clases del módulo con JavaDoc en inglés.
Solicitar Pull Requests al Líder Técnico para la integración del módulo.
Desarrollador 2 — Eusid junior carrillo romero
Crear la rama feature correspondiente al módulo de personas.
Implementar la clase base abstracta de la jerarquía de personas (Person) con sus atributos comunes, constructor y métodos comunes.
Declarar el método abstracto o de negocio que las clases derivadas deberán implementar según el análisis realizado.
Implementar la primera clase derivada (Customer) con sus atributos particulares.
Implementar la segunda clase derivada (Seller) con sus atributos particulares.
Implementar la clase de persistencia del módulo de personas (PersonRepository) con los métodos de guardado y carga desde archivos.
Implementar la clase de servicio del módulo de personas (PersonService) con los métodos de registro y listado.
Documentar todas las clases del módulo con JavaDoc en inglés.
Solicitar Pull Requests al Líder Técnico para la integración del módulo.