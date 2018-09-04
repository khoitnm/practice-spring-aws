package org.tnmk.practicespringaws.pro02awsjavasdks3.sampledomain.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringaws.common.resourcemanagement.aws.s3.S3ResourceRetriever;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.Resource;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceRetrieverException;

@Service
public class SampleStory {
    private static final String SAMPLE_FILE_LOCATION = "s3://practicekevin/loadtest.csv";

    @Autowired
    private S3ResourceRetriever s3ResourceRetriever;

    @EventListener(ApplicationReadyEvent.class)
    public void loadSampleFile() throws ResourceRetrieverException, ResourceReadException {
        Resource resource = s3ResourceRetriever.retrieve(SAMPLE_FILE_LOCATION);

        assert resource != null;
        assert resource.getBytes() != null;
        assert resource.getContentType() != null;
    }
}
