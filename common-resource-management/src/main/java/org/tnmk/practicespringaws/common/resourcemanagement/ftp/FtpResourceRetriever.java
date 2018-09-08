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
public class FtpResourceRetriever implements ResourceRetriever {
    private static final int DEFAULT_FTP_PORT = 21;

    private final FtpServerProperties ftpServerProperties;

    public FtpResourceRetriever(FtpServerProperties ftpServerProperties) {
        this.ftpServerProperties = ftpServerProperties;
    }

    /**
     * @param fileLocation The file path inside the FTP server, this is not the full FTP URL.
     *                     For example:<br/>
     *                     If you want to download the file "ftp://speedtest.tele2.net/1KB.zip", the fileLocation is "1KB.zip"
     * @return
     * @throws ResourceReadException
     * @throws ResourceRetrieverException
     */
    @Override
    public Resource retrieve(String fileLocation) throws ResourceReadException, ResourceRetrieverException {
        FTPClient ftpClient = new FTPClient();
        try {
            connectFTPServer(ftpClient, ftpServerProperties);
            return retrieveResource(ftpClient, fileLocation);
        } finally {
            closeFTPClient(ftpClient, fileLocation);
        }
    }

    private FTPClient connectFTPServer(FTPClient ftpClient, FtpServerProperties ftpServerProperties) throws ResourceRetrieverException {
        try {
            Integer port = ftpServerProperties.getPort();
            if (port == null) {
                port = DEFAULT_FTP_PORT;
            }
            ftpClient.connect(ftpServerProperties.getHost(), port);
            ftpClient.login(ftpServerProperties.getUsername(), ftpServerProperties.getPassword());
            return ftpClient;
        } catch (IOException e) {
            throw new ResourceRetrieverException("Cannot connect FTP server: " + e.getMessage(), e, ftpServerProperties.getHost() + ":" + ftpServerProperties.getPort());
        }
    }

    private Resource retrieveResource(FTPClient ftpClient, String fileLocation) throws ResourceRetrieverException, ResourceReadException {
        InputStream inputStream = retrieveFileStream(ftpClient, fileLocation);
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

    private InputStream retrieveFileStream(FTPClient ftpClient, String fileLocation) throws ResourceRetrieverException {
        InputStream inputStream;
        try {
            inputStream = ftpClient.retrieveFileStream(fileLocation);
            if (inputStream == null) {
                throw new ResourceRetrieverException("File not found", fileLocation);
            }
            return inputStream;
        } catch (IOException e) {
            throw new ResourceRetrieverException("Cannot download FTP file: " + e.getMessage(), e, fileLocation);
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
