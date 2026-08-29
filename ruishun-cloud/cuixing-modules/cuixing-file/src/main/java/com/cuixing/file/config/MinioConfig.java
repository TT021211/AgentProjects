//package com.cuixing.file.config;
//
//import io.minio.MinioClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @Author wupuj
// * @create 2024/7/21 10:33
// */
//
//@Configuration
//@RefreshScope
//public class MinioConfig {
//
//    @Value("${minio.url}")
//    private String url;
//    @Value("${minio.accessKey}")
//    private String accessKey;
//    @Value("${minio.secretKey}")
//    private String secretKey;
//    @Value("${minio.bucketName}")
//    private String bucketName;
//
//    @Bean
//    public MinioClient getMinioClient() {
//        MinioClient.Builder endpoint = MinioClient.builder().endpoint(url);
//        MinioClient build = endpoint.credentials(accessKey, secretKey).build();
//        return build;
//    }
//
//}
