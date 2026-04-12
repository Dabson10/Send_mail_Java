package org.github.dabson10.sendmail.controller;

import jakarta.validation.Valid;
import org.github.dabson10.sendmail.entity.Correo;
import org.github.dabson10.sendmail.entity.TimeStamp;
import org.github.dabson10.sendmail.service.MailSend;
import org.github.dabson10.sendmail.utilty.ResponseFormat;
import org.github.dabson10.sendmail.utilty.ValidateEmail;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://cv-juan-david.netlify.app/")
@RequestMapping("/mail")
public class CorreosController {

    private final MailSend mailSend;
    private final ResponseFormat formatMail;
    private final ValidateEmail validateEmail;

    public CorreosController(MailSend mailSend, ResponseFormat formatMail, ValidateEmail validateEmail) {
        this.mailSend = mailSend;
        this.formatMail = formatMail;
        this.validateEmail = validateEmail;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendCorreo(
            @Valid @RequestBody Correo correo
    ) {
//        Creamos el valor por si existe algún error
        TimeStamp ts = new TimeStamp();

        //Si el objeto es un null entonces regresamos un estado 400
        if (correo == null) {
            return new ResponseEntity<>((HttpHeaders) null, HttpStatus.BAD_REQUEST);
        }

        //Validamos que no se guarden datos vacíos en el objeto.
        if (correo.validateData()) {
            ts.setMessage("Ingreso valores incorrectos o no relleno correctamente el formulario.");
            return new ResponseEntity<>(ts, HttpStatus.UNPROCESSABLE_CONTENT);
        }
        //Validamos que el correo ingresado pueda enviar y recibir datos.
        if (!validateEmail.validateEmail(correo.getMail())) {
            //Si regresa un false entonces regresamos un código de estado.
            ts.setMessage("El correo electrónico ingresado no es valido.");
            return new ResponseEntity<>(ts, HttpStatus.UNPROCESSABLE_CONTENT);
        }
        try {
            System.out.println(">|<");
            //Manda un correo electrónico del Remitente a mi destinatario(Yo).
            mailSend.sendMail(correo);
            //Se realiza una respuesta utilizando el mismo sendMail y algunos datos del correo enviado.
            Correo respuesta = formatMail.responseFormat(correo);
            mailSend.sendResponse(respuesta, correo);
            return new ResponseEntity<>(correo, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            System.err.println(">>> Exception: " + e.getMessage());
            e.printStackTrace();
            ts.setMessage("Se encontró con un error inesperado");
            return new ResponseEntity<>(ts, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
