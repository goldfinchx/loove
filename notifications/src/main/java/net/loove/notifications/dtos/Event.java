package net.loove.notifications.dtos;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an event with user ID, message, and service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event implements Serializable {

    /**
     * The ID of the user associated with the event.
     */
    private Long userId;

    /**
     * The message of the event.
     */
    private String message;

    /**
     * The service associated with the event.
     */
    private String service;

}