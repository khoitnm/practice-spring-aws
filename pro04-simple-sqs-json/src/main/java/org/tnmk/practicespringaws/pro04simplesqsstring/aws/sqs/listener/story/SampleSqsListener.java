package org.tnmk.practicespringaws.pro04simplesqsstring.aws.sqs.listener.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.tnmk.practicespringaws.pro04simplesqsstring.aws.sqs.model.SampleData;

@Component
public class SampleSqsListener {
    private final SampleDataAwareness sampleDataAwareness;

    @Autowired
    public SampleSqsListener(SampleDataAwareness sampleDataAwareness) {
        this.sampleDataAwareness = sampleDataAwareness;
    }

    @JmsListener(destination = "${sample.sqs.queue}")
    public void processMessageA(@Payload final SampleData sampleData) {
        sampleDataAwareness.aware(sampleData);
    }
}