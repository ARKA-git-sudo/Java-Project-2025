package com.example.Payment;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String toEmail, String name, String course, double amount){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Payment Successful - Course Enrollment Confirmation");

        message.setText("Hi " + name + ",\n\n" +
                "Thank you for enrolling in our course: " + course + ".\n" +
                "Your payment has been successfully received and your enrollment is confirmed.\n\n" +
                "A receipt has been attached for your reference.\n\n" +
                "If you have any questions, feel free to contact our support team.\n\n" +
                "Best Regards,\n" +
                "XXX Team\n" +
                "-------------------------------------------------\n" +
                "This is an automated message, please do not reply directly to this email.");

        javaMailSender.send(message);
    }
}



//    public void sendEmail(String toEmail, String name, String course, double amount, File pdfReceipt) {
//        try {
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setTo(toEmail);
//            helper.setSubject("Payment Successful - Course Enrollment Confirmation");
//
//
//            String body = "Hi " + name + ",\n\n" +
//                    "Thank you for enrolling in our course: " + course + ".\n" +
//                    "Your payment of ₹" + amount + " has been successfully received and your enrollment is confirmed.\n\n" +
//                    "A receipt has been attached for your reference.\n\n" +
//                    "If you have any questions, feel free to contact our support team.\n\n" +
//                    "Best Regards,\n" +
//                    "XXX Team\n" +
//                    "-------------------------------------\n" +
//                    "This is an automated message, please do not reply directly to this email.";
//
//            helper.setText(body);
//
//            if (pdfReceipt != null && pdfReceipt.exists()) {
//                FileSystemResource file = new FileSystemResource(pdfReceipt);
//                helper.addAttachment("Receipt.pdf", file);
//            }
//
//            javaMailSender.send(message);
//            System.out.println("Mail sent successfully with PDF!");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
//}