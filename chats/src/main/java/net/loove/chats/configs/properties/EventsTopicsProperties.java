package net.loove.chats.configs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "events")
public class EventsTopicsProperties {

    private String chatMessagesTopic;


}
