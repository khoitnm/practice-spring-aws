package org.tnmk.practicespringaws.pro04simplesqsjson.sqs.publisher.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SampleSqsPublisher {

    private final JmsTemplate defaultJmsTemplate;
    private final String queue;

    @Autowired
    public SampleSqsPublisher(JmsTemplate defaultJmsTemplate, @Value("${sample.sqs.queue}") String queue) {
        this.defaultJmsTemplate = defaultJmsTemplate;
        this.queue = queue;
    }

    public void publish(String message) {
        defaultJmsTemplate.convertAndSend(queue, message);
    }
}
