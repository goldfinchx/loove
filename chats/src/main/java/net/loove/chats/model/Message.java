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

    @Builder.Default
    private Long at = System.currentTimeMillis();

    public Long getReceiver() {
        return this.chat.getUsers().stream()
                .filter(user -> !user.equals(this.sender))
                .findFirst()
                .orElse(null);
    }

}
