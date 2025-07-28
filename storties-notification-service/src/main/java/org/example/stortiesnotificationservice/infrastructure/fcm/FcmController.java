package org.example.stortiesnotificationservice.infrastructure.fcm;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fcm")
public class FcmController {

    private final FcmService fcmService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String sendMessage(@RequestBody FcmMessageRequest requestDto) {
        return fcmService.execute(requestDto);
    }
}
