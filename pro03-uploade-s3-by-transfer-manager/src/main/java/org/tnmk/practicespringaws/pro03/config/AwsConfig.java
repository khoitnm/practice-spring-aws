package org.tnmk.practicespringaws.pro03.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tnmk.practicespringaws.common.resourcemanagement.aws.AwsProperties;

@Configuration
public class AwsConfig {

    @Bean
    @ConfigurationProperties("aws.s3")
    public AwsProperties awsS3Properties() {
        return new AwsProperties();
    }

    /**
     * https://github.com/mapbox/rasterio/issues/1362
     */
    @Bean
    public AmazonS3 anonymousAmazonS3(AwsProperties awsProperties){
        return AmazonS3ClientBuilder.standard()
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new AnonymousAWSCredentials()
                        )
                )
                .withRegion(awsProperties.getRegion())
                .build();
    }

}
