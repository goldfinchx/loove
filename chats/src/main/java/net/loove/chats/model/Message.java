package net.loove.chats.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Chat chat;

    private Long sender;

    private String content;

    private Instant at;

    public Message(Chat chat, Long sender, String content) {
        this.chat = chat;
        this.sender = sender;
        this.content = content;
        this.at = Instant.now();
    }

}
