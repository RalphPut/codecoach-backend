package com.switchfully.teamair.codecoach.services;

import com.switchfully.teamair.codecoach.domain.entities.Session;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MessagingService {
    public final static String EXCHANGE_NAME = "CodecoachChat";

    @Autowired
    private RabbitTemplate template;

    public void sendMessageToTopic(String message) {
        MessageProperties messageProperties = MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .build();
        template.setExchange(EXCHANGE_NAME);
        template.send(new Message(message.getBytes(), messageProperties));
    }

    public String createRequestSessionMessage(Session session) {
        return String.format("sendTo:%s Session requested by %s %s on %s", session.getCoach().getPhoneNumber(),
                session.getCoachee().getFirstName(), session.getCoachee().getLastName(), session.getDateTime());
    }

    public String createCancelSessionMessage(Session session) {
        return String.format("sendTo:%s Session cancelled by %s %s on %s", session.getCoachee().getPhoneNumber(),
                session.getCoach().getFirstName(), session.getCoach().getLastName(), LocalDate.now());
    }
}
