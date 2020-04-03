package ru.pavlenty.chatBroadcast.message;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private int userID;
    private int fromUserID;
    private String message;
}
