package org.tnmk.practicespringaws.pro03awsjavasdks3.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringaws.pro03awsjavasdks3.common.resource.Resource;
import org.tnmk.practicespringaws.pro03awsjavasdks3.common.aws.s3.S3ResourceRetriever;
import org.tnmk.practicespringaws.pro03awsjavasdks3.common.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.pro03awsjavasdks3.common.resource.exception.ResourceRetrieverException;

@Service
public class SampleStory {
    @Autowired
    private S3ResourceRetriever s3ResourceRetriever;

    @EventListener(ApplicationReadyEvent.class)
    public void loadSampleFile() throws ResourceRetrieverException, ResourceReadException {
        Resource resource = s3ResourceRetriever.retrieve("s3://practicekevin/orbitz_property_list_100.csv");
        resource.getContentType();
    }
}
