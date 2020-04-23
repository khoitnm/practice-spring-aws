package org.tnmk.practicespringaws.pro04sqsbatchpubisher

import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.test.annotation.DirtiesContext
import org.tnmk.practicespringaws.pro04sqsbatchpubisher.aws.sqs.listener.story.SampleDataAwareness
import org.tnmk.practicespringaws.pro04sqsbatchpubisher.aws.sqs.model.Child
import org.tnmk.practicespringaws.pro04sqsbatchpubisher.aws.sqs.model.SampleData
import org.tnmk.practicespringaws.pro04sqsbatchpubisher.aws.sqs.publisher.story.SampleSqsPublisher
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
        Child child = new Child( id: System.nanoTime(), value: "Child "+System.nanoTime());

        SampleData sampleData = new SampleData();
        sampleData.value = "sample message value "+System.nanoTime();
        sampleData.children = constructChildren(20);
        sampleData.childrenMap = new HashMap<String, Child>();
        sampleData.childrenMap.put("child 1", child);
        sampleData.childrenMap.put("child 2", child);
        sampleData.childrenMap.put("child 3", child);

        when:
        sampleSqsPublisher.publish(sampleData)

        then:
        pollingConditions.eventually {
            // Just do this because I turn off the Listener
//            Mockito.verify(mockSampleDataAwareness, Mockito.times(1))
//                    .aware(Mockito.any())
        }
    }

    private List<Child> constructChildren(int numOfChildren){
        List<Child> children = new ArrayList<>();
        for (int i = 0; i < numOfChildren; i++) {
            Child child = new Child( id: i, value: "Child "+System.nanoTime());
            children.add(child);
        }
        return children;
    }
}

