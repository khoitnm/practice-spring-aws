package org.tnmk.practicespringaws.pro03.story;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Service
public class S3ResourceManagement {
    private static final Logger logger = LoggerFactory.getLogger(SampleUploadStory.class);

    private final AmazonS3 amazonS3;

    @Autowired
    public S3ResourceManagement(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void uploadByAwsTransfer(InputStream inputStream, String bucketName, String keyName){
        ObjectMetadata objectMetadata = new ObjectMetadata();
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(amazonS3).build();
        try {
            Upload xfer = transferManager.upload(bucketName, keyName, inputStream, objectMetadata);
            UploadResult uploadResult = xfer.waitForUploadResult();
            logger.info("Uploaded "+uploadResult.getETag());
        } catch (InterruptedException e) {
            throw new RuntimeException("Cannot upload file "+e.getMessage(), e);
        }
    }

    public void getBucketLocation(String bucketName){
        String bucketLocation = amazonS3.getBucketLocation(bucketName);
        logger.info(bucketLocation);
    }
}
