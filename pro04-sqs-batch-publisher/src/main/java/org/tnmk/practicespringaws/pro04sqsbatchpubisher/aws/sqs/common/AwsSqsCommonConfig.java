package org.tnmk.practicespringaws.pro04sqsbatchpubisher.aws.sqs.common;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * Copied from here:
 * https://github.com/jonyfs/spring-boot-jms-sqs/blob/master/src/main/java/br/com/jonyfs/JmsConfig.java
 */
@Configuration
@EnableJms
public class AwsSqsCommonConfig {


    @Value("${aws_region}")
    private String region;

    @Value("${aws_access_key_id}")
    private String accessKeyId;

    @Value("${aws_secret_access_key}")
    private String secretAccessKey;

    @Value("${aws_session_token}")
    private String sessionToken;

    @Bean
    public AwsProperties awsProperties() {
        AwsProperties awsProperties = new AwsProperties();
        awsProperties.setRegion(region);
        awsProperties.setAccessKeyId(accessKeyId);
        awsProperties.setSecretAccessKey(secretAccessKey);
        awsProperties.setSessionToken(sessionToken);
        return awsProperties;
    }

    @Bean
    public AmazonSQS amazonSQS(AmazonSQSClientBuilder amazonSQSClientBuilder) {
        return amazonSQSClientBuilder
//            .withClientConfiguration()
            .build();
    }

    @Bean
    public AmazonSQSClientBuilder amazonSQSClientBuilder(AwsProperties awsProperties) {
        AmazonSQSClientBuilder amazonSQSClientBuilder = AmazonSQSClientBuilder.standard()
            .withRegion(awsProperties.getRegion())
            .withCredentials(new AWSStaticCredentialsProvider(
                new BasicSessionCredentials(awsProperties.getAccessKeyId(), awsProperties.getSecretAccessKey(), awsProperties.getSessionToken())
//                new BasicAWSCredentials(awsProperties.getAccessKey(), awsProperties.getSecretKey())
            ));
        return amazonSQSClientBuilder;
    }

    //This bean is used for JmsTemplate
    @Bean
    public SQSConnectionFactory sqsConnectionFactory(AmazonSQSClientBuilder amazonSQSClientBuilder) {
        ProviderConfiguration providerConfiguration = new ProviderConfiguration();
//        providerConfiguration.setNumberOfMessagesToPrefetch(30);//Still don't see much affect to the performance.
        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(providerConfiguration, amazonSQSClientBuilder);
        return connectionFactory;
    }

    /**
     * Spring already has this bean by default. This code is just used for showing example how to customize the {@link MessageConverter}.
     *
     * @return
     */
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter mappingJackson2MessageConverter = new MappingJackson2MessageConverter();
        mappingJackson2MessageConverter.setObjectMapper(objectMapper);
        mappingJackson2MessageConverter.setTargetType(MessageType.TEXT);
        mappingJackson2MessageConverter.setTypeIdPropertyName("documentType");
        return mappingJackson2MessageConverter;
    }

    @Bean
    public ObjectMapper objectMapper() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_EMPTY);
        builder.dateFormat(new StdDateFormat());
        ObjectMapper objectMapper = builder.build();
        return objectMapper;
    }
}