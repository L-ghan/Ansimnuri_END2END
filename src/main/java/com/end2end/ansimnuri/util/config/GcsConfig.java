package com.end2end.ansimnuri.util.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class GcsConfig {
    @Value("${gcs.key.location}")
    private String gcsKeyLocation;

    @Bean
    public Storage getStorage() throws IOException {
        try(InputStream in = new ClassPathResource(gcsKeyLocation).getInputStream()) {
            GoogleCredentials key = GoogleCredentials.fromStream(in);

            return StorageOptions.newBuilder()
                    .setCredentials(key)
                    .build()
                    .getService();
        }
    }
}
