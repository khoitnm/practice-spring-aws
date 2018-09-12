package org.tnmk.practicespringaws.pro04;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.messaging.handler.annotation.support.PayloadArgumentResolver;
import org.tnmk.practicespringaws.common.resourcemanagement.aws.AwsProperties;

import javax.jms.Session;
import java.util.Collections;

@Configuration
public class AwsSqsConfig {

    @Bean
    public SQSConnectionFactory sqsConnectionFactory(AwsProperties awsProperties) {
        SQSConnectionFactory connectionFactory =
            new SQSConnectionFactory.Builder()
                .withRegion(Region.getRegion(Regions.fromName(awsProperties.getRegion())))
                .withAWSCredentialsProvider(
                    new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(
                            awsProperties.getAccessKey(),
                            awsProperties.getSecretKey()
                        )
                    )
                )
                .build();
        return connectionFactory;
    }


    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(SQSConnectionFactory sqsConnectionFactory) {
        DefaultJmsListenerContainerFactory factory =
            new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(sqsConnectionFactory);
        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setConcurrency("3-10");
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return factory;
    }

    @Bean
    public JmsTemplate defaultJmsTemplate(SQSConnectionFactory sqsConnectionFactory) {
        return new JmsTemplate(sqsConnectionFactory);
    }


    @Bean
    public QueueMessageHandlerFactory queueMessageHandlerFactory() {
        QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();

        //set strict content type match to false
        messageConverter.setStrictContentTypeMatch(false);
        factory.setArgumentResolvers(Collections.<HandlerMethodArgumentResolver>singletonList(new PayloadArgumentResolver(messageConverter)));
        return factory;
    }
}
