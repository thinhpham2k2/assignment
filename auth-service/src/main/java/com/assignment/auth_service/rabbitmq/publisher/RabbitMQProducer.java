package com.assignment.auth_service.rabbitmq.publisher;

import com.assignment.auth_service.dto.account.CreateAccountDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    public void sendMessage(CreateAccountDTO dto){

        LOGGER.info("Message sent -> {}", dto.toString());
        rabbitTemplate.convertAndSend(exchange, routingKey, dto);
    }
}
