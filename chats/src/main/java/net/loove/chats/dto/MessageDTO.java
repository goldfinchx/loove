package net.loove.chats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    private Long id;
    private Long receiver;
    private Long sender;
    private Long at;
    private String content;


}
