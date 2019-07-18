package org.tnmk.practicespringaws.pro03.story;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringaws.common.resourcemanagement.aws.s3.S3ResourceUploader;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.Resource;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceUploadException;
import org.tnmk.practicespringaws.common.utils.FileUtils;

@Service
public class S3ResourceUploaderStory {
    private static final Logger logger = LoggerFactory.getLogger(S3ResourceUploaderStory.class);
    public static final String SAMPLE_SOURCE_FILE_LOCATION = "/application.yml";
    private static final String SAMPLE_DESTINATION_FILE_LOCATION = "s3://kevin-test-public-bucket/application.yml";

    @Autowired
    private S3ResourceUploader s3ResourceUploader;

    public void uploadSampleFile() throws ResourceReadException, ResourceUploadException {

        byte[] bytes = FileUtils.loadFileFromClasspath(SAMPLE_SOURCE_FILE_LOCATION);
        Resource resource = new Resource();
        resource.setBytes(bytes);
        resource.setContentType("application/x-yaml");
        resource.setContentEncoding("UTF-8");
        resource.getUserMetadata().put("custom-metadata-01-key", "custom-metadata-01-value");

        s3ResourceUploader.upload(resource, SAMPLE_DESTINATION_FILE_LOCATION);
        logger.info("Uploaded file");
    }

}
