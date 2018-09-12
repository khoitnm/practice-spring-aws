package org.tnmk.practicespringaws.pro04.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class SqsPublisher {
    @Autowired
    protected JmsTemplate defaultJmsTemplate;

    public void publish(String queue, Object object) {
        defaultJmsTemplate.convertAndSend(queue, object);
    }
}
