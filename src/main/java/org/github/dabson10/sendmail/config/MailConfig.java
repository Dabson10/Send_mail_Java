package org.github.dabson10.sendmail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class MailConfig {

    //La plantilla del mensaje
    @Bean
    public SimpleMailMessage templateMessage(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("example@gmail.com");
        message.setSubject("Cuerpo del correo.");
        return message;
    }


}
