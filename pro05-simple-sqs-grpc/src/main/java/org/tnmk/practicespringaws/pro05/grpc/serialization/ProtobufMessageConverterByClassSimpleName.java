package org.tnmk.practicespringaws.pro05.grpc.serialization;

//import com.google.common.base.Preconditions;

import com.amazon.sqs.javamessaging.message.SQSBytesMessage;
import com.google.protobuf.GeneratedMessageV3;
import org.reflections.Reflections;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * If either client app or server app doesn't use Java, this class is the best for you.
 * It will try to find the original proto class based on class's simple name only, it doesn't require the full canonical class name.
 * TODO add some cache for ProtbufDeserializer.
 */
public class ProtobufMessageConverterByClassSimpleName implements MessageConverter {

    private final static String CONTENT_TYPE_PROTOBUF = "application/protobuf";
    private final static String MESSAGE_TYPE = "messageType";


    public ProtobufMessageConverterByClassSimpleName() {
    }


    @Override
    public javax.jms.Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        if (!com.google.protobuf.Message.class.isAssignableFrom(object.getClass())) {
            throw new MessageConversionException("Message wasn't a protobuf");
        } else {
            com.google.protobuf.Message protobuf = (com.google.protobuf.Message) object;
            byte[] byteArray = protobuf.toByteArray();
            SQSBytesMessage sqsBytesMessage = new SQSBytesMessage();
            sqsBytesMessage.setStringProperty(MessageHeaders.CONTENT_TYPE, CONTENT_TYPE_PROTOBUF);
            sqsBytesMessage.setStringProperty(MESSAGE_TYPE, object.getClass().getSimpleName());
            sqsBytesMessage.writeBytes(byteArray);
            return sqsBytesMessage;
        }
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {

        if (message instanceof SQSBytesMessage) {
            SQSBytesMessage sqsBytesMessage = (SQSBytesMessage) message;

            try {
                String contentType = message.getStringProperty(MessageHeaders.CONTENT_TYPE);
                if (CONTENT_TYPE_PROTOBUF.equals(contentType)) {
                    Class<?> payloadClass = getPayloadClassOfMessageByClassSimpleName(message);
                    ProtobufDeserializer protobufDeserializer = new ProtobufDeserializer(payloadClass);

                    byte[] payLoadBytes = sqsBytesMessage.getBodyAsBytes();

                    Object payload = protobufDeserializer.deserialize(payLoadBytes);
                    return payload;
                } else {
                    throw new MessageConversionException("Cannot convert, unknown message type %s".format(contentType));
                }
            } catch (Exception e) {
                throw new MessageConversionException("Cannot convert " + e.getMessage());
            }
        } else {
            throw new MessageConversionException("Message must be SQSBytesMessage: " + message);
        }
    }

    private Class<?> getPayloadClassOfMessageByClassSimpleName(Message message) throws ClassNotFoundException, JMSException {
        String payloadClassSimpleName = message.getStringProperty(MESSAGE_TYPE);
        Reflections reflections = new Reflections();
        Set<Class<? extends GeneratedMessageV3>> protoClasses = reflections.getSubTypesOf(GeneratedMessageV3.class);
        Set<Class<? extends GeneratedMessageV3>> protoClassesWithSameSimpleName = protoClasses.stream().filter(
            clazz -> clazz.getSimpleName().equals(payloadClassSimpleName)
        ).collect(Collectors.toSet());

        if (CollectionUtils.isEmpty(protoClassesWithSameSimpleName)) {
            throw new MessageConversionException("Not found any proto file with simple name " + payloadClassSimpleName);
        } else if (protoClassesWithSameSimpleName.size() > 1) {
            throw new MessageConversionException("Expect only one proto file with name " + payloadClassSimpleName + ", but found " + protoClassesWithSameSimpleName);
        } else {
            Class<?> payloadClass = protoClassesWithSameSimpleName.iterator().next();
            return payloadClass;
        }
    }
}