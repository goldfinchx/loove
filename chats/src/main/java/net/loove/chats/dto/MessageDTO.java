package net.loove.chats.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageDTO {

    private Long chatId;
    private Long sender;
    private Long at;
    private String content;


}
