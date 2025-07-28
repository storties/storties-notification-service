package org.example.stortiesnotificationservice.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FcmConfig {

    private final FcmProperties fcmProperties;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        if (!FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.getApps().get(0);
        }

        String pathOrUrl = fcmProperties.getFirebaseConfigPath();
        if (pathOrUrl == null || pathOrUrl.isEmpty()) {
            throw new IllegalStateException("Firebase config path is not set");
        }

        InputStream serviceAccount;

        if (pathOrUrl.startsWith("http://") || pathOrUrl.startsWith("https://") || pathOrUrl.startsWith("file:/")) {
            // URL 형식일 때
            URL url = new URL(pathOrUrl);
            serviceAccount = url.openStream();
        } else if (pathOrUrl.startsWith("classpath:")) {
            // 클래스패스 리소스
            String resourcePath = pathOrUrl.substring("classpath:".length());
            serviceAccount = getClass().getClassLoader().getResourceAsStream(resourcePath);
            if (serviceAccount == null) {
                throw new IllegalStateException("Firebase service account file not found in classpath: " + resourcePath);
            }
        } else {
            // 파일 시스템 경로
            java.nio.file.Path path = java.nio.file.Paths.get(pathOrUrl);
            if (!java.nio.file.Files.exists(path)) {
                throw new IllegalStateException("Firebase service account file not found at path: " + pathOrUrl);
            }
            serviceAccount = java.nio.file.Files.newInputStream(path);
        }

        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

        return FirebaseApp.initializeApp(options);
    }
}
