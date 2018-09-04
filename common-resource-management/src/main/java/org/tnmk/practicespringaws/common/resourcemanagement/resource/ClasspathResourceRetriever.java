package org.tnmk.practicespringaws.common.resourcemanagement.resource;

import org.apache.commons.io.IOUtils;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceRetrieverException;

import java.io.IOException;
import java.io.InputStream;

public class ClasspathResourceRetriever implements ResourceRetriever {
    @Override
    public Resource retrieve(String classpathFileLocation) throws ResourceReadException, ResourceRetrieverException {
        try (InputStream fileAsStream = this.getClass().getResourceAsStream(classpathFileLocation)) {
            if (fileAsStream == null) {
                throw new ResourceRetrieverException("File not found", classpathFileLocation);
            }
            Resource retrievedFile = new Resource();
            retrievedFile.setBytes(IOUtils.toByteArray(fileAsStream));
            return retrievedFile;
        } catch (IOException e) {
            throw new ResourceReadException("Read file error", e, classpathFileLocation);
        }
    }
}
