package net.loove.notifications.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "notifications")
public class KafkaTopicsConfigurationProperties {

    private String topics;

}
