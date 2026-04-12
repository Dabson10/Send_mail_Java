package org.github.dabson10.sendmail.service;

import lombok.Setter;
import org.github.dabson10.sendmail.entity.Correo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

//@AllArgsConstructor
@Setter
@Service
public class MailSend implements MailSendImpl {

    @Value("${spring.owner.mail}")
    private String correoPersonal;
    @Value("${spring.owner.name}")
    private String nombre;
    @Value("${brevo.api.key}")
    private String apyKey;

    private final RestTemplate rt = new RestTemplate();
    private static final String brevo_url = "https://api.brevo.com/v3/smtp/email";

    /**
     * Para la version <b>free</b> o cuando no pagas ningún servicio que acepte el protocolo SMTP y utilizas algún proveedor
     * como <b>Brevo</b> el cual realizara todo este trafico de correos electrónicos, no enviara un correo del remitente a tu
     * correo personal sera todo lo contrario. Del correo vinculado con <b>Brevo o cualquier proveedor</b> se enviara un correo
     * a tu perfil personal solamente que en el cuerpo del mensaje se añadirá una sección en donde se informara quien envio el correo,
     * incluyendo el correo mismo.
     * @param correo : parametro que cuenta con datos fundamentales como:
     *               -Mail: Correo del Remitente.
     *               -Asunto o Header: Mensaje con el asunto del correo.
     *               -Body o cuerpo: Cuerpo del correo.
     *               -Name: Nombre del Remitente.
     */
    @Override
    public void sendMail(Correo correo) {
        enviar(
                correoPersonal,           // Destinatario (Tu correo personal).
                nombre,                   // Nombre del destinatario.
                correoPersonal,           // Aquí se envia el correo desde tu correo asociado a tu correo personal.
                correo.getHeader(),       // Asunto del correo electrónico
                "Correo enviado por: " + correo.getName() + " Correo: " + correo.getMail() + "\n\n" + correo.getBody()
        );
        System.out.println("Correo enviado por: " + correo.getName() + " Correo: " + correo.getMail() + "\n\n" + correo.getBody());
    }

    /**
     * Esta función está muy relacionada con {@link #sendMail(Correo)} ya que al instante de enviar el mail del Remitente
     * al Destinatario se realiza una respuesta por parte del destinatario al Remitente, por lo que los datos
     * son prácticamente lo mismo solo que cambia el destinatario y el remitente.
     * Con una diferencia en el caso del uso <b>free</b> la respuesta generada no será con base al correo personal si no
     * al correo asociado con <b>Brevo u otro proveedor</b>.
     *
     * @param respuesta : A diferencia del correo este parametro es contiene los datos
     *                  del reclutador o persona que relleno el formulario.
     * @param correo    : Correo a donde fue enviado principalmente, este contiene los datos que recibiré
     *                  en mi correo.
     */
    @Override
    public void sendResponse(Correo respuesta, Correo correo) {
        enviar(
                correo.getMail(),
                nombre,
                correoPersonal,
                respuesta.getHeader(),
                respuesta.getBody()
        );
    }

    private void enviar(String destinatario, String remitenteNombre,
                        String remitenteEmail, String asunto, String contenido) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            ObjectNode sender = mapper.createObjectNode();
            sender.put("name", remitenteNombre);
            sender.put("email", remitenteEmail);

            ObjectNode toItem = mapper.createObjectNode();
            toItem.put("email", destinatario);

            ObjectNode payload = mapper.createObjectNode();
            payload.set("sender", sender);
            payload.set("to", mapper.createArrayNode().add(toItem));
            payload.put("subject", asunto);
            payload.put("textContent", contenido);

            String body = mapper.writeValueAsString(payload);

            HttpHeaders headers = new HttpHeaders();
            headers.set("api-key", apyKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(body, headers);
            rt.postForEntity(brevo_url, request, String.class);

        } catch (Exception e) {
            System.err.println(">>> Error al enviar: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
