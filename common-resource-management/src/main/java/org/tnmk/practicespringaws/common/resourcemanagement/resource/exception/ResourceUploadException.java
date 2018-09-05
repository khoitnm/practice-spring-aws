package org.tnmk.practicespringaws.common.resourcemanagement.resource.exception;

public class ResourceUploadException extends Exception {
    private final String sourceLocation;
    private final String destinationLocation;

    public ResourceUploadException(String message, Throwable throwable, String sourceLocation, String destinationLocation) {
        super(message, throwable);
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
    }

    public String getSourceLocation() {
        return sourceLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }
}
