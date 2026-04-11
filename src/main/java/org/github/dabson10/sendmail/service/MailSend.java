package org.github.dabson10.sendmail.service;

import lombok.Setter;
import org.github.dabson10.sendmail.entity.Correo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

//@AllArgsConstructor
@Setter
@Service
public class MailSend implements MailSendImpl {

    private final MailSender mailSender;
    private final SimpleMailMessage templateMessage;
    public SimpleMailMessage msg;
    @Value("${spring.mail.username}")
    private String correoPersonal;
    @Value("${spring.owner.name}")
    private String nombre;
    @Value("${spring.mail.port}")
    private String puerto;
    @Value("${spring.mail.password}")
    private String clave;


    public MailSend(MailSender mailSender, SimpleMailMessage templateMessage) {
        this.mailSender = mailSender;
        this.templateMessage = templateMessage;
        this.msg = new SimpleMailMessage(this.templateMessage);
    }

    /**
     * Esta función sirve para enviar un correo electrónico de un Remitente a un destinatario.
     *
     * @param correo : parametro que cuenta con datos fundamentales como:
     *               -Mail: Correo del Remitente.
     *               -Asunto o Header: Mensaje con el asunto del correo.
     *               -Body o cuerpo: Cuerpo del correo.
     *               -Name: Nombre del Remitente.
     */
    @Override
    public void sendMail(Correo correo) {
        try {
            msg.setTo(correoPersonal);
            msg.setSubject(correo.getHeader());
            msg.setReplyTo(correo.getMail());
            msg.setText(correo.getBody() + "\n" + correo.getMail());

            System.out.println("Datos: " +
                    "\nNombre: " + nombre +
                    "\nCorreo: " + correoPersonal +
                    "\nClave: " + clave +
                    "\nMail port: " + puerto);
            this.mailSender.send(msg);
        } catch (MailException mailEx) {
            System.err.println(mailEx.getMessage());
        }catch(Exception e){
            System.err.println(">>> ERROR CRÍTICO AL ENVIAR CORREO:");
            System.err.println("Causa: " + e.getMessage());
            e.printStackTrace(); // Esto te dará el "stack trace" completo en Render
        }
    }

    /**
     * Esta función está muy relacionada con {@link #sendMail(Correo)} ya que al instante de enviar el mail del Remitente
     * al Destinatario se realiza una respuesta por parte del destinatario al Remitente, por lo que los datos
     * son prácticamente lo mismo solo que cambia el destinatario y el remitente.
     *
     * @param respuesta : A diferencia del correo este parametro es contiene los datos
     *                  del reclutador o persona que relleno el formulario.
     * @param correo    : Correo a donde fue enviado principalmente, este contiene los datos que recibiré
     *                  en mi correo.
     */
    @Override
    public void sendResponse(Correo respuesta, Correo correo) {
        try {
            msg.setTo(respuesta.getMail());
            msg.setSubject("Confirmación de: " + respuesta.getHeader());
            msg.setReplyTo(correoPersonal);
            msg.setText("Hola " + respuesta.getName() + ", \n" +
                    "Muchas gracias por tu mensaje. He recibido tu información de contacto con éxito.\n" +
                    "Te estaré escribiendo pronto para dar seguimiento a tu propuesta o comentario.\n" +
                    "Que tengas un excelente dia.\n\n" +
                    "De: " + nombre + ".\n\n" +
                    ">| Este es un mensaje automático de confirmación. No es necesario responder a este correo directamente |<\n\n" +
                    ">|---- Mensaje Original ----|<\n" +
                    "Asunto: " + correo.getHeader() +
                    "\nMensaje: " + correo.getBody());

            this.mailSender.send(msg);
        } catch (MailException mailEx) {
            System.err.println(mailEx.getMessage());
        }catch(Exception e ){
            System.err.println(">>> ERROR CRÍTICO AL ENVIAR CORREO:");
            System.err.println("Causa: " + e.getMessage());
            e.printStackTrace(); // Esto te dará el "stack trace" completo en Render
        }
    }


}
