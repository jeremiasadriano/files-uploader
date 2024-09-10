package com.jeremias.uploaderfiles.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

@Configuration
public class AmazonConfig {
    @Value("${aws.secret.key.id}")
    private String secretKeyId;
    @Value("${aws.secret.access.key}")
    private String accessKey;

    @Bean
    public S3TransferManager transferManager() {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(secretKeyId, accessKey);
        S3AsyncClient s3AsyncClient = S3AsyncClient.builder()
                .multipartEnabled(true)
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .region(Region.US_EAST_1)
                .build();

        return S3TransferManager.builder()
                .s3Client(s3AsyncClient)
                .build();
    }
}
