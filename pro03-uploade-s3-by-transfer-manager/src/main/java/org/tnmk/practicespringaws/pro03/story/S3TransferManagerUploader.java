package org.tnmk.practicespringaws.pro03.story;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S3TransferManagerUploader {
    private static final Logger logger = LoggerFactory.getLogger(S3TransferManagerUploaderStory.class);

    private final AmazonS3 amazonS3;

    @Autowired
    public S3TransferManagerUploader(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void uploadByAwsTransfer(InputStream inputStream, String bucketName, String keyName){
        ObjectMetadata objectMetadata = new ObjectMetadata();
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(amazonS3).build();
        try {
            Upload upload = transferManager.upload(bucketName, keyName, inputStream, objectMetadata);
            UploadResult uploadResult = upload.waitForUploadResult();
            logger.info("Uploaded "+uploadResult.getETag());
        } catch (InterruptedException e) {
            throw new RuntimeException("Cannot upload file "+e.getMessage(), e);
        }
    }
}
