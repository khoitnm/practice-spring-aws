package org.tnmk.practicespringaws.pro02awsjavasdks3.common.aws.s3;

import org.apache.commons.io.IOUtils;
import org.tnmk.practicespringaws.pro02awsjavasdks3.common.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.pro02awsjavasdks3.common.resource.exception.ResourceRetrieverException;
import org.tnmk.practicespringaws.pro02awsjavasdks3.common.resource.Resource;
import org.tnmk.practicespringaws.pro02awsjavasdks3.common.resource.ResourceRetriever;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * This class is a fake implementation fo {@link S3ResourceRetriever}.
 * Instead of reading a file from S3, it will read a file from the classpath.
 * This class could be helpful for you in some cases.
 */
public class FakeS3ResourceRetriever implements ResourceRetriever {
    private static final String DEFAULT_CHARSET = Charset.defaultCharset().name();

    @Override
    public Resource retrieve(String fileLocation) throws ResourceReadException, ResourceRetrieverException {
        String classpathFileLocation = S3Utils.getObjectNameFromLocation(fileLocation);
        try (InputStream fileAsStream = this.getClass().getResourceAsStream(classpathFileLocation)) {
            if (fileAsStream == null) {
                throw new ResourceRetrieverException("File not found", classpathFileLocation);
            }
            Resource retrievedFile = new Resource();
            retrievedFile.setBytes(IOUtils.toByteArray(fileAsStream));
            retrievedFile.setContentEncoding(DEFAULT_CHARSET);
            return retrievedFile;
        } catch (IOException e) {
            throw new ResourceReadException("Read file error", e, classpathFileLocation);
        }
    }
}