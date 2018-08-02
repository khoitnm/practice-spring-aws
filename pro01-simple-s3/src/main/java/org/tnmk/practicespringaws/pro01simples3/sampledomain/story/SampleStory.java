package org.tnmk.practicespringaws.pro01simples3.sampledomain.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringaws.pro01simples3.common.s3.S3ResourceRetriever;

import java.io.IOException;

@Service
public class SampleStory {
    @Autowired
    private S3ResourceRetriever s3ResourceRetriever;

    @EventListener(ApplicationReadyEvent.class)
    public void loadSampleFile() throws IOException {
        s3ResourceRetriever.retrieve("s3://practicekevin/orbitz_property_list_100.csv");
    }
}
