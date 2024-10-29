package net.loove.chats.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
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
public class Chat {

    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection
    private Set<Long> users;

    @OneToMany
    private Set<Message> messages;

    public Chat(Long... users) {
        this.users = Set.of(users);
        this.messages = new HashSet<>();
    }

}
