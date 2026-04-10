package org.github.dabson10.sendmail.service;

import org.github.dabson10.sendmail.entity.Correo;

public interface MailSendImpl {
    public void sendMail(Correo correo);
    public void sendResponse(Correo respuesta, Correo correo);
}
