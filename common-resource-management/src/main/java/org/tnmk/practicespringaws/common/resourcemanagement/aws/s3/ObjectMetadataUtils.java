package org.tnmk.practicespringaws.common.resourcemanagement.aws.s3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.Resource;

public class ObjectMetadataUtils {

    public static ObjectMetadata getObjectMetadata(Resource resource){
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(resource.getContentType());
        objectMetadata.setContentEncoding(resource.getContentEncoding());
        objectMetadata.setUserMetadata(resource.getUserMetadata());
        return objectMetadata;
    }
}
