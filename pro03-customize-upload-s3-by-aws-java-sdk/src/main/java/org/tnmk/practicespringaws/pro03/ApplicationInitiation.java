package org.tnmk.practicespringaws.pro03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceUploadException;
import org.tnmk.practicespringaws.pro03.story.S3ResourceUploaderStory;

@Service
public class ApplicationInitiation {

    private final S3ResourceUploaderStory s3ResourceUploaderStory;

    @Autowired
    public ApplicationInitiation(S3ResourceUploaderStory s3ResourceUploaderStory) {
        this.s3ResourceUploaderStory = s3ResourceUploaderStory;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() throws ResourceUploadException, ResourceReadException {
        s3ResourceUploaderStory.uploadSampleFile();
    }
}
