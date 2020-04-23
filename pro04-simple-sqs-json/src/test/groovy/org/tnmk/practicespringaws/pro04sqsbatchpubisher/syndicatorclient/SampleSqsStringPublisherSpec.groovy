package org.tnmk.practicespringaws.pro04sqsbatchpubisher.syndicatorclient


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import org.tnmk.practicespringaws.pro04.aws.sqs.model.SampleData
import org.tnmk.practicespringaws.pro04.aws.sqs.publisher.story.SampleSqsPublisher
import org.tnmk.practicespringaws.pro04sqsbatchpubisher.BaseComponentSpecification

@DirtiesContext
class SampleSqsStringPublisherSpec extends BaseComponentSpecification {

    @Autowired
    private SampleSqsPublisher sampleSqsPublisher;

    def 'Publish Sqs message successfully'() {
        given:
        List<SampleData> messages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SampleData sampleData = new SampleData();
            sampleData.setValue("" + i);
            messages.add(sampleData);
        }

        when:
        sampleSqsPublisher.publishList(messages);

        then:
        noExceptionThrown()
    }

}

