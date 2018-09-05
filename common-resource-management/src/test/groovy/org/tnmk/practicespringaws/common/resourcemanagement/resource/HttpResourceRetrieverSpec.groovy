package org.tnmk.practicespringaws.common.resourcemanagement.resource

import org.tnmk.practicespringaws.common.resourcemanagement.http.HttpResourceRetriever
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceRetrieverException
import spock.lang.Specification
import spock.lang.Unroll

class HttpResourceRetrieverSpec extends Specification {

    @Unroll
    def 'HttpResourceRetriever load a file from classpath successfully'() {
        when:
        HttpResourceRetriever resourceRetriever = new HttpResourceRetriever();
        Resource resource = resourceRetriever.retrieve("https://s3.us-east-2.amazonaws.com/practicekevin/loadtest.csv")

        then:
        resource.bytes != null
    }

    @Unroll
    def 'HttpResourceRetriever show throw exception when cannot read the file'() {
        when:
        HttpResourceRetriever resourceRetriever = new HttpResourceRetriever();
        resourceRetriever.retrieve("/no-exist-file-"+System.nanoTime())

        then:
        thrown(ResourceRetrieverException.class)
    }
}
