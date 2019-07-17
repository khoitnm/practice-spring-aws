package org.tnmk.practicespringaws.pro03.story;

import com.amazonaws.services.s3.AmazonS3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S3ResourceManagement {
    private static final Logger logger = LoggerFactory.getLogger(SampleUploadStory.class);

    private final AmazonS3 amazonS3;

    @Autowired
    public S3ResourceManagement(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }



    public void getBucketLocation(String bucketName){
        String bucketLocation = amazonS3.getBucketLocation(bucketName);
        logger.info(bucketLocation);
    }
}
