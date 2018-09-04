package org.tnmk.practicespringaws.common.resourcemanagement.resource;

import java.util.HashMap;
import java.util.Map;

public class Resource {
    private static final String METADATA_CONTENT_ENCODING = "Content-Encoding";
    private static final String METADATA_CONTENT_TYPE = "Content-Type";

    private byte[] bytes;
    private final Map<String, String> systemMetadata = new HashMap<>();
    private final Map<String, String> userMetadata = new HashMap<>();

    public String getContentEncoding() {
        return (String) this.systemMetadata.get(METADATA_CONTENT_ENCODING);
    }

    public void setContentEncoding(String encoding) {
        this.systemMetadata.put(METADATA_CONTENT_ENCODING, encoding);
    }

    public String getContentType() {
        return (String) this.systemMetadata.get(METADATA_CONTENT_TYPE);
    }

    public void setContentType(String contentType) {
        this.systemMetadata.put(METADATA_CONTENT_TYPE, contentType);
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
