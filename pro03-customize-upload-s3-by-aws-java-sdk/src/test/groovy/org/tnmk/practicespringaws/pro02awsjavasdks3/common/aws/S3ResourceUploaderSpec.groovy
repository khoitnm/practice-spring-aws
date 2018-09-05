package org.tnmk.practicespringaws.pro02awsjavasdks3.common.aws

import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practicespringaws.common.resourcemanagement.aws.s3.S3ResourceUploader
import org.tnmk.practicespringaws.common.resourcemanagement.resource.ClasspathResourceRetriever
import org.tnmk.practicespringaws.common.resourcemanagement.resource.Resource

class S3ResourceUploaderSpec extends BaseComponentSpecification {

    @Autowired
    private S3ResourceUploader s3ResourceUploader;


    def 'Upload resource to s3 successfully'() {
        given:
        ClasspathResourceRetriever classpathResourceRetriever = new ClasspathResourceRetriever();
        Resource resource = classpathResourceRetriever.retrieve("/testdata/test-sample-file.csv");

        when:
        s3ResourceUploader.upload(resource, "s3://practicekevin/test-sample-uploaded-file.yml")

        then:
        noExceptionThrown()
    }
}
