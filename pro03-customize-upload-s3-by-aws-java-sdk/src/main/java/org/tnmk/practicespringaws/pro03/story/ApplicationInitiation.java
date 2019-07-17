package org.tnmk.practicespringaws.pro03.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceUploadException;

@Service
public class ApplicationInitiation {
//    private static final String DESTINATION_BUCKET_NAME = "tuna-sbx-aep-media-input-bucket";

    public static final String DESTINATION_BUCKET_NAME = "kevin-test-public-bucket";


    private final S3ResourceManagement s3ResourceManagement;
    private final SampleUploadStory sampleUploadStory;

    @Autowired
    public ApplicationInitiation(S3ResourceManagement s3ResourceManagement, SampleUploadStory sampleUploadStory) {
        this.s3ResourceManagement = s3ResourceManagement;
        this.sampleUploadStory = sampleUploadStory;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() throws ResourceUploadException, ResourceReadException {
//        s3ResourceManagement.getBucketLocation(DESTINATION_BUCKET_NAME);
        sampleUploadStory.uploadSampleFile();
    }
}
