package org.github.dabson10.sendmail.utilty;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import org.github.dabson10.sendmail.entity.Correo;
import org.springframework.stereotype.Component;

@Component
public class ValidateEmail {
    /**
     * Esta función válida que el correo ingresado sea un correo en el cual se pueden recibir y enviar,
     * ya que al utilizar regex, puedes abarcar a la mayoría de correos, pero siempre existirá una excepción.
     * @param email : Correo electrónico del remitente o destinatario.
     * @return : Si el correo puede enviar y recibir correos lanzará un true, si no se lanzara un false.
     */
    public boolean validateEmail(String email){
        if(email == null || email.isEmpty()){
            return false;
        }
        try{
            InternetAddress emailAddr = new InternetAddress(email, true);
            emailAddr.validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
    }
}
