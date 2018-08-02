package org.tnmk.practicespringaws.pro02awsjavasdks3.common.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringaws.pro02awsjavasdks3.common.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.pro02awsjavasdks3.common.resource.exception.ResourceRetrieverException;
import org.tnmk.practicespringaws.pro02awsjavasdks3.common.resource.Resource;
import org.tnmk.practicespringaws.pro02awsjavasdks3.common.resource.ResourceRetriever;

import java.io.IOException;

@Service
public class S3ResourceRetriever implements ResourceRetriever {
    private final AmazonS3 amazonS3;

    @Autowired
    public S3ResourceRetriever(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    /**
     * @param fileLocation this is the S3 Protocol URL "s3://myBucket/rootFile.log"
     * @return file content as bytes data as well as the metadata.
     * @throws ResourceRetrieverException error when cannot download the file from S3
     * @throws ResourceReadException error when cannot read the byte data from S3
     */
    @Override
    public Resource retrieve(String fileLocation) throws ResourceReadException, ResourceRetrieverException {
        S3Object s3Object = retrieveS3Object(fileLocation);
        Resource resource = new Resource();
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
            throw new ResourceRetrieverException("Cannot download s3Object "+e.getErrorCode()+": "+e.getErrorMessage(), e, fileLocation);
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
