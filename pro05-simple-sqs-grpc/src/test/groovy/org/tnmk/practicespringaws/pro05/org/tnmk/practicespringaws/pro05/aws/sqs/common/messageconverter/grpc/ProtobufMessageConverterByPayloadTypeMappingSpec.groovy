package org.tnmk.practicespringaws.pro05.org.tnmk.practicespringaws.pro05.aws.sqs.common.messageconverter.grpc

import com.amazon.sqs.javamessaging.SQSQueueDestination
import com.google.protobuf.GeneratedMessageV3
import org.tnmk.practicespringaws.pro05.SampleComplicatedMessageProto
import org.tnmk.practicespringaws.pro05.SampleMessageProto
import org.tnmk.practicespringaws.pro05.common.aws.sqs.share.messageconverter.grpc.ProtobufMessageConverterByPayloadTypeMapping
import org.tnmk.practicespringaws.pro05.datafactory.SampleComplicatedProtoFactory
import spock.lang.Specification

import javax.jms.Message

class ProtobufMessageConverterByPayloadTypeMappingSpec extends Specification {
    def 'Serialize protoObject and Deserialize successfully'() {
        given:
        Map<String, Class<? extends GeneratedMessageV3>> payloadTypesMapByQueueName = [
                "queue01": SampleMessageProto.class,
                "queue02": SampleComplicatedMessageProto.class
        ];
        ProtobufMessageConverterByPayloadTypeMapping converter = new ProtobufMessageConverterByPayloadTypeMapping(payloadTypesMapByQueueName);
        SampleComplicatedMessageProto originalProto = SampleComplicatedProtoFactory.constructWithChildren(10);

        when:
        Message convertedMessage = converter.toMessage(originalProto, null);

        //By default, the SQSQueueDestination will be set into Message by Spring framework, but after converted only.
        //So, to test the fromMessage() method, we have to simulate setting SQSQueueDestination into the message.
        convertedMessage.setJMSDestination(new SQSQueueDestination("queue02", "arn://sampleurl/queue02"))
        Object convertedPayload = converter.fromMessage(convertedMessage);
        SampleComplicatedMessageProto convertedProto = (SampleComplicatedMessageProto) convertedPayload;

        then:
        convertedProto.value == originalProto.value;
        convertedProto.getChildrenList() == originalProto.getChildrenList()
    }
}
