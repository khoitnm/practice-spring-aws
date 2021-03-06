package org.tnmk.practicespringaws.common.resourcemanagement.aws.s3.util;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.apache.commons.lang3.StringUtils;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.Resource;

import java.util.Map;

public class ObjectMetadataUtils {

    public static ObjectMetadata getObjectMetadata(Resource resource) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        if (StringUtils.isNotBlank(resource.getContentType())) {
            objectMetadata.setContentType(resource.getContentType());
        }

        if (StringUtils.isNotBlank(resource.getContentEncoding())) {
            objectMetadata.setContentEncoding(resource.getContentEncoding());
        }

        Map<String, String> customUserMetadata = resource.getUserMetadata();
        objectMetadata.setUserMetadata(customUserMetadata);
        return objectMetadata;
    }
}
