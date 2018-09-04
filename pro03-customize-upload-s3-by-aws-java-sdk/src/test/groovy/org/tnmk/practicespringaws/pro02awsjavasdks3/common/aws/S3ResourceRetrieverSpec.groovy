package org.tnmk.practicespringaws.pro02awsjavasdks3.common.aws

import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practicespringaws.common.resourcemanagement.aws.s3.S3ResourceRetriever
import org.tnmk.practicespringaws.common.resourcemanagement.resource.Resource
import org.tnmk.practicespringaws.pro03.story.SampleUploadStory

class S3ResourceRetrieverSpec extends BaseComponentSpecification {

    @Autowired
    private S3ResourceRetriever s3ResourceRetriever;

    def 'test retrieve resource from s3 successfully'() {
        when:
        Resource resource = s3ResourceRetriever.retrieve(SampleUploadStory.SAMPLE_FILE_LOCATION)

        then:
        resource != null
        resource.bytes != null
        resource.metadata != null
    }
}
