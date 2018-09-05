package org.tnmk.practicespringaws.common.resourcemanagement.ftp;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.Resource;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.ResourceRetriever;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceReadException;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceRetrieverException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * This class helps you to download file from a FTP URL which doesn't require any login information.
 */
public class SimpleFtpResourceRetriever implements ResourceRetriever {
    private static final int DEFAULT_FTP_PORT = 21;

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
        FTPClient ftpClient = new FTPClient();
        try {
            URL url = validateURL(fileLocation);
            connectFTPServer(ftpClient, url);
            return retrieveResource(ftpClient, url, fileLocation);
        } finally {
            closeFTPClient(ftpClient, fileLocation);
        }
    }

    private URL validateURL(String fileLocation) throws ResourceRetrieverException {
        try {
            return new URL(fileLocation);
        } catch (MalformedURLException e) {
            throw new ResourceRetrieverException("Malformed URL: " + e.getMessage(), e, fileLocation);
        }
    }

    private FTPClient connectFTPServer(FTPClient ftpClient, URL url) throws ResourceRetrieverException {
        try {
            int port = url.getPort();
            if (port < 0) {
                port = DEFAULT_FTP_PORT;
            }
            ftpClient.connect(url.getHost(), port);
            ftpClient.login("anonymous","");
            //TODO security ftpClient.login()
            return ftpClient;
        } catch (IOException e) {
            throw new ResourceRetrieverException("Cannot connect FTP server: " + e.getMessage(), e, url.toString());
        }
    }

    private Resource retrieveResource(FTPClient ftpClient, URL url, String fileLocation) throws ResourceRetrieverException, ResourceReadException {
        InputStream inputStream = retrieveFileStream(ftpClient, url);
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            Resource resource = new Resource();
            String contentType = URLConnection.guessContentTypeFromStream(inputStream);
            if (StringUtils.isNotBlank(contentType)) {
                resource.setContentType(contentType);
            }
            resource.setSourceLocation(fileLocation);
            resource.setBytes(bytes);
            return resource;
        } catch (IOException e) {
            throw new ResourceReadException("Cannot read data in file: " + e.getMessage(), e, fileLocation);
        }
    }

    private InputStream retrieveFileStream(FTPClient ftpClient, URL url) throws ResourceRetrieverException {
        InputStream inputStream;
        try {
            inputStream = ftpClient.retrieveFileStream(url.getPath());
            if (inputStream == null) {
                throw new ResourceRetrieverException("File not found", url.toString());
            }
            return inputStream;
        } catch (IOException e) {
            throw new ResourceRetrieverException("Cannot download FTP file: " + e.getMessage(), e, url.toString());
        }
    }

    private void closeFTPClient(FTPClient ftpClient, String fileLocation) throws ResourceRetrieverException {
        try {
            ftpClient.disconnect();
        } catch (IOException e) {
            throw new ResourceRetrieverException("Cannot close FTPClient: " + e.getMessage(), e, fileLocation);
        }
    }
}
