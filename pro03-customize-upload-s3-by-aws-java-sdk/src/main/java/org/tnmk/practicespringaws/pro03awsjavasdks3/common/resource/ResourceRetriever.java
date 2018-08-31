package org.tnmk.practicespringaws.pro03awsjavasdks3.common.resource;

import org.tnmk.practicespringaws.pro03awsjavasdks3.common.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.pro03awsjavasdks3.common.resource.exception.ResourceRetrieverException;

public interface ResourceRetriever {
    Resource retrieve(String fileLocation) throws ResourceReadException, ResourceRetrieverException;
}
