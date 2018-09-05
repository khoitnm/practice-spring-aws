package org.tnmk.practicespringaws.common.resourcemanagement.resource;

import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceUploadException;

public interface ResourceUploader {

    void upload(Resource resource, String targetLocation) throws ResourceUploadException, ResourceReadException;
}
