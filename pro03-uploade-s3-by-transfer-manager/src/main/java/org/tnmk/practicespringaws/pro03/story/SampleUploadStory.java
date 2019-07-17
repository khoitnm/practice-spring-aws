package org.tnmk.practicespringaws.pro03.story;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringaws.common.resourcemanagement.aws.s3.S3ResourceUploader;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceUploadException;

import java.io.IOException;
import java.io.InputStream;

@Service
public class SampleUploadStory {
    private static final Logger logger = LoggerFactory.getLogger(SampleUploadStory.class);
    public static final String SAMPLE_SOURCE_FILE_LOCATION = "/application-fullmylocal.yml";
    private static final String SAMPLE_DESTINATION_FILE_LOCATION = "s3://kevin-test-public-bucket/application-fullmylocal.yml";

    @Autowired
    private S3ResourceManagement s3ResourceManagement;

    @Autowired
    private S3ResourceUploader s3ResourceUploader;

    public void uploadSampleFile() throws ResourceReadException, ResourceUploadException {
//
//        byte[] bytes = loadFileFromClasspath(SAMPLE_SOURCE_FILE_LOCATION);
//        Resource resource = new Resource();
//        resource.setBytes(bytes);
//        resource.setContentType("application/x-yaml");
//        resource.setContentEncoding("UTF-8");
//        resource.getUserMetadata().put("custom-metadata-01-key", "custom-metadata-01-value");

        InputStream inputStream = loadFileInputStreamFromClasspath(SAMPLE_SOURCE_FILE_LOCATION);
        s3ResourceManagement.uploadByAwsTransfer(inputStream, "kevin-test-public-bucket", "some-file-name");
//        s3ResourceUploader.upload(resource, SAMPLE_DESTINATION_FILE_LOCATION);
        logger.info("Uploaded file");
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

    private InputStream loadFileInputStreamFromClasspath(String classpathFileLocation) {
        InputStream inputStream = this.getClass().getResourceAsStream(classpathFileLocation);
        return inputStream;
    }
}
