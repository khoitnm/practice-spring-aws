package org.tnmk.practicespringaws.common.resourcemanagement.resource.exception;

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
