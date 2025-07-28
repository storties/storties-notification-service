package org.example.stortiesnotificationservice.infrastructure.fcm;

public record FcmMessageRequest(

    String userId,

    String title,

    String body
) {

}
