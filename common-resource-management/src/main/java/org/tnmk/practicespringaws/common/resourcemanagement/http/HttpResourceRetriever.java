package org.tnmk.practicespringaws.common.resourcemanagement.http;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.Resource;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.ResourceRetriever;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceRetrieverException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * This class could work on both http and https protocols.
 */
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
            Resource resource = new Resource();
            String contentType = URLConnection.guessContentTypeFromStream(inputStream);
            if (StringUtils.isNotBlank(contentType)){
                resource.setContentType(contentType);
            }
            resource.setSourceLocation(fileLocation);
            resource.setBytes(bytes);
            return resource;
        } catch (IOException e) {
            throw new ResourceRetrieverException("Cannot download file", e, fileLocation);
        }
    }
}
