package org.tnmk.practicespringaws.pro04.aws.sqs.publisher.story;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Component
public class SampleSqsPublisher {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final JmsTemplate defaultJmsTemplate;
    private final String queue;

    @Autowired
    public SampleSqsPublisher(JmsTemplate defaultJmsTemplate, @Value("${sample.sqs.queue}") String queue) {
        this.defaultJmsTemplate = defaultJmsTemplate;
        this.queue = queue;
    }

    public void publish(Object object) {
        log.info("Send message: " + object);
        defaultJmsTemplate.convertAndSend(queue, object);
    }

    @Transactional
    public void publishList(List<Object> objects) {
        for (Object object : objects) {
            log.info("Send message: " + object);
            defaultJmsTemplate.convertAndSend(queue, object);
        }
    }
}
