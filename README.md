# Java Mail Send 📧

_Este proyecto sirve para enviar correos mediante el uso de HTTP y la librería Java MailSender (SMTP)_

## Comenzando 🚀

Para iniciar, es necesario saber que el proyecto está dividido en dos ramas. Puedes usar la que más necesites; ambas cumplen el mismo objetivo bajo diferentes condiciones:

1. **Rama `free`**: Esta rama envía correos mediante HTTP. No utiliza el protocolo SMTP directamente, lo que la hace ideal para servicios de alojamiento como **Render**, **Fly.io** o **Vercel**, ya que estos no soportan el protocolo SMTP en sus planes gratuitos. Es la opción ideal para alojar tu código sin costo.
2. **Rama `ssl`**: Esta rama envía correos simples mediante el protocolo SMTP. Está funcional para enviar correos de manera local y el archivo `application.properties` está ajustado para que solo subas el código a un servicio de pago y funcione correctamente. Es más simple de entender y modificar.

### Prerrequisitos
Antes de clonar el proyecto, asegúrate de tener instaladada la versión de java:

* [Java 17+](https://www.oracle.com/java/technologies/downloads/) - Versión recomendada para Spring Boot 4.0.5 en adelante.

### Instalación 🔧

_Clona el repositorio en tu máquina local:_

```bash
git clone [https://github.com/Dabson10/Send_mail_Java.git](https://github.com/Dabson10/Send_mail_Java.git)
```

## Antes de ejecutar ⚙️

Cuando clones el repositorio, **no lo ejecutes de inmediato**. Es necesario configurar las variables de entorno para que el sistema funcione al 100%.

### 1. Rama `free` ☁️
Debes agregar estas variables en tu IDE o en el panel de configuración de tu servidor:

> **`${MAIL}`**: Correo electrónico de destino.  
> **`${NAME}`**: Nombre de la persona asociada al correo electrónico.  
> **`${BREVO_API_KEY}`**: API Key de Brevo (utilizada para sustituir SMTP por su facilidad de conexión).

### 2. Rama `ssl` 🔒
Para esta rama, las variables dependen directamente de tu proveedor de correo:

* **`${MAIL_HOST}`**: Servidor SMTP de tu proveedor.
  * *Gmail:* `smtp.gmail.com` | *Outlook:* `smtp-mail.outlook.com` | *iCloud:* `smtp.mail.me.com`
* **`${MAIL_PORT}`**: 
  * Local: `587` o `2525`. 
  * Producción (SSL): `465`. (El puerto `25` suele estar bloqueado).
* **`${MAIL}`**: Correo electrónico emisor/receptor.
* **`${PASSWORD}`**: **Contraseña de aplicación**. No uses tu contraseña real. En Gmail, genérala en [App Passwords](https://myaccount.google.com/apppasswords).
* **`${NAME}`**: Nombre del administrador o emisor.

---

### Guía de Brevo

Si quieres utilizar Brevo y hacer más rápido el despliegue gratuito, debes hacer lo siguiente:
1. Crea o ingresa con tu correo; no es necesario que sea el mismo en el que recibirás correos.
2. Ingresa al panel de control y a la sección de ajustes.
3. Entra en la sección SMTP y API, dentro de esta en claves API y MCP.
4. Genera una nueva clave API y agrégala en las variables de entorno.

[Imagen de la ubicación]()

### Prueba del endpoint `sendCorreo` 🔩

Para probar el envío, realiza una petición **POST** al endpoint correspondiente enviando el siguiente JSON (puedes usar Postman).

```json
// 1 Procesa la petición.
{
    "mail" : "dabson753@gmail.com",
    "header" : "Prueba render",
    "body" : "Esta será la última prueba antes de subir el proyecto a la rama free",
    "name" : "Juan David Almaraz González"
}

//2. Error objeto vacío.
// {}

// 3. Error de algún atributo vacío.
// {
//     "mail" : "",
//     "header" : "Cabecera del correo",
//     "body" : "Cuerpo del correo el correo electrónico",
//     "name" : "Mark"
// }

//4. Error de correo (no puede enviar ni recibir correos).
// 4.1
// {
//     "mail" : "perro@@gmail.com", o "mail" : "perro@gmail..com", o "mail" : "perro@@gmail..com"
//     "header" : "Cabecera del correo",
//     "body" : "Cuerpo del correo el correo electrónico",
//     "name" : "Mark"
// }
```

## Despliegue 📦

Para desplegar este proyecto en algún servicio de alojamiento, te recomiendo seguir estos pasos:

1. Haz un **Fork** de este repositorio o clónalo, selecciona la rama que necesites y súbelo a tu propio perfil de GitHub en un nuevo repositorio.
2. Desde el servicio de hosting que prefieras (como Render, Railway o Fly.io), conecta tu cuenta de GitHub y selecciona el repositorio.
3. El proyecto ya incluye un **Dockerfile**, lo que permitirá que el servicio lo detecte y lo suba rápidamente sin configuraciones adicionales complejas.

## Construido con 🛠️

* [Spring Boot](https://spring.io/projects/spring-boot) - El framework web principal.
* [Maven](https://maven.apache.org/) - Manejador de dependencias.
* [Java Mail Sender](https://javaee.github.io/javamail/) - Librería utilizada para la gestión de correos.

## Autor ✒️

* **Juan David Almaraz** - *Trabajo Inicial* - [Dabson10](https://github.com/Dabson10)

⌨️ con ❤️ por [Dabson :3](https://github.com/Dabson10)
