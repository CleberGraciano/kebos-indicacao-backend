package br.com.kebos.service.impl;


import br.com.kebos.dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmail(EmailDto emailDto) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setTo(emailDto.getDestinatario());
            helper.setSubject(emailDto.getAssunto());
            helper.setText(emailDto.getMensagem(), true);
//            helper.setFrom(emailFrom);
        } catch (javax.mail.MessagingException ex) {
            throw new RuntimeException(ex);
        }
        mailSender.send(mimeMessage);
    }

    public void sendPasswordResetEmail(String emailTo, String resetLink) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailFrom);
            helper.setTo(emailTo);
            helper.setSubject("Redefinição de senha");
            String content = "Você solicitou a redefinição de sua senha. Clique no link abaixo para redefinir sua senha:\n\n" + resetLink;
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}