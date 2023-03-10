package com.visualization.logtransformer.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseService {
    private FirebaseApp firebaseApp;

    public FirebaseService() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("./serviceAccountKeyTest.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        firebaseApp = FirebaseApp.initializeApp(options);
    }

    public FirebaseApp getFirebaseApp() {
        return firebaseApp;
    }
}
