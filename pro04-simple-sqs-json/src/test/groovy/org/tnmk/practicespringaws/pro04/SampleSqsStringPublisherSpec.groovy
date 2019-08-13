package org.tnmk.practicespringaws.pro04


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import org.tnmk.practicespringaws.pro04.aws.sqs.publisher.story.SampleSqsPublisher

@DirtiesContext
class SampleSqsStringPublisherSpec extends BaseComponentSpecification {

    @Autowired
    private SampleSqsPublisher sampleSqsPublisher;

    def 'Publish Sqs message successfully'() {
        given:
        MessageModel messageModel = new MessageModel();
        messageModel.setMessageId(""+System.nanoTime())
        messageModel.setOidIdsClient(140618);
        messageModel.setOidPropertyClient(1289178)
//        String sampleData = "{\"oidPropertyClient\":1289178,\"oidIdsClient\":140618,\"messageId\":\"22\"}";

        when:
        sampleSqsPublisher.publish(messageModel)

        then:
        noExceptionThrown()
    }

}

