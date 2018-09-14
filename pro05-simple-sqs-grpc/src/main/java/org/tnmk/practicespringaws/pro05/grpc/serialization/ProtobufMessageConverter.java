package org.tnmk.practicespringaws.pro05.grpc.serialization;

//import com.google.common.base.Preconditions;

import com.amazon.sqs.javamessaging.message.SQSBytesMessage;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.messaging.MessageHeaders;
import org.tnmk.practicespringaws.pro05.SampleMessageProto;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Based on Scala version : https://github.com/gensen/spring-amqp-protobuf/blob/master/src/main/scala/com/gs/amqp/ProtobufMessageConverter.scala
 *
 * @author Thibault NORMAND
 * @date 21/03/13
 */
public class ProtobufMessageConverter implements MessageConverter {

    private final static String MESSAGE_TYPE_NAME = "_msg_type_name_";
    private final static String CONTENT_TYPE_PROTOBUF = "application/x-backend-command";

//    private final Descriptors.FileDescriptor fileDescriptor;

    public ProtobufMessageConverter() {
//        this.fileDescriptor = fileDescriptor;
    }

    private String getMessageTypeName(Message msg) throws JMSException {
        Object messageTypeName = msg.getStringProperty(ProtobufMessageConverter.MESSAGE_TYPE_NAME);
        return (String) messageTypeName;
    }

    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        if (!com.google.protobuf.Message.class.isAssignableFrom(object.getClass())) {
            throw new MessageConversionException("Message wasn't a protobuf");
        } else {
            com.google.protobuf.Message protobuf = (com.google.protobuf.Message) object;
            Descriptors.Descriptor descriptor = protobuf.getDescriptorForType();
//            Descriptors.FileDescriptor fileDescriptor = protobuf.getDescriptorForType().getFile();
            byte[] byteArray = protobuf.toByteArray();
            SQSBytesMessage sqsBytesMessage = new SQSBytesMessage();
            sqsBytesMessage.setStringProperty(MessageHeaders.CONTENT_TYPE, ProtobufMessageConverter.CONTENT_TYPE_PROTOBUF);
//            sqsBytesMessage.setStringProperty(ProtobufMessageConverter.MESSAGE_TYPE_NAME, protobuf.getDescriptorForType().getName());
            sqsBytesMessage.setObjectProperty("descriptor", descriptor);
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
//                    String typeName = getMessageTypeName(message);
                    Descriptors.Descriptor descriptor = (Descriptors.Descriptor) message.getObjectProperty("descriptor");
//                    Descriptors.Descriptor descriptor = fileDescriptor.findMessageTypeByName(typeName);
                    byte[] payLoadBytes = sqsBytesMessage.getBodyAsBytes();
                    com.google.protobuf.Message parsedProtobufMessage = DynamicMessage.parseFrom(descriptor, payLoadBytes);
                    return parsedProtobufMessage;
                } else {
                    throw new MessageConversionException("Cannot convert, unknown message type %s".format(getMessageTypeName(message)));
                }
            } catch (Exception e) {
                throw new MessageConversionException("Cannot convert " + e.getMessage());
            }
        } else {
            throw new MessageConversionException("Message must be SQSBytesMessage: " + message);
        }
    }
}