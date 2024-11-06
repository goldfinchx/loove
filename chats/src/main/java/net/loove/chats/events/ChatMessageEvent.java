package net.loove.chats.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ChatMessageEvent extends Event {

    private Long sender;
    private Long receiver;
    private Long chatId;

}
