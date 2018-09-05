package org.tnmk.practicespringaws.common.resourcemanagement.ftp;

import org.tnmk.practicespringaws.common.resourcemanagement.resource.Resource;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.ResourceRetriever;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceRetrieverException;

public class FtpResourceRetriever implements ResourceRetriever {

    /**
     * @param fileLocation a FTP link. <br/>
     *                     For example: ftp://ftp.funet.fi/pub/standards/RFC/rfc959.txt where:
     *                     <li>host: "ftp.funet.fi"</li>
     *                     <li>port: default 21</li>
     * @return
     * @throws ResourceReadException
     * @throws ResourceRetrieverException
     */
    @Override
    public Resource retrieve(String fileLocation) throws ResourceReadException, ResourceRetrieverException {
        return null;
    }
}
