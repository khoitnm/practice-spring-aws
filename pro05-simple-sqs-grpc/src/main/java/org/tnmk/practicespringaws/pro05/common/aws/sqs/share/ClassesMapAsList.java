package org.tnmk.practicespringaws.pro05.common.aws.sqs.share;

import org.springframework.util.ClassUtils;

import java.util.List;
import java.util.Map;

/**
 * This class helps to configured a {@link Map<String, Class>} in a form of a {@link List}.<br/>
 * For example: you can configured this Map with following values:<br/>
 * <code>
 * <li>"key01"</li>
 * <li>"a.b.c.d.ClassName01"</li>
 * <li>"key02"</li>
 * <li>"a.b.c.d.ClassName02"</li>
 * </code>
 * Then the {@link #getMap()} will return a {@link Map} with following key-value pairs: <br/>
 * <code>
 *     <li>"key01": a.b.c.d.ClassName01</li>
 *     <li>"key02": a.b.c.d.ClassName02</li>
 * </code>
 * @see AbstractMapAsList
 */
public class ClassesMapAsList<C extends Class> extends AbstractMapAsList<C> {

    @Override
    protected C getValue(int valueIndex) {
        String payloadTypeName = (String) super.get(valueIndex);
        try {
            C payloadType = (C) ClassUtils.forName(payloadTypeName, this.getClass().getClassLoader());
            return payloadType;
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Cannot find class " + payloadTypeName);
        }
    }
}
