package org.example.stortiesnotificationservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "fcm")
public class FcmProperties {

    private String firebaseConfigPath;
    private String projectId;
    private String databaseUrl;
}
