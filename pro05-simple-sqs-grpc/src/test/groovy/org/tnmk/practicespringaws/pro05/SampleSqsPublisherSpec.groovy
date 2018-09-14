package org.tnmk.practicespringaws.pro05

import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.test.annotation.DirtiesContext
import org.tnmk.practicespringaws.pro05.aws.sqs.listener.story.SampleDataAwareness
import org.tnmk.practicespringaws.pro05.aws.sqs.publisher.story.SampleSqsPublisher
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
        SampleMessageProto sampleMessageProto = SampleMessageProto.newBuilder()
            .setValue("sample message value "+System.nanoTime())
            .build();

        when:
        sampleSqsPublisher.publish(sampleMessageProto)

        then:
        pollingConditions.eventually {
            Mockito.verify(mockSampleDataAwareness, Mockito.atLeast(1))
                    .aware(Mockito.any())
        }
    }
}
