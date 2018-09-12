package org.tnmk.practicespringaws.pro04;

import com.amazonaws.services.sqs.AmazonSQS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsSqsConfig {
    @Bean
    @Override
    public ConnectionFactory connectionFactory(SqsProperties sqsProperties) {
        return createStandardSQSConnectionFactory(sqsProperties);
    }
    
    protected SQSConnectionFactory createStandardSQSConnectionFactory(SqsProperties sqsProperties) {
        AmazonSQS sqsClient = createAmazonSQSClient(sqsProperties);
        ProviderConfiguration providerConfiguration = new ProviderConfiguration();
        sqsProperties.getNumberOfMessagesToPrefetch()
            .ifPresent(providerConfiguration::setNumberOfMessagesToPrefetch);
        return new SQSConnectionFactory(providerConfiguration, sqsClient);
    }
}
