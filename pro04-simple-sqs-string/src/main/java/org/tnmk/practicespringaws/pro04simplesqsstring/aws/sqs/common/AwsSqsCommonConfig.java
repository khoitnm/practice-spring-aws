package org.tnmk.practicespringaws.pro04simplesqsstring.aws.sqs.common;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.tnmk.practicespringaws.common.resourcemanagement.aws.AwsProperties;

/**
 * Copied from here:
 * https://github.com/jonyfs/spring-boot-jms-sqs/blob/master/src/main/java/br/com/jonyfs/JmsConfig.java
 */
@Configuration
@EnableJms
public class AwsSqsCommonConfig {


    public AwsSqsCommonConfig(){
    }

    @Bean
    public SQSConnectionFactory sqsConnectionFactory(AwsProperties awsProperties) {
        AmazonSQSClientBuilder amazonSQSClientBuilder = AmazonSQSClientBuilder.standard()
            .withRegion(awsProperties.getRegion())
            .withCredentials(new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(awsProperties.getAccessKey(), awsProperties.getSecretKey())
            ));

        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(new ProviderConfiguration(), amazonSQSClientBuilder);
        return connectionFactory;
    }

}