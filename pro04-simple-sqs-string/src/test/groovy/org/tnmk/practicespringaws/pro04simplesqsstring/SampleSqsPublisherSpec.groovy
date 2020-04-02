package org.tnmk.practicespringaws.pro04simplesqsstring

import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practicespringaws.pro04simplesqsjson.sqs.publisher.story.SampleSqsPublisher

class SampleSqsPublisherSpec extends BaseComponentSpecification {

    @Autowired
    private SampleSqsPublisher sampleSqsPublisher;


    /**
     * This sample code will send message to a real AWS, there's no mock test. So you need to configure your application to be able to connect to real AWS.
     */
    def 'Publish Sqs message successfully'() {
        given:
        String testMessage = "Somestirng" + System.nanoTime();

        when:
        sampleSqsPublisher.publish(testMessage)

        then:
        noExceptionThrown();
    }

}

