package org.tnmk.practicespringaws.common.resourcemanagement.aws.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.io.IOUtils;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.Resource;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.ResourceRetriever;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceRetrieverException;

import java.io.IOException;

public class S3ResourceRetriever implements ResourceRetriever {
    private final AmazonS3 amazonS3;

    public S3ResourceRetriever(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    /**
     * @param fileLocation this is the S3 Protocol URL "s3://myBucket/rootFile.log"
     * @return file content as bytes data as well as the metadata.
     * @throws ResourceRetrieverException error when cannot download the file from S3
     * @throws ResourceReadException      error when cannot read the byte data from S3
     */
    @Override
    public Resource retrieve(String fileLocation) throws ResourceReadException, ResourceRetrieverException {
        S3Object s3Object = retrieveS3Object(fileLocation);
        Resource resource = new Resource();
        resource.setLocation(fileLocation);
        resource.setContentEncoding(s3Object.getObjectMetadata().getContentEncoding());
        resource.setContentType(s3Object.getObjectMetadata().getContentType());
        resource.setBytes(getBytesContent(s3Object));
        return resource;
    }

    private S3Object retrieveS3Object(String fileLocation) throws ResourceRetrieverException {
        String bucketName = S3Utils.getBucketNameFromLocation(fileLocation);
        String key = S3Utils.getObjectNameFromLocation(fileLocation);
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        try {
            S3Object s3Object = amazonS3.getObject(getObjectRequest);
            return s3Object;
        } catch (AmazonS3Exception e) {
            throw new ResourceRetrieverException("Cannot download s3Object " + e.getErrorCode() + ": " + e.getErrorMessage(), e, fileLocation);
        } catch (SdkClientException e) {
            throw new ResourceRetrieverException("Cannot download s3Object " + e.getMessage(), e, fileLocation);
        }
    }

    private byte[] getBytesContent(S3Object s3Object) throws ResourceReadException {
        try {
            return IOUtils.toByteArray(s3Object.getObjectContent());
        } catch (IOException e) {
            throw new ResourceReadException("Cannot read bytes array from s3Object", s3Object.toString());
        }
    }

}
