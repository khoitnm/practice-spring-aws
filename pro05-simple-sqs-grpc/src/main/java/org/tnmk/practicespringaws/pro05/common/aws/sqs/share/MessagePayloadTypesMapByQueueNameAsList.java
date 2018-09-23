package org.tnmk.practicespringaws.pro05.common.aws.sqs.share;

import com.google.protobuf.GeneratedMessageV3;
import org.springframework.util.ClassUtils;

/**
 * This class is used to store configuration properties of Mapping between the message queue name and the payloadType.<br/>
 * And this Map is represented as a {@link java.util.List}.
 * @see AbstractMapAsList
 */
public class MessagePayloadTypesMapByQueueNameAsList extends AbstractMapAsList<Class<? extends GeneratedMessageV3>> {

    @Override
    protected Class<? extends GeneratedMessageV3> getValue(int valueIndex) {
        String payloadTypeName = (String) super.get(valueIndex);
        try {
            Class<? extends GeneratedMessageV3> payloadType = (Class<? extends GeneratedMessageV3>) ClassUtils.forName(payloadTypeName, this.getClass().getClassLoader());
            return payloadType;
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Cannot find class " + payloadTypeName);
        }
    }
}
