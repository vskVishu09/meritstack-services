package com.example.meritstack.security.mail;

import com.example.meritstack.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.mail.SimpleMailMessage;
import java.util.Properties;

@Component
public class MailSender {

    @Autowired
    private JavaMailSender mailSender;

    private AppProperties appProperties;

    public MailSender(AppProperties appProperties) {
        this.appProperties = appProperties;
    }


    public void sendEmail(String from, String to, String cc, String bcc,
                             String subject,
                             String message,
                             String signature) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(from);
        email.setTo(to);
        email.setSubject(subject);

        if (cc != null && !cc.isEmpty()) {
            email.setCc(cc);
        }

        if (bcc != null && !bcc.isEmpty()) {
            email.setBcc(bcc);
        }

        email.setText(message + "\n" + signature);
        mailSender.send(email);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(appProperties.getMail().getHost());
        mailSender.setPort(appProperties.getMail().getPort());

        mailSender.setUsername(appProperties.getMail().getUsername());
        mailSender.setPassword(appProperties.getMail().getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
