package net.loove.chats.events;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class Event implements Serializable {

    private String body;

    @Builder.Default
    private Long timestamp = System.currentTimeMillis();

    @Builder.Default
    private String service = "chats";

}
