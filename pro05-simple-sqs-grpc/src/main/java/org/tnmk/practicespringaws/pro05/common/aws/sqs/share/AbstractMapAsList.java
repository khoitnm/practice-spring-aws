package org.tnmk.practicespringaws.pro05.common.aws.sqs.share;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class will help to construct a {@link Map<String, E>} in form as a {@link List}.<br/>
 * For example:<br/>
 * You create a {@link AbstractMapAsList} with following elements:</br>
 * <code>
 * <li>"key01"</li>
 * <li>SampleObject01</li>
 * <li>"key02"</li>
 * <li>SampleObject02</li>
 * </code>
 * <br/>
 * Then the {@link AbstractMapAsList#getMap()} will return a {@link Map<String, E>} with following key-value pairs:
 * <code>
 * <li>"key01": SampleObject01</li>
 * <li>"key02": SampleOjbect02</li>
 * </code>
 * <br/>
 *
 * This class is mostly used for containing configure values for a Map with dynamic key.<br/>
 * For example:</br>
 * In Spring application.yml, you can configure a Map like this:<br/>
 * <code>
 *     <pre>
 *     sample.map.configuration:
 *     - key01
 *     - value01
 *     - ${VAR_KEY_02_PREFIX}_key02
 *     - value02
 *     - ${VAR_KEY_03}
 *     - ${VAR_VALUE_03}
 *     </pre>
 * </code>
 * @param <E>
 */
public abstract class AbstractMapAsList<E> extends ArrayList<Object> {

    public Map<String, E> getMap(){
        Map<String, E> map = new HashMap<>(this.size()/2);
        for (int i = 0; i < this.size() / 2; i++) {
            int ikey = i * 2;
            int ival = i * 2 + 1;
            String key = (String)this.get(ikey);
            E value = getValue(ival);
            map.put(key, value);
        }
        return map;
    }

    protected abstract E getValue(int valueIndex);
}
