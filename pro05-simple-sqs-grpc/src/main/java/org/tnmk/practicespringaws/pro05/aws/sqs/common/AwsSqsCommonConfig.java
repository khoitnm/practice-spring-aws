package org.tnmk.practicespringaws.pro05.aws.sqs.common;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.google.protobuf.GeneratedMessageV3;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MessageConverter;
import org.tnmk.practicespringaws.common.resourcemanagement.aws.AwsProperties;
import org.tnmk.practicespringaws.pro05.SampleComplicatedMessageProto;
import org.tnmk.practicespringaws.pro05.SampleMessageProto;
import org.tnmk.practicespringaws.pro05.grpc.serialization.ProtobufMessageConverterByClassSimpleName;
import org.tnmk.practicespringaws.pro05.grpc.serialization.ProtobufMessageConverterByQueueTypeMapping;

import java.util.HashMap;
import java.util.Map;

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

    @Bean
    @ConfigurationProperties("aws.sqs.payload-types-map")
    public Map<String, Class<? extends GeneratedMessageV3>> payloadTypesMapByQueueName(){
        return new HashMap<>();
    }

    /**
     * Spring already has this bean by default. This code is just used for showing example how to customize the {@link MessageConverter}.
     *
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        Map<String, Class<? extends GeneratedMessageV3>> payloadTypesMapByQueueName = payloadTypesMapByQueueName();
        return new ProtobufMessageConverterByQueueTypeMapping(payloadTypesMapByQueueName);
    }


}