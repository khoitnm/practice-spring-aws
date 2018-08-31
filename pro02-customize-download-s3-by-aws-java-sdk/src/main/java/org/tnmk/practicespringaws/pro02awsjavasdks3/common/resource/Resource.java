package org.tnmk.practicespringaws.pro02awsjavasdks3.common.resource;

import java.util.HashMap;
import java.util.Map;

public class Resource {
    private static final String METADATA_CONTENT_ENCODING = "Content-Encoding";
    private static final String METADATA_CONTENT_TYPE = "Content-Type";

    private byte[] bytes;
    private final Map<String, Object> metadata = new HashMap<>();

    public String getContentEncoding() {
        return (String) this.metadata.get(METADATA_CONTENT_ENCODING);
    }

    public void setContentEncoding(String encoding) {
        this.metadata.put(METADATA_CONTENT_ENCODING, encoding);
    }

    public String getContentType() {
        return (String) this.metadata.get(METADATA_CONTENT_TYPE);
    }

    public void setContentType(String contentType) {
        this.metadata.put(METADATA_CONTENT_TYPE, contentType);
    }


    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }
}
