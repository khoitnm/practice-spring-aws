package org.tnmk.practicespringaws.common.resourcemanagement.resource;

import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceRetrieverException;

public interface ResourceRetriever {
    Resource retrieve(String fileLocation) throws ResourceReadException, ResourceRetrieverException;
}
