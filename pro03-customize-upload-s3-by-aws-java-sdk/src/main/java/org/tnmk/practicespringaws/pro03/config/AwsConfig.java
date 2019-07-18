package org.tnmk.practicespringaws.pro03.config;

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
    public AmazonS3 amazonS3(AwsProperties awsProperties) {
        /**
         * There are some cases you can upload file to S3 bucket without AWS credentials.
         * - The s3 bucket is public for writing
         * - Or your machine in it's whitelist setting
         */
        return new AmazonS3SimpleBuilder().buildAnonymous(awsProperties.getRegion());
        /**
         * If your application run on the EC2 which connect directly with S3 bucket, you can use this:
         */
//        return AmazonS3ClientBuilder.standard()
//                .withCredentials(InstanceProfileCredentialsProvider.getInstance())
//                .build();
        /**
         * If your application connect via IAMRole, use this:
         */
//        return AmazonS3ClientBuilder.defaultClient();
        /**
         * Otherwise, you need to provide full AWS Access Key and SecretKey which can be generated from an IAMUser
         */
//        return new AmazonS3SimpleBuilder().build(awsProperties);
    }
}
