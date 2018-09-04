package org.tnmk.practicespringaws.common.resourcemanagement.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.Resource;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.ResourceUploader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class S3ResourceUploader implements ResourceUploader {
    private final AmazonS3 amazonS3;

    public S3ResourceUploader(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public void upload(Resource resource, String targetLocation) {
        String bucketName = S3Utils.getBucketNameFromLocation(targetLocation);
        String keyName = S3Utils.getObjectNameFromLocation(targetLocation);

        InputStream myInputStream = new ByteArrayInputStream(resource.getBytes());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setUserMetadata(resource.getMetadata());
        amazonS3.putObject(bucketName, keyName, myInputStream, objectMetadata);
    }


}
