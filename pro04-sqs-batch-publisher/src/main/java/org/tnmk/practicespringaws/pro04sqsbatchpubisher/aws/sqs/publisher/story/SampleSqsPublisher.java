package org.tnmk.practicespringaws.pro04sqsbatchpubisher.aws.sqs.publisher.story;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SampleSqsPublisher {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * By default, the maximum batch size of SQS is 10.
     * If you want more than 10, you have to change the config of AwsSQSClient
     */
    public static final int BATCH_SIZE = 10;
    private final AmazonSQS amazonSQS;
    private final String queue;
    private final String queueUrl;

    private final ObjectMapper objectMapper;

    @Autowired
    public SampleSqsPublisher(AmazonSQS amazonSQS, @Value("${sample.sqs.queue}") String queue, ObjectMapper objectMapper) {
        this.amazonSQS = amazonSQS;
        this.queue = queue;
        this.queueUrl = amazonSQS.getQueueUrl(queue).getQueueUrl();
        log.info("QueueURL: {}", queueUrl);
        this.objectMapper = objectMapper;
    }

    public void publish(Object object) {
        log.info("Send message: " + object);
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
            .withQueueUrl(queueUrl)
            .withMessageBody(toMessageBody(object))
            .withMessageAttributes(jmsMessageAttribute(object));
        amazonSQS.sendMessage(sendMessageRequest);
    }

    public void publishList(List<Object> objects) {
        List<List<Object>> objectBatches = ListUtils.partition(objects, BATCH_SIZE);
        int messageIndex = -1;
        int batchIndex = 0;
        for (List<Object> objectBatch : objectBatches) {
            List<SendMessageBatchRequestEntry> messageEntries = objectBatch.stream()
                .map(o -> toMessageEntry(o))
                .collect(Collectors.toList());

            SendMessageBatchRequest sendMessageBatchRequest = new SendMessageBatchRequest(queueUrl, messageEntries);
            amazonSQS.sendMessageBatch(sendMessageBatchRequest);
            messageIndex += objectBatch.size();
            log.info("Finished sending messages [{}], batch[{}]: {} ", messageIndex, batchIndex, objectBatch);
            batchIndex++;
        }
    }

    private SendMessageBatchRequestEntry toMessageEntry(Object object) {
        SendMessageBatchRequestEntry entry = new SendMessageBatchRequestEntry()
            .withId(UUID.randomUUID().toString())
            .withMessageBody(toMessageBody(object))
            .withMessageAttributes(jmsMessageAttribute(object))
            //.withMessageGroupId()//Messages that belong to the same message group are always processed one by one, in a strict order relative to the message group (however, messages that belong to different message groups might be processed out of order).
            ;
        return entry;
    }

    private Map<String, MessageAttributeValue> jmsMessageAttribute(Object object) {
        Map<String, MessageAttributeValue> attributes = new HashMap<>();
        MessageAttributeValue attJmsSqsMessageType = new MessageAttributeValue()
            .withDataType("String")
            .withStringValue("text");
        MessageAttributeValue attDocumentType = new MessageAttributeValue()
            .withDataType("String")
            .withStringValue(object != null ? object.getClass().getCanonicalName() : null);
        attributes.put("JMS_SQSMessageType", attJmsSqsMessageType);
        attributes.put("documentType", attDocumentType);

//        attributes.put("JMS_SQSMessageType", messageAttributeValue);
        return attributes;
    }

    private String toMessageBody(Object object) {
//        return String.valueOf(object) + System.nanoTime();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
