package org.tnmk.practicespringaws.pro05.aws.sqs.common.messageconverter.grpc;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Parser;
import org.springframework.jms.support.converter.MessageConversionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Deserializer for a Protocol Buffer message payload.
 *
 * @param <T> The Protocol Buffer message type.
 */
public class ProtobufDeserializer<T extends GeneratedMessageV3> {

    protected static final String PARSER_GETTER_METHOD_NAME = "parser";

    protected Parser<T> parser;

    /**
     * For now, the constructor need the @param messageType because the Protobuf parser instance is coupled to the generated message type.
     * It means that each type will have a separated parser instance.
     * </br>
     * If I can find a way to initiate that parser independently, then we don't need the messageType anymore.
     * And then this deserializer could be work with any message type.
     *
     * @param messageType
     */
    public ProtobufDeserializer(Class<T> messageType) {
        parser = getParserFromMessageType(messageType);
    }

    private Parser<T> getParserFromMessageType(Class<T> messageType) {
        if (messageType == null) {
            throw new MessageConversionException("Protocol Buffer message type cannot be null");
        }

        try {
            Method method = messageType.getMethod(PARSER_GETTER_METHOD_NAME);
            return (Parser<T>) method.invoke(null);
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new MessageConversionException("Failed to find Protocol Buffer parser for [" + messageType.getCanonicalName() + "]", e);
        }
    }

    public T deserialize(byte[] data) {
        try {
            T deseralizedData = parser.parseFrom(data);
            return deseralizedData;
        } catch (Exception e) {
            throw new MessageConversionException("Cannot deserialize byte data "+e.getMessage(), e);
        }
    }
}
