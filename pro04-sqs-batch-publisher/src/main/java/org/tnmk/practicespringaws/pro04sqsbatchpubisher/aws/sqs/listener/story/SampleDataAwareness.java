package org.tnmk.practicespringaws.pro04sqsbatchpubisher.aws.sqs.listener.story;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tnmk.practicespringaws.pro04sqsbatchpubisher.aws.sqs.model.SampleData;

import java.lang.invoke.MethodHandles;

@Component
public class SampleDataAwareness {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void aware(SampleData sampleData){
        log.info("Start processing {} in the queue...", sampleData.getValue());
        runSomethingSlow();
        log.info("Finish processing {} in the queue!!!", sampleData.getValue());
    }

    private void runSomethingSlow(){
        for (int i = 0; i < Integer.MAX_VALUE/300; i++) {
            int[] arr = new int[1000];
        }
    }
}
