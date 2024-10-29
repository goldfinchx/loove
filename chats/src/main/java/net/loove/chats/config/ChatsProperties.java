package net.loove.chats.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "chats")
public class ChatsProperties {

    private String queuePrefix;

}
