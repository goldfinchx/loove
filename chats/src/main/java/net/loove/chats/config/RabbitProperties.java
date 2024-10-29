package net.loove.chats.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitProperties {

    private String queuePrefix;
    private String exchangeName;

}
