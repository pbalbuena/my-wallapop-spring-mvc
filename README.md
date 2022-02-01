# my-wallapop-spring-mvc
Aplicación que simula el funcionamiento de wallapop.

Realizada con Spring Boot siguiendo el patrón de diseño MVC (Modelo-Vista-Controlador)

El proyecto esta dividido de la siguiente forma:

## Controllers
Los controladores del sistema son:
- HomeController
- OfertasController
- UsersController
## Entities
Las entidades del sistema son:
- Conversación (Tiene una oferta, un usuario interesado, un usuario que vende, una lista de mensajes)
- Mensaje (Tiene un usuario que es el autor del mensaje, una conversación a la que pertenece)
- Oferta (Tiene un usuario que vende, un usuario que compra, una lista de conversaciones asociada)
- Usuario (Tiene una lista de ofertas a la venta, una lista de ofertas que ha comprado, una lista de conversaciones en las que participa, una lista de mensajes enviados)
## Repositories
Los repositorios del sistema son:
- ConversacionRepository
- MensajeRepository
- OfertaRepository
- UsuarioRepository
## Services
Los servicios del sistema son:
- ConversacionService
- MensajeService
- OfertaService
- UsuarioService
- RolesService (Otorga roles de administrador o usuario básico)
- SecurityService (Maneja la seguridad del sistema)
- UserDetailsServiceImpl (Comprueba el login del usuario)
- InsertSampleDataService (añade datos de ejemplo a la base de datos)
