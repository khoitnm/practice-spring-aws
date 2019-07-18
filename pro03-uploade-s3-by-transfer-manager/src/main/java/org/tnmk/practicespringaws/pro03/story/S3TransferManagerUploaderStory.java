package org.tnmk.practicespringaws.pro03.story;

import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringaws.common.utils.FileUtils;

@Service
public class S3TransferManagerUploaderStory {
    private static final Logger logger = LoggerFactory.getLogger(S3TransferManagerUploaderStory.class);
    public static final String SAMPLE_SOURCE_FILE_LOCATION = "/application-fullmylocal.yml";
    private static final String SAMPLE_BUCKET = "kevin-test-public-bucket";
    private static final String SAMPLE_FILE_KEY = "application-fullmylocal.yml";

    @Autowired
    private S3TransferManagerUploader s3TransferManagerUploader;

    public void uploadSampleFile() {
        InputStream inputStream = FileUtils.loadFileInputStreamFromClasspath(SAMPLE_SOURCE_FILE_LOCATION);
        s3TransferManagerUploader.uploadByAwsTransfer(inputStream, SAMPLE_BUCKET, SAMPLE_FILE_KEY);
        logger.info("Uploaded file");
    }
}
