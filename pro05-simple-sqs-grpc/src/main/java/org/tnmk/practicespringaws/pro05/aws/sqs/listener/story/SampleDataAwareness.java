package org.tnmk.practicespringaws.pro05.aws.sqs.listener.story;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tnmk.practicespringaws.pro05.SampleComplicatedMessageProto;

import java.lang.invoke.MethodHandles;

@Component
public class SampleDataAwareness {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void aware(SampleComplicatedMessageProto sampleMessageProto){
        log.info("Processing {} in queue a", sampleMessageProto.getValue());
    }
}
