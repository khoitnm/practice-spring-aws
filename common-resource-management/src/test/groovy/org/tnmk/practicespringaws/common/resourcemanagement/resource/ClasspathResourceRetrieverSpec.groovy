package org.tnmk.practicespringaws.common.resourcemanagement.resource

import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceRetrieverException
import spock.lang.Specification
import spock.lang.Unroll

class ClasspathResourceRetrieverSpec extends Specification {

    @Unroll
    def 'ClasspathResourceRetriever load a file from classpath successfully'() {
        when:
        ClasspathResourceRetriever classpathResourceRetriever = new ClasspathResourceRetriever();
        Resource resource = classpathResourceRetriever.retrieve("/testdata/test-sample-file.csv")

        then:
        resource.bytes != null
    }

    @Unroll
    def 'ClasspathResourceRetriever show throw exception when cannot read the file'() {
        when:
        ClasspathResourceRetriever classpathResourceRetriever = new ClasspathResourceRetriever();
        classpathResourceRetriever.retrieve("/no-exist-file-"+System.nanoTime())

        then:
        thrown(ResourceRetrieverException.class)
    }
}
