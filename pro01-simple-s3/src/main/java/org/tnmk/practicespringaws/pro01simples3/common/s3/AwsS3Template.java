package org.tnmk.practicespringaws.pro01simples3.common.s3;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AwsS3Template {
    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * @param filePath for example: "s3://myBucket/rootFile.log"
     * @return
     */
    public byte[] loadFile(String filePath) throws IOException {
        Resource resource = resourceLoader.getResource(filePath);
        return IOUtils.toByteArray(resource.getInputStream());
    }
}
