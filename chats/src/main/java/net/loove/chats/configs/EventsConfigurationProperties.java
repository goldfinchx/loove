package net.loove.chats.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "events")
public class EventsConfigurationProperties {

    private String notificationsTopic;


}
