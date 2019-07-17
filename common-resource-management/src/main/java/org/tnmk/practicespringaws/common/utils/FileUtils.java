package org.tnmk.practicespringaws.common.utils;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceReadException;

public class FileUtils {


    public static byte[] loadFileFromClasspath(String classpathFileLocation) throws ResourceReadException {
        try (InputStream inputStream = FileUtils.class.getResourceAsStream(classpathFileLocation)) {
            if (inputStream == null) {
                throw new ResourceReadException("File not found ", classpathFileLocation);
            }
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new ResourceReadException("Read file error", e, classpathFileLocation);
        }
    }


    public static InputStream loadFileInputStreamFromClasspath(String classpathFileLocation) {
        InputStream inputStream = FileUtils.class.getResourceAsStream(classpathFileLocation);
        return inputStream;
    }
}
