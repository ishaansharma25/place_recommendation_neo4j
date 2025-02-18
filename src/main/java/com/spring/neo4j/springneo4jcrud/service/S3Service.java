package com.spring.neo4j.springneo4jcrud.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    private static final String BUCKET_NAME = "your-bucket-name";

    public void uploadFile(String fileName, byte[] fileContent) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(fileName)
                .build();

        // Upload the file to S3
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileContent));
    }
}

