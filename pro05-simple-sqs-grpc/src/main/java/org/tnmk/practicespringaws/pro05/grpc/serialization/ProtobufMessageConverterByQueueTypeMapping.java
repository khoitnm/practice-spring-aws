package org.tnmk.practicespringaws.pro05.grpc.serialization;


import com.amazon.sqs.javamessaging.SQSQueueDestination;
import com.amazon.sqs.javamessaging.message.SQSBytesMessage;
import com.google.protobuf.GeneratedMessageV3;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Map;

/**
 * This converter needs a little bit extra configuration compares to {@link ProtobufMessageConverterByClassSimpleName} or {@link ProtobufMessageConverterByClassCanonicalName}.
 * However, it provides more flexibility for client code.
 */
public class ProtobufMessageConverterByQueueTypeMapping implements MessageConverter {

    private final Map<String, Class<? extends GeneratedMessageV3>> payloadTypesMapByQueueName;

    /**
     * @param payloadTypesMapByQueueName
     */
    public ProtobufMessageConverterByQueueTypeMapping(Map<String, Class<? extends GeneratedMessageV3>> payloadTypesMapByQueueName) {
        this.payloadTypesMapByQueueName = payloadTypesMapByQueueName;
    }


    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        if (!com.google.protobuf.Message.class.isAssignableFrom(object.getClass())) {
            throw new MessageConversionException("Message wasn't a protobuf");
        } else {
            com.google.protobuf.Message protobuf = (com.google.protobuf.Message) object;
            byte[] byteArray = protobuf.toByteArray();
            SQSBytesMessage sqsBytesMessage = new SQSBytesMessage();
            sqsBytesMessage.writeBytes(byteArray);
            return sqsBytesMessage;
        }
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {

        if (message instanceof SQSBytesMessage) {
            SQSBytesMessage sqsBytesMessage = (SQSBytesMessage) message;
            try {
                Class<?> payloadClass = getPayloadClassOfMessageByQueueName(sqsBytesMessage);
                ProtobufDeserializer protobufDeserializer = new ProtobufDeserializer(payloadClass);
                byte[] payLoadBytes = sqsBytesMessage.getBodyAsBytes();
                Object payload = protobufDeserializer.deserialize(payLoadBytes);
                return payload;
            } catch (Exception e) {
                throw new MessageConversionException("Cannot convert to Proto Payload from SQSBytesMessage: " + e.getMessage(), e);
            }
        } else {
            throw new MessageConversionException("Message must be SQSBytesMessage: " + message);
        }
    }

    private Class<?> getPayloadClassOfMessageByQueueName(SQSBytesMessage sqsBytesMessage) throws JMSException {
        SQSQueueDestination destination = (SQSQueueDestination) sqsBytesMessage.getJMSDestination();
        String queueName = destination.getQueueName();
        Class<? extends GeneratedMessageV3> payloadType = payloadTypesMapByQueueName.get(queueName);
        if (payloadType == null) {
            throw new MessageConversionException("Not found any proto file by queue name " + queueName);
        } else {
            return payloadType;
        }
    }
}