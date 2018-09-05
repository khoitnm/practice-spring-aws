package org.tnmk.practicespringaws.common.resourcemanagement.resource;

import org.apache.commons.io.IOUtils;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceRetrieverException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpResourceRetriever implements ResourceRetriever {
    @Override
    public Resource retrieve(String fileLocation) throws ResourceRetrieverException {
        URL url;
        try {
            url = new URL(fileLocation);
        } catch (MalformedURLException e) {
            throw new ResourceRetrieverException("Malformed URL", e, fileLocation);
        }

        try (InputStream inputStream = url.openStream()) {
            if (inputStream == null) {
                throw new ResourceRetrieverException("File not found", fileLocation);
            }

            byte[] bytes = IOUtils.toByteArray(inputStream);
            String contentType = URLConnection.guessContentTypeFromStream(inputStream);

            Resource resource = new Resource();
            resource.setContentType(contentType);
            resource.setLocation(fileLocation);
            resource.setBytes(bytes);
            return resource;
        } catch (IOException e) {
            throw new ResourceRetrieverException("Cannot download file", e, fileLocation);
        }
    }
}
