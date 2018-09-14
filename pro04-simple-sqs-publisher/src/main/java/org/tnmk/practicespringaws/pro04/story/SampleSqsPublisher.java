package org.tnmk.practicespringaws.pro04.story;

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

    public void publish(Object object) {
        defaultJmsTemplate.convertAndSend(queue, object);
    }
}
