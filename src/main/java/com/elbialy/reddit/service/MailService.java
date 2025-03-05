package com.elbialy.reddit.service;

import com.elbialy.reddit.exceptions.SpringRedditException;
import com.elbialy.reddit.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender javaSender;
    private final MailContentBuilder mailContentBuilder;
    @Async
   public void sendEmail(NotificationEmail notificationEmail){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,"utf-8");
            messageHelper.setFrom("reddit@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()),true);
        };
        try {
            javaSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException ex){
            throw new SpringRedditException("Exception occurred when sending mail to"+notificationEmail.getRecipient());
        }
    }

}
