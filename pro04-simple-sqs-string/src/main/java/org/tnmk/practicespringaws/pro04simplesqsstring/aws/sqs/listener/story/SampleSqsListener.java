package org.tnmk.practicespringaws.pro04simplesqsstring.aws.sqs.listener.story;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class SampleSqsListener {
    public static final Logger logger = LoggerFactory.getLogger(SampleSqsListener.class);

    @JmsListener(destination = "${sample.sqs.queue}")
    public void processMessageA(@Payload final String receiveMessage) {
        System.out.println("Receive message" + receiveMessage);
    }
}