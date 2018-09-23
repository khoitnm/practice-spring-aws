package org.tnmk.practicespringaws.pro05.common.aws.sqs.share;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class MapAsList<E> extends ArrayList<Object> {

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
