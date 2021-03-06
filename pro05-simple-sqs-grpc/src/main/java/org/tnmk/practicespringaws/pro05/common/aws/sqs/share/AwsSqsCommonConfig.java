package org.tnmk.practicespringaws.pro05.common.aws.sqs.share;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.google.protobuf.GeneratedMessageV3;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MessageConverter;
import org.tnmk.practicespringaws.common.resourcemanagement.aws.AwsProperties;
import org.tnmk.practicespringaws.pro05.common.aws.sqs.share.messageconverter.grpc.ProtobufMessageConverterByPayloadTypeMapping;

/**
 * Copied from here:
 * https://github.com/jonyfs/spring-boot-jms-sqs/blob/master/src/main/java/br/com/jonyfs/JmsConfig.java
 */
@Configuration
@EnableJms
public class AwsSqsCommonConfig {


    public AwsSqsCommonConfig() {
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

    /**
     * Spring already has this bean by default. This code is just used for showing example how to customize the {@link MessageConverter}.
     *
     * @return
     */

    @Bean
    public MessageConverter messageConverter(@Qualifier("payloadTypesMapByQueueName") ClassesMapAsList<Class<? extends GeneratedMessageV3>> payloadTypesMapAsListByQueueName) {
        return new ProtobufMessageConverterByPayloadTypeMapping(payloadTypesMapAsListByQueueName.getMap());
    }

    @Bean
    @ConfigurationProperties("aws.sqs.payload-types-map-by-queue-name")
    public ClassesMapAsList<Class<? extends GeneratedMessageV3>> payloadTypesMapByQueueName() {
        return new ClassesMapAsList<>();
    }

//    @Bean
//    public MessageConverter messageConverter(@Qualifier("payloadTypesMapByQueueName") Map<String, Class<? extends GeneratedMessageV3>> payloadTypesMapByQueueName) {
//        return new ProtobufMessageConverterByPayloadTypeMapping(payloadTypesMapByQueueName);
//    }
//
//    @Bean
//    @ConfigurationProperties("aws.sqs.payload-types-map-by-queue-name")
//    public Map<String, Class<? extends GeneratedMessageV3>> payloadTypesMapByQueueName(){
//        return new HashMap<>();
//    }

}