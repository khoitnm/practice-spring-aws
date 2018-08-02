package org.tnmk.practicespringaws.pro02awsjavasdks3.common.resourceretriever;

import org.tnmk.practicespringaws.pro02awsjavasdks3.common.resourceretriever.exception.ResourceReadException;
import org.tnmk.practicespringaws.pro02awsjavasdks3.common.resourceretriever.exception.ResourceRetrieverException;

public interface ResourceRetriever {
    Resource retrieve(String fileLocation) throws ResourceReadException, ResourceRetrieverException;
}
