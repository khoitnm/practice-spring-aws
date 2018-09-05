package org.tnmk.practicespringaws.common.resourcemanagement.resource;

import java.util.HashMap;
import java.util.Map;

public class Resource {
    private static final String METADATA_CONTENT_ENCODING = "Content-Encoding";
    private static final String METADATA_CONTENT_TYPE = "Content-Type";
    private static final String METADATA_SOURCE_LOCATION = "Source-Location";
    private byte[] bytes;
    private final Map<String, String> systemMetadata = new HashMap<>();
    private final Map<String, String> userMetadata = new HashMap<>();

    public String getContentEncoding() {
        return this.systemMetadata.get(METADATA_CONTENT_ENCODING);
    }

    public void setContentEncoding(String encoding) {
        this.systemMetadata.put(METADATA_CONTENT_ENCODING, encoding);
    }

    public String getContentType() {
        return this.systemMetadata.get(METADATA_CONTENT_TYPE);
    }

    public void setContentType(String contentType) {
        this.systemMetadata.put(METADATA_CONTENT_TYPE, contentType);
    }

    public String getSourceLocation() {
        return this.userMetadata.get(METADATA_SOURCE_LOCATION);
    }

    public void setSourceLocation(String sourceLocation) {
        this.userMetadata.put(METADATA_SOURCE_LOCATION, sourceLocation);
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Map<String, String> getSystemMetadata() {
        return systemMetadata;
    }

    public Map<String, String> getUserMetadata() {
        return userMetadata;
    }


}
