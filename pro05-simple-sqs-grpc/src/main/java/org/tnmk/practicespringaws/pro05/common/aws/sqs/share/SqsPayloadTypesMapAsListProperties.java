package org.tnmk.practicespringaws.pro05.common.aws.sqs.share;

import com.google.protobuf.GeneratedMessageV3;
import org.springframework.util.ClassUtils;

public class SqsPayloadTypesMapAsListProperties extends MapAsList<Class<? extends GeneratedMessageV3>> {
    protected Class<? extends GeneratedMessageV3> getValue(int ival) {
        String payloadTypeName = (String) super.get(ival);
        Class<? extends GeneratedMessageV3> payloadType = null;
        try {
            payloadType = (Class<? extends GeneratedMessageV3>) ClassUtils.forName(payloadTypeName, this.getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Cannot find class " + payloadTypeName);
        }
        return payloadType;
    }
}
