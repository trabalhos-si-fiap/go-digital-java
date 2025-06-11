package com.laroz.interfaces;

import com.laroz.dtos.mail.EmailMessage;

import java.io.IOException;
import java.net.http.HttpResponse;

public interface EmailSender {
    HttpResponse<String> sendEmail(EmailMessage message) throws IOException, InterruptedException;
}
