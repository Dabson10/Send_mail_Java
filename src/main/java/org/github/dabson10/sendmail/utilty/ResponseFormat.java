package org.github.dabson10.sendmail.utilty;

import org.github.dabson10.sendmail.entity.Correo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class ResponseFormat {

    @Value("${spring.owner.name}")
    private String nombre;

    public Correo responseFormat(Correo correo){
        Correo respuesta = new Correo();
        respuesta.setMail(correo.getMail());
        respuesta.setName(correo.getName());
        respuesta.setHeader("Confirmación de: " + correo.getHeader());
        respuesta.setBody(
                "Hola " + correo.getName() + ", \n" +
                        "Muchas gracias por tu mensaje. He recibido tu información de contacto con éxito.\n" +
                        "Te estaré escribiendo pronto para dar seguimiento a tu propuesta o comentario.\n" +
                        "Que tengas un excelente dia.\n\n" +
                        "De: " + nombre + ".\n\n" +
                        ">| Este es un mensaje automático de confirmación. No es necesario responder a este correo directamente |<\n\n" +
                        ">|---- Mensaje Original ----|<\n" +
                        "Asunto: " + correo.getHeader() + "\n" +
                        "Mensaje: " + correo.getBody()
        );
        return respuesta;
    }
}