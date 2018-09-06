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
                , port: 21
                , username: "anonymous"
                , password: " "
        )
        ResourceRetriever resourceRetriever = new FtpResourceRetriever(ftpServerProperties)
        Resource resource = resourceRetriever.retrieve("1KB.zip")

        then:
        resource.bytes != null
    }

    @Unroll
    def 'SimpleFtpResourceRetriever throws exception when cannot connect FTP Server'() {
        when:
        FtpServerProperties ftpServerProperties = new FtpServerProperties(
                host: "not.exist.host." + System.nanoTime()
                , port: 21
                , username: "not.exist.user." + System.nanoTime()
                , password: "not.exist.password." + System.nanoTime()
        )
        ResourceRetriever resourceRetriever = new FtpResourceRetriever(ftpServerProperties)
        resourceRetriever.retrieve("not-exist-file-" + System.nanoTime() + ".csv")

        then:
        thrown(ResourceRetrieverException)
    }
}
