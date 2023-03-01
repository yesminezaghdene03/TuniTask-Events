/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.*;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author user
 */
public class SendMsg {

    public static void sendMail(String recepient) {
        try {
            System.out.println("pre");
            Properties props = new Properties();

            String from = "kiraamv1337@gmail.com";
            String pass = "jlyzpkqqiyvffskk";

            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", 587);
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");


            Session session = Session.getInstance(props, new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication(from, pass);

                }

            });

            Message message = prepareMessage(session, from, recepient);

            assert message != null;
            Transport.send(message);
            System.out.println("message send");
        } catch (MessagingException ex) {
            Logger.getLogger(SendMsg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Message prepareMessage(Session session, String myac, String recepient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myac));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Reservation");


            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("votre reservation est établi avec succés ");

            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            DataSource source = new FileDataSource("C:\\Users\\genop\\OneDrive\\Desktop\\JAVA\\TuniTask-Events\\TuniTask-Events\\src\\resources\\card_image.jpg");




            messageBodyPart2.setDataHandler(new DataHandler(source));

            Multipart multipart = new MimeMultipart();
            messageBodyPart2.setFileName("1.png");
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);
            message.setContent(multipart);
            return message;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

}
