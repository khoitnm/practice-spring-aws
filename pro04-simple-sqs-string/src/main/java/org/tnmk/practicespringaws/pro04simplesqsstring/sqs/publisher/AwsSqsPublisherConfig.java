package org.tnmk.practicespringaws.pro04simplesqsstring.sqs.publisher;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

@Configuration
public class AwsSqsPublisherConfig {

    @Bean
    public JmsTemplate jmsTemplate(SQSConnectionFactory sqsConnectionFactory, MessageConverter messageConverter) {
        JmsTemplate jmsTemplate = new JmsTemplate(sqsConnectionFactory);
        jmsTemplate.setMessageConverter(messageConverter);
        return jmsTemplate;
    }
}
