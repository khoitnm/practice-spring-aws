package org.tnmk.practicespringaws.pro02awsjavasdks3.common.resourceretriever.exception;

/**
 * The exception when retrieving file.
 */
public class ResourceRetrieverException extends Exception {
    private final String resourceLocation;

    public ResourceRetrieverException(String message, String resourceLocation) {
        super(message);
        this.resourceLocation = resourceLocation;
    }

    public ResourceRetrieverException(String message, Throwable throwable, String resourceLocation) {
        super(message, throwable);
        this.resourceLocation = resourceLocation;
    }

    public String getResourceLocation() {
        return resourceLocation;
    }
}
