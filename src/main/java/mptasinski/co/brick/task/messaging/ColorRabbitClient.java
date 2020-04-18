package mptasinski.co.brick.task.messaging;

import io.micronaut.configuration.rabbitmq.annotation.Binding;
import io.micronaut.configuration.rabbitmq.annotation.RabbitClient;
import mptasinski.co.brick.task.dto.ColorMessageDto;

@RabbitClient
public interface ColorRabbitClient {

    @Binding("colors")
    void send(ColorMessageDto colorMessageDto);
}
