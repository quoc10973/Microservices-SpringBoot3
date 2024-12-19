package com.quoc.microservices.service;

import com.quoc.microservices.order.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed")
    public void listenOrderPlaced(OrderPlacedEvent orderPlacedEvent) {
        log.info("Got message from order-placed topic : {}", orderPlacedEvent);
        // Send email to customer
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("microservices@email.com");
            messageHelper.setTo(orderPlacedEvent.getEmail().toString());
            messageHelper.setSubject(String.format("Order %s has been placed!!", orderPlacedEvent.getOrderNumber()));
            messageHelper.setText(String.format(("""
                    Hi %s %s,
                    Your order with orderNumber %s has been placed successfully.
                    
                    Thanks,
                    Microservices Team
                    """),

                    orderPlacedEvent.getFirstName(),
                    orderPlacedEvent.getLastName(),
                    orderPlacedEvent.getOrderNumber()));
        };
        try{
            javaMailSender.send(mimeMessagePreparator);
        } catch (MailException e) {
            log.error("Error while sending email to user : {}", e.getMessage());
            throw new RuntimeException("Error while sending email to user");
        }

    }
}
