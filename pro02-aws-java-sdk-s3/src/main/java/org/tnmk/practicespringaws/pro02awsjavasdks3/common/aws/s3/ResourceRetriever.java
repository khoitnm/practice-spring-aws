package org.tnmk.practicespringaws.pro02awsjavasdks3.common.aws.s3;

import org.tnmk.practicespringaws.pro02awsjavasdks3.common.aws.s3.exception.ResourceReadException;
import org.tnmk.practicespringaws.pro02awsjavasdks3.common.aws.s3.exception.ResourceRetrieverException;

public interface ResourceRetriever {
    Resource retrieve(String fileLocation) throws ResourceReadException, ResourceRetrieverException;
}
