package org.tnmk.practicespringaws.common.resourcemanagement.ftp

import org.tnmk.practicespringaws.common.resourcemanagement.http.HttpResourceRetriever
import org.tnmk.practicespringaws.common.resourcemanagement.resource.Resource
import org.tnmk.practicespringaws.common.resourcemanagement.resource.ResourceRetriever
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceRetrieverException
import spock.lang.Specification
import spock.lang.Unroll

class SimpleFtpResourceRetrieverSpec extends Specification {

    @Unroll
    def 'SimpleFtpResourceRetriever load a file from classpath successfully'() {
        when:
        ResourceRetriever resourceRetriever = new SimpleFtpResourceRetriever()
//        Resource resource = resourceRetriever.retrieve("ftp://ftp.funet.fi/pub/standards/RFC/rfc959.txt")
        Resource resource = resourceRetriever.retrieve("ftp://speedtest.tele2.net/1KB.zip")

        then:
        resource.bytes != null
    }

    @Unroll
    def 'SimpleFtpResourceRetriever show throw exception when cannot read the file'() {
        when:
        HttpResourceRetriever resourceRetriever = new HttpResourceRetriever();
        resourceRetriever.retrieve("/no-exist-file-"+System.nanoTime())

        then:
        thrown(ResourceRetrieverException.class)
    }
}
