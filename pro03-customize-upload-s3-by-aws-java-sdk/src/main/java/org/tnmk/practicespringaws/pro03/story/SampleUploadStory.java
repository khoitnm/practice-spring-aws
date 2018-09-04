package org.tnmk.practicespringaws.pro03.story;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringaws.common.resourcemanagement.aws.s3.S3ResourceUploader;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.Resource;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceRetrieverException;

import java.io.IOException;
import java.io.InputStream;

@Service
public class SampleUploadStory {
    private static final String SAMPLE_SOURCE_FILE_LOCATION = "/application-fullmylocal.yml";
    private static final String SAMPLE_DESTINATION_FILE_LOCATION = "s3://practicekevin/application-fullmylocal.yml";


    @Autowired
    private S3ResourceUploader s3ResourceUploader;

    @EventListener(ApplicationReadyEvent.class)
    public void uploadSampleFile() throws ResourceRetrieverException, ResourceReadException {
        byte[] bytes = loadFileFromClasspath(SAMPLE_SOURCE_FILE_LOCATION);
        Resource resource = new Resource();
        resource.setBytes(bytes);
        resource.setContentType("yml");
        s3ResourceUploader.upload(resource, SAMPLE_DESTINATION_FILE_LOCATION);
    }

    private byte[] loadFileFromClasspath(String classpathFileLocation) throws ResourceReadException {
        try (InputStream inputStream = this.getClass().getResourceAsStream(classpathFileLocation)) {
            if (inputStream == null) {
                throw new ResourceReadException("File not found ", classpathFileLocation);
            }
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new ResourceReadException("Read file error", e, classpathFileLocation);
        }
    }
}
