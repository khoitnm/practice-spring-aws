package org.tnmk.practicespringaws.pro03.story;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tnmk.practicespringaws.common.resourcemanagement.aws.s3.S3ResourceUploader;

@Configuration
public class S3ResourceManagementConfig {

    @Bean
    public S3ResourceUploader defaultS3ResourceUploader(@Autowired AmazonS3 amazonS3){
        return new S3ResourceUploader(amazonS3);
    }
}
