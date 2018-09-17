package org.tnmk.practicespringaws.pro05.grpc.serialization;

//import com.google.common.base.Preconditions;

import com.amazon.sqs.javamessaging.message.SQSBytesMessage;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.GeneratedMessageV3;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.messaging.MessageHeaders;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Based on Scala version : https://github.com/gensen/spring-amqp-protobuf/blob/master/src/main/scala/com/gs/amqp/ProtobufMessageConverter.scala
 */
public class ProtobufMessageConverter<T extends GeneratedMessageV3> implements MessageConverter {

    private final static String CONTENT_TYPE_PROTOBUF = "application/protobuf";
    /**
     * @deprecated This property may be redundant.
     */
    @Deprecated
    private final static String CLASS_TYPE = "type";


    private final ProtobufDeserializer<T> protobufDeserializer;

    public ProtobufMessageConverter(Class<T> messageType) {
        this.protobufDeserializer = new ProtobufDeserializer<T>(messageType);
    }


    @Override
    public javax.jms.Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        if (!com.google.protobuf.Message.class.isAssignableFrom(object.getClass())) {
            throw new MessageConversionException("Message wasn't a protobuf");
        } else {
            com.google.protobuf.Message protobuf = (com.google.protobuf.Message) object;
            byte[] byteArray = protobuf.toByteArray();
            SQSBytesMessage sqsBytesMessage = new SQSBytesMessage();
            sqsBytesMessage.setStringProperty(MessageHeaders.CONTENT_TYPE, ProtobufMessageConverter.CONTENT_TYPE_PROTOBUF);
            sqsBytesMessage.setStringProperty(ProtobufMessageConverter.CLASS_TYPE, object.getClass().getTypeName());
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
                if (ProtobufMessageConverter.CONTENT_TYPE_PROTOBUF.equals(contentType)) {
                    byte[] payLoadBytes = sqsBytesMessage.getBodyAsBytes();
                    T parsedProtobufMessage = protobufDeserializer.deserialize(payLoadBytes);
                    return parsedProtobufMessage;
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
}