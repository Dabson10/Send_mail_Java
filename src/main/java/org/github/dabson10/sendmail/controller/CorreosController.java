package org.github.dabson10.sendmail.controller;

import jakarta.validation.Valid;
import org.github.dabson10.sendmail.entity.Correo;
import org.github.dabson10.sendmail.entity.TimeStamp;
import org.github.dabson10.sendmail.service.MailSend;
import org.github.dabson10.sendmail.utilty.ResponseFormat;
import org.github.dabson10.sendmail.utilty.ValidateEmail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/mail")
public class CorreosController {

    private final MailSend mailSend;
    private final ResponseFormat formatMail;
    private final ValidateEmail validateEmail;
    private static final Logger log = LoggerFactory.getLogger(CorreosController.class);
    String cyan = "\u001B[36m";
    String RESET = "\u001B[0m";
    public CorreosController(MailSend mailSend, ResponseFormat formatMail, ValidateEmail validateEmail) {
        this.mailSend = mailSend;
        this.formatMail = formatMail;
        this.validateEmail = validateEmail;
    }

    /**
     * Esta función solo sirve para despertar al servidor y la petición del correo electrónico
     * no tarde tanto en responder.
     */
    @PostMapping("/wakeup")
    public void mensaje(){
        log.info("=======================");
        log.info("Despertando el servidor");
        log.info("=======================");
    }

    /**
     *El siguiente endpoint sirve para mandar un JSON con los atributos mail, header, body y message
     * este endpoint tiene validación para cuando llega un objeto con valores null, cuando hay algún
     * valor vacío, validamos que el correo electrónico pueda recibir y mandar correos electrónicos.
     * @param correo : Objeto con los atributos de mail, header, body y message.
     * @return : Regresará un objeto de tipo Correo y si hubo algún error entonces regresará un mensaje del error
     */
    @PostMapping("/send")
    public ResponseEntity<?> sendCorreo(
            @Valid @RequestBody Correo correo
    ) {
//        Creamos el valor por si existe algún error
        TimeStamp ts = new TimeStamp();

        //Si el objeto es un null entonces regresamos un estado 400
        if (correo == null) {
            ts.setMessage("Se recibió un valor vacío.");
            return new ResponseEntity<>(ts, HttpStatus.BAD_REQUEST);
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
            //Manda un correo electrónico del Remitente a mi destinatario(Yo).
            mailSend.sendMail(correo);
            //Se realiza una respuesta utilizando el mismo sendMail y algunos datos del correo enviado.
            Correo respuesta = formatMail.responseFormat(correo);
            mailSend.sendResponse(respuesta, correo);
            return new ResponseEntity<>(correo, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            log.error("=========================");
            log.error(">>> Exception: {}", e.getMessage());
            log.error("=========================");

            e.printStackTrace();
            ts.setMessage("Se encontró con un error inesperado");
            return new ResponseEntity<>(ts, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
