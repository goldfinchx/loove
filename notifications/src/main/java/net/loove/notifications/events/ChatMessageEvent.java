package net.loove.notifications.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChatMessageEvent extends Event {

    private Long receiver;

    @Override
    public String toString() {
        return "ChatMessageEvent{" +
               "receiver=" + this.receiver +
               "} " + super.toString();
    }

}
