package org.tnmk.practicespringaws.pro04

import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practicespringaws.pro04.aws.sqs.publisher.story.SampleSqsPublisher

class SampleSqsPublisherSpec extends BaseComponentSpecification {

    @Autowired
    private SampleSqsPublisher sampleSqsPublisher;


    def 'Publish Sqs message successfully'() {
        given:
        SampleData sampleData = new SampleData();
        sampleData.value = "xxx"+System.nanoTime();

        when:
        sampleSqsPublisher.publish(sampleData)

        then:
        noExceptionThrown()
    }
}
