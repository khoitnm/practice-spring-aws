package org.tnmk.practicespringaws.pro02awsjavasdks3.sampledomain.story;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tnmk.practicespringaws.common.resourcemanagement.aws.s3.S3ResourceRetriever;

@Configuration
public class S3ResourceManagementConfig {

    @Bean
    public S3ResourceRetriever s3ResourceRetriever(@Autowired AmazonS3 amazonS3){
        return new S3ResourceRetriever(amazonS3);
    }
}
