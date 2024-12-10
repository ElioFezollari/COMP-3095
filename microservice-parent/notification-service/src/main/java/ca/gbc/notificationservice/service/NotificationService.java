package ca.gbc.notificationservice.service;

import ca.gbc.orderservice.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final JavaMailSender javaMailSender;
    @KafkaListener(topics="order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent){

        log.info("Recieved message from order-placed topic {}",orderPlacedEvent);

        // Send email
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("comp3095@georgebrown.ca");
            messageHelper.setTo(orderPlacedEvent.getEmail());
            messageHelper.setSubject(String.format("Your order (%s) was placed successfully",orderPlacedEvent.getOrderNumber()));
            messageHelper.setText(String.format("""
                    Good Day %s %s
                    
                    Your order with order number %s was successfully placed.
                    
                    Thank you for your business
                    
                    COMP3095 Staff
                    """,
                    orderPlacedEvent.getFirstName(),
                    orderPlacedEvent.getLastName(),
                    orderPlacedEvent.getOrderNumber()));
        };
        try{
            javaMailSender.send(messagePreparator);
            log.info("Order notification successfully sent!!");
        }catch (MailException e){
            log.error("Exception occured when sending email",e);
            throw new RuntimeException("Exception occurred when sending email",e);
        }
    }
}
