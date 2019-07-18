package org.tnmk.practicespringaws.common.resourcemanagement.aws.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.tnmk.practicespringaws.common.resourcemanagement.aws.s3.util.ObjectMetadataUtils;
import org.tnmk.practicespringaws.common.resourcemanagement.aws.s3.util.S3Utils;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.Resource;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.ResourceUploader;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceUploadException;

public class S3ResourceUploader implements ResourceUploader {

    private final AmazonS3 amazonS3;

    public S3ResourceUploader(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    /**
     * THis method will upload file to S3. And if there's already an existing file on S3 with the same name, it will be overwritten.
     *
     * @param resource
     * @param targetLocation
     */
    @Override
    public void upload(Resource resource, String targetLocation) throws ResourceReadException, ResourceUploadException {
        String bucketName = S3Utils.getBucketNameFromLocation(targetLocation);
        String keyName = S3Utils.getObjectNameFromLocation(targetLocation);

        try (InputStream inputStream = new ByteArrayInputStream(resource.getBytes())) {
            ObjectMetadata objectMetadata = ObjectMetadataUtils.getObjectMetadata(resource);
            PutObjectResult result = amazonS3.putObject(bucketName, keyName, inputStream, objectMetadata);
        } catch (AmazonServiceException ex) {
            throw new ResourceUploadException("Cannot upload resource. Error at the server side." + ex.getErrorMessage(), ex, resource.getSourceLocation(), targetLocation);
        } catch (SdkClientException ex) {
            throw new ResourceUploadException("Cannot upload resource. Error at the client side." + ex.getMessage(), ex, resource.getSourceLocation(), targetLocation);
        } catch (IOException e) {
            throw new ResourceReadException("Cannot read the source data " + e.getMessage(), e, resource.getSourceLocation());
        }
    }

}
