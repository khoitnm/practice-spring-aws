package org.tnmk.practicespringaws.pro04

import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.test.annotation.DirtiesContext
import org.tnmk.practicespringaws.pro04.aws.sqs.listener.story.SampleDataAwareness
import org.tnmk.practicespringaws.pro04.aws.sqs.model.Child
import org.tnmk.practicespringaws.pro04.aws.sqs.model.SampleData
import org.tnmk.practicespringaws.pro04.aws.sqs.publisher.story.SampleSqsPublisher
import spock.lang.Shared
import spock.util.concurrent.PollingConditions

@DirtiesContext
@Import(MockConfiguration.class)
class SampleSqsPublisherSpec extends BaseComponentSpecification {

    @TestConfiguration
    static class MockConfiguration {
        @Primary
        @Bean
        SampleDataAwareness mockSampleDataAwareness() {
            return Mockito.mock(SampleDataAwareness.class);
        }
    }

    @Autowired
    private SampleSqsPublisher sampleSqsPublisher;

    @Autowired
    private SampleDataAwareness mockSampleDataAwareness;

    @Shared
    PollingConditions pollingConditions;

    def setup(){
        pollingConditions = new PollingConditions(timeout: 1)
        Mockito.reset(mockSampleDataAwareness);
    }


    def 'Publish Sqs message successfully'() {
        given:
        Child child = new Child( id: UUID.randomUUID().toString(), value: "Child "+System.nanoTime());

        SampleData sampleData = new SampleData();
        sampleData.value = "new sample data "+System.nanoTime();
        sampleData.children = Arrays.asList(child);
        sampleData.childrenMap = new HashMap<String, Child>();
        sampleData.childrenMap.put("child 1", child);

        when:
        sampleSqsPublisher.publish(sampleData)

        then:
        pollingConditions.eventually {
            Mockito.verify(mockSampleDataAwareness, Mockito.times(1))
                    .aware(Mockito.any())
        }
    }
}
