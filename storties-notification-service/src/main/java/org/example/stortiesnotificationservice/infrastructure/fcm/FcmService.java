package org.example.stortiesnotificationservice.infrastructure.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class FcmService {

    public String execute(FcmMessageRequest requestDto) {
        // 사용자의 Firebase 토큰 값을 조회
        String userFirebaseToken = "ddd";

        // 메시지 구성
        Message message = Message.builder()
            .putData("title", requestDto.title())
            .putData("content", requestDto.body())
            .setToken(userFirebaseToken) // 조회한 토큰 값을 사용
            .build();

        try {
            // 메시지 전송
            String response = FirebaseMessaging.getInstance().send(message);
            return "Message sent successfully: " + response;
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }
}