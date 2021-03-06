package org.tnmk.practicespringaws.pro05.story.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.tnmk.practicespringaws.pro05.SampleComplicatedMessageProto;

@Component
public class SampleSqsListener {
    private final SampleDataAwareness sampleDataAwareness;

    @Autowired
    public SampleSqsListener(SampleDataAwareness sampleDataAwareness) {
        this.sampleDataAwareness = sampleDataAwareness;
    }

    @JmsListener(destination = "${sample.sqs.queue}")
    public void listenMessage(@Header(JmsHeaders.CORRELATION_ID) String correlationId, @Payload final SampleComplicatedMessageProto sampleData) {
        sampleDataAwareness.aware(correlationId, sampleData);
    }
}