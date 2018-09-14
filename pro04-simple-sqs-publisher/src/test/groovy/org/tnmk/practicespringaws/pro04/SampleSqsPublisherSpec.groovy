package org.tnmk.practicespringaws.pro04

import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practicespringaws.pro04.story.SampleSqsPublisher

class SampleSqsPublisherSpec extends BaseComponentSpecification {

    @Autowired
    private SampleSqsPublisher sampleSqsPublisher;


    def 'Publish Sqs message successfully'() {
        given:
        SampleData sampleData = new SampleData();
        sampleData.value = "xxx";

        when:
        sampleSqsPublisher.publish(sampleData)

        then:
        noExceptionThrown()
    }
}
