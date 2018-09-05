package org.tnmk.practicespringaws.pro03.story;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tnmk.practicespringaws.common.resourcemanagement.aws.AwsProperties;
import org.tnmk.practicespringaws.common.resourcemanagement.aws.s3.AmazonS3SimpleBuilder;

@Configuration
public class AwsConfig {

    @Bean
    @ConfigurationProperties("aws.s3")
    public AwsProperties awsS3Properties() {
        return new AwsProperties();
    }

    @Bean
    public AmazonS3 amazonS3(){
        return new AmazonS3SimpleBuilder().build(awsS3Properties());
    }


}
