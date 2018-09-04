package org.tnmk.practicespringaws.common.resourcemanagement.resource;

public interface ResourceUploader {

    void upload(Resource resource, String targetLocation);
}
