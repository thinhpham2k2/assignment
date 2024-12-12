package com.assignment.auth_service.rabbitmq.consumer;

import com.assignment.auth_service.dto.account.CreateAccountDTO;
import com.assignment.auth_service.mapper.AccountMapper;
import com.assignment.auth_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQConsumer {

    private final AccountMapper accountMapper;

    private final AccountRepository accountRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.consumer.name}"})
    public void consume(CreateAccountDTO dto) {

        LOGGER.info("Received message -> {}", dto.toString());
        accountRepository.save(accountMapper.createToEntity(dto));
    }
}
