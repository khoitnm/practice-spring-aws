package org.tnmk.practicespringaws.common.resourcemanagement.ftp

import org.tnmk.practicespringaws.common.resourcemanagement.http.HttpResourceRetriever
import org.tnmk.practicespringaws.common.resourcemanagement.resource.Resource
import org.tnmk.practicespringaws.common.resourcemanagement.resource.ResourceRetriever
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceRetrieverException
import spock.lang.Specification
import spock.lang.Unroll

class FtpResourceRetrieverSpec extends Specification {

    @Unroll
    def 'SimpleFtpResourceRetriever load a file from classpath successfully'() {
        when:
        FtpServerProperties ftpServerProperties = new FtpServerProperties(
                host: "speedtest.tele2.net"
                ,port: 21
                ,username: "anonymous"
                ,password: " "
        )
        ResourceRetriever resourceRetriever = new FtpResourceRetriever(ftpServerProperties)
        Resource resource = resourceRetriever.retrieve("1KB.zip")

        then:
        resource.bytes != null
    }

}
