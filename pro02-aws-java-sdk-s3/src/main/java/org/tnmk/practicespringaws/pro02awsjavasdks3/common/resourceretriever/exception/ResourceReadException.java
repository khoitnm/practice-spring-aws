package org.tnmk.practicespringaws.pro02awsjavasdks3.common.resourceretriever.exception;

/**
 * The exception when reading content of a resource.
 */
public class ResourceReadException extends Exception {
    private final String resourceLocation;

    public ResourceReadException(String message, String resourceLocation) {
        super(message);
        this.resourceLocation = resourceLocation;
    }

    public ResourceReadException(String message, Throwable throwable, String resourceLocation) {
        super(message, throwable);
        this.resourceLocation = resourceLocation;
    }

    public String getResourceLocation() {
        return resourceLocation;
    }

}
