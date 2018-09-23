package org.tnmk.practicespringaws.pro05.common.aws.sqs.share.messageconverter.grpc;

//import com.google.share.base.Preconditions;

import com.amazon.sqs.javamessaging.message.SQSBytesMessage;
import com.google.protobuf.GeneratedMessageV3;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.ClassUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * If both client app and server app use Java, this class is the best. Otherwise, use {@link ProtobufMessageConverterByClassSimpleName}.
 *
 * Based on Scala version : https://github.com/gensen/spring-amqp-protobuf/blob/master/src/main/scala/com/gs/amqp/ProtobufMessageConverter.scala
 * TODO 1. add some cache for ProtbufDeserializer.
 * TODO 2. client app may not use Java, so there will be no full class name in messageType. We may need a mechanism to load full class name form a simplified messageType.
 * To solve the TODO 2, we can try to find the full class name from simple name:
 * https://stackoverflow.com/questions/13215403/finding-a-class-reflectively-by-its-simple-name-alone
 * https://stackoverflow.com/questions/1308961/how-do-i-get-a-list-of-packages-and-or-classes-on-the-classpath
 * https://code.google.com/archive/p/reflections/
 */
public class ProtobufMessageConverterByClassCanonicalName<T extends GeneratedMessageV3> implements MessageConverter {

    private final static String CONTENT_TYPE_PROTOBUF = "application/protobuf";
    private final static String MESSAGE_TYPE = "messageType";


    public ProtobufMessageConverterByClassCanonicalName() { }


    @Override
    public javax.jms.Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        if (!com.google.protobuf.Message.class.isAssignableFrom(object.getClass())) {
            throw new MessageConversionException("Message wasn't a protobuf");
        } else {
            com.google.protobuf.Message protobuf = (com.google.protobuf.Message) object;
            byte[] byteArray = protobuf.toByteArray();
            SQSBytesMessage sqsBytesMessage = new SQSBytesMessage();
            sqsBytesMessage.setStringProperty(MessageHeaders.CONTENT_TYPE, ProtobufMessageConverterByClassCanonicalName.CONTENT_TYPE_PROTOBUF);
            sqsBytesMessage.setStringProperty(MESSAGE_TYPE, object.getClass().getCanonicalName());
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
                if (ProtobufMessageConverterByClassCanonicalName.CONTENT_TYPE_PROTOBUF.equals(contentType)) {
                    Class<?> payloadClass  = getPayloadClassOfMessageByClassCanonicalName(message);
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

    private Class<?> getPayloadClassOfMessageByClassCanonicalName(Message message) throws ClassNotFoundException, JMSException {
        String payloadClassCanoncialName = message.getStringProperty(MESSAGE_TYPE);
        Class<?> payloadClass  = ClassUtils.forName(payloadClassCanoncialName, this.getClass().getClassLoader());
        return payloadClass;
    }
}