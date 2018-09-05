package org.tnmk.practicespringaws.common.resourcemanagement.aws.s3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.apache.commons.lang3.StringUtils;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.Resource;

import java.util.Map;

public class ObjectMetadataUtils {
    private static final String META_KEY_SOURCE_LOCATION = "sourceLocation";


    public static ObjectMetadata getObjectMetadata(Resource resource) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(resource.getContentType());
        objectMetadata.setContentEncoding(resource.getContentEncoding());

        Map<String, String> customUserMetadata = resource.getUserMetadata();
        if (StringUtils.isNotBlank(resource.getLocation())) {
            customUserMetadata.put(META_KEY_SOURCE_LOCATION, resource.getLocation());
        }
        objectMetadata.setUserMetadata(customUserMetadata);
        return objectMetadata;
    }
}
