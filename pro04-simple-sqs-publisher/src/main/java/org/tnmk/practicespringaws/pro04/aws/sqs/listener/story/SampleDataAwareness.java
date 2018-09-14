package org.tnmk.practicespringaws.pro04.aws.sqs.listener.story;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tnmk.practicespringaws.pro04.aws.sqs.model.SampleData;

import java.lang.invoke.MethodHandles;

@Component
public class SampleDataAwareness {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void aware(SampleData sampleData){
        log.info("Processing {} in queue a", sampleData.getValue());
    }
}
