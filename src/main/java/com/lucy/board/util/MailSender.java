package com.lucy.board.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sun.tools.sjavac.Log;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MailSender {

    private final String senderId = "sendmailbot@naver.com";
    private final String senderPwd = "apdlfqkfsong14!@";

    public void send(String receiverEmail, String tempPassword) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.naver.com");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderId, senderPwd);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderId));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            message.setSubject("[Board] 임시비밀번호 발송");
            message.setText("임시비밀번호는 [" + tempPassword + "] 입니다.");
            Transport.send(message);
            log.info("Success Message Send");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
