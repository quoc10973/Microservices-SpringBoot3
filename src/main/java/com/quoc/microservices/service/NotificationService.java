package com.quoc.microservices.service;

import com.quoc.microservices.order.events.OrderPlaceEvent;
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
    public void listenOrderPlaced(OrderPlaceEvent orderPlaceEvent) {
        log.info("Got message from order-placed topic : {}", orderPlaceEvent);
        // Send email to customer
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("microservices@email.com");
            messageHelper.setTo(orderPlaceEvent.getEmail());
            messageHelper.setSubject(String.format("Order %s has been placed!!", orderPlaceEvent.getOrderNumber()));
            messageHelper.setText(String.format(("""
                    Hi,
                    Your order with orderNumber %s has been placed successfully.
                    
                    Thanks,
                    Microservices Team
                    """),
                    orderPlaceEvent.getOrderNumber()));
        };
        try{
            javaMailSender.send(mimeMessagePreparator);
        } catch (MailException e) {
            log.error("Error while sending email to user : {}", e.getMessage());
            throw new RuntimeException("Error while sending email to user");
        }

    }
}
