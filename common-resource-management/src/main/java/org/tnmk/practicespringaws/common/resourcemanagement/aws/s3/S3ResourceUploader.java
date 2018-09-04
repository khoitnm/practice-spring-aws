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

    /**
     * THis method will upload file to S3. And if there's already an existing file on S3 with the same name, it will be overwritten.
     * @param resource
     * @param targetLocation
     */
    @Override
    public void upload(Resource resource, String targetLocation) {
        String bucketName = S3Utils.getBucketNameFromLocation(targetLocation);
        String keyName = S3Utils.getObjectNameFromLocation(targetLocation);

        InputStream inputStream = new ByteArrayInputStream(resource.getBytes());
        ObjectMetadata objectMetadata = ObjectMetadataUtils.getObjectMetadata(resource);
        amazonS3.putObject(bucketName, keyName, inputStream, objectMetadata);
    }


}
