package net.loove.notifications.events;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Event implements Serializable {

    private String body;

    @Builder.Default
    private Long timestamp = System.currentTimeMillis();

    @Builder.Default
    private String service = "notifications";

}
