package com.end2end.ansimnuri.util;

import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FileUtil {
    private final Storage storage;
    @Value("${gcs.bucket.name}")
    private String bucketName;
}
