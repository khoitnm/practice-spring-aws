package org.tnmk.practicespringaws.common.resourcemanagement.resource;

import org.apache.commons.io.IOUtils;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceRetrieverException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

public class ClasspathResourceRetriever implements ResourceRetriever {
    @Override
    public Resource retrieve(String classpathFileLocation) throws ResourceReadException, ResourceRetrieverException {
        try (InputStream fileAsStream = this.getClass().getResourceAsStream(classpathFileLocation)) {
            if (fileAsStream == null) {
                throw new ResourceRetrieverException("File not found", classpathFileLocation);
            }
            String contentType = URLConnection.guessContentTypeFromStream(fileAsStream);
            Resource resource = new Resource();
            resource.setContentType(contentType);
            resource.setLocation(classpathFileLocation);
            resource.setBytes(IOUtils.toByteArray(fileAsStream));
            return resource;
        } catch (IOException e) {
            throw new ResourceReadException("Read file error", e, classpathFileLocation);
        }
    }
}
