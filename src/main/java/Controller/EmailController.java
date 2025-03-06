package Controller;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EmailController {


    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;
    private static final String SMTP_USER = "chiheb.medini@isimg.tn";
    private static final String SMTP_PASSWORD = "14278077";


    public static void sendEmail(String recipient, String subject, String content) {

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", String.valueOf(SMTP_PORT));


        Session session = Session.getInstance(props, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
            }
        });

        try {

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(SMTP_USER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);

            System.out.println("Email envoyé avec succès à " + recipient);

        } catch (MessagingException e) {

            System.out.println("Erreur lors de l'envoi de l'email");
            e.printStackTrace();
        }
    }


    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();


    public static void sendScheduledEmail(String recipient, String subject, String content, long delayInSeconds) {

        scheduler.schedule(() -> sendEmail(recipient, subject, content), delayInSeconds, TimeUnit.SECONDS);
    }
}
