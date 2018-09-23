package org.tnmk.practicespringaws.pro05.story.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.stereotype.Component;
import org.tnmk.practicespringaws.pro05.SampleComplicatedMessageProto;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class SampleSqsPublisher {

    private final JmsTemplate defaultJmsTemplate;
    private final String queue;

    @Autowired
    public SampleSqsPublisher(JmsTemplate defaultJmsTemplate, @Value("${sample.sqs.queue}") String queue) {
        this.defaultJmsTemplate = defaultJmsTemplate;
        this.queue = queue;
    }

    public void publish(String correlationId, SampleComplicatedMessageProto sampleMessageProto) {
        defaultJmsTemplate.convertAndSend(queue, sampleMessageProto, new MessagePostProcessor(){
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                // This method doesn't set correlationId into message attribute(header), I don't know why:
                // message.setJMSCorrelationID(correlationId);
                // So I have to use message.setStringProperty(...)
                message.setStringProperty(JmsHeaders.CORRELATION_ID, correlationId);
                return message;
            }
        });
    }
}
