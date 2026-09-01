package com.movelink.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {

        try {

            if (FirebaseApp.getApps().isEmpty()) {

                InputStream serviceAccount =
                        getClass().getClassLoader()
                                .getResourceAsStream("firebase-service-account.json");

                if (serviceAccount == null) {
                    System.out.println("Firebase service account not found. Skipping Firebase initialization.");
                    return;
                }

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);

                System.out.println("Firebase initialized successfully.");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}