package org.tnmk.practicespringaws.common.resourcemanagement.aws.s3;

import org.tnmk.practicespringaws.common.resourcemanagement.aws.s3.util.S3Utils;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.ClasspathResourceRetriever;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.Resource;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.ResourceRetriever;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceRetrieverException;

import java.nio.charset.Charset;

/**
 * This class is a fake implementation fo {@link S3ResourceRetriever}.
 * Instead of reading a file from S3, it will extract the fileLocation from S3 URL and try to read a file from the classpath.
 * This class could be helpful for testing in some cases.
 */
public class FakeS3ResourceRetriever implements ResourceRetriever {
    private static final String DEFAULT_CONTENT_ENCODING = Charset.defaultCharset().name();
    private final ClasspathResourceRetriever classpathResourceRetriever;

    public FakeS3ResourceRetriever(ClasspathResourceRetriever classpathResourceRetriever) {
        this.classpathResourceRetriever = classpathResourceRetriever;
    }

    /**
     * @param fileLocation S3URL "s3://whateverBucketName/fileLocationInClassPath"
     * @return
     * @throws ResourceReadException
     * @throws ResourceRetrieverException
     */
    @Override
    public Resource retrieve(String fileLocation) throws ResourceReadException, ResourceRetrieverException {
        String bucketName = S3Utils.getBucketNameFromLocation(fileLocation);
        String objectName = S3Utils.getObjectNameFromLocation(fileLocation);
        String classpathFileLocation = String.format("/%s/%s", bucketName, objectName);
        Resource resource = classpathResourceRetriever.retrieve(classpathFileLocation);
        resource.setSourceLocation(fileLocation);
        resource.setContentEncoding(DEFAULT_CONTENT_ENCODING);
        return resource;
    }
}