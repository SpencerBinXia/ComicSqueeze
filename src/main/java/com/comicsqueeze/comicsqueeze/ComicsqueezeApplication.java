package com.comicsqueeze.comicsqueeze;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
public class ComicsqueezeApplication {

    public static void main(String[] args) throws FirebaseAuthException {
        SpringApplication.run(ComicsqueezeApplication.class, args);
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("C:/Users/Varun/cse308/comicsqueeze/ComicSqueeze/src/main/resources/comicsqueeze-firebase-adminsdk-cgyos-49457802ed.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://comicsqueeze.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
