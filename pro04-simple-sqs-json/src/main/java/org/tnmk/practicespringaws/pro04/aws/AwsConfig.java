package org.tnmk.practicespringaws.pro04.aws;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tnmk.practicespringaws.common.resourcemanagement.aws.AwsProperties;

@Configuration
public class AwsConfig {

    @Bean
    @ConfigurationProperties("aws")
    public AwsProperties awsProperties() {
        return new AwsProperties();
    }


}
