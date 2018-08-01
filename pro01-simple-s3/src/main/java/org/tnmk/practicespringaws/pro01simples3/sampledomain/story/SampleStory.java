package org.tnmk.practicespringaws.pro01simples3.sampledomain.story;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringaws.pro01simples3.common.s3.AwsS3Template;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class SampleStory {
    @Autowired
    private AwsS3Template awsS3Template;

    @EventListener(ApplicationReadyEvent.class)
    public void loadSampleFile() throws IOException {
        awsS3Template.loadFile("s3://myBucket/rootFile.log");
    }
}
