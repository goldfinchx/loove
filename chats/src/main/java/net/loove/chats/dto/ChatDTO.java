package net.loove.chats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {

    private Long id;
    private Long[] users;
    private MessageDTO[] messages;

}
