package org.tnmk.practicespringaws.pro03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceUploadException;
import org.tnmk.practicespringaws.pro03.story.SampleUploadStory;

@Service
public class ApplicationInitiation {

    private final SampleUploadStory sampleUploadStory;

    @Autowired
    public ApplicationInitiation(SampleUploadStory sampleUploadStory) {
        this.sampleUploadStory = sampleUploadStory;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() throws ResourceUploadException, ResourceReadException {
        sampleUploadStory.uploadSampleFile();
    }
}
