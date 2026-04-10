package org.github.dabson10.sendmail.utilty;

import org.github.dabson10.sendmail.entity.Correo;
import org.springframework.stereotype.Controller;

@Controller
public class ResponseFormat {
    public Correo responseFormat(Correo correo){
        Correo respuesta = new Correo();
        respuesta.setMail(correo.getMail());
        respuesta.setHeader(correo.getHeader());
        respuesta.setBody(correo.getBody());
        respuesta.setName(correo.getName());
        return respuesta;
    }
}