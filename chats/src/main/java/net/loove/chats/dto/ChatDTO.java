package net.loove.chats.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatDTO {

    private Long id;
    private Long[] users;
    private MessageDTO[] messages;

}
