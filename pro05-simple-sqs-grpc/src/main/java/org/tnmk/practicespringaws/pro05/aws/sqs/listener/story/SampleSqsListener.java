package org.tnmk.practicespringaws.pro05.aws.sqs.listener.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.tnmk.practicespringaws.pro05.SampleComplicatedMessageProto;

//@Component
public class SampleSqsListener {
    private final SampleDataAwareness sampleDataAwareness;

    @Autowired
    public SampleSqsListener(SampleDataAwareness sampleDataAwareness) {
        this.sampleDataAwareness = sampleDataAwareness;
    }

    @JmsListener(destination = "${sample.sqs.queue}")
    public void processMessageA(@Payload final SampleComplicatedMessageProto sampleData) {
        sampleDataAwareness.aware(sampleData);
    }
}