package com.langlang.utils;

import com.langlang.exceptions.DuplicateKeyException;

import java.util.HashMap;

public class StrictHashMap<V> extends HashMap<String, V> {

    private final String name;

    public StrictHashMap(String name) {
        super();
        this.name = name;
    }

    /**
     * HashMap 默认的 put 方法, 遇到相同的 key 时, 会覆盖掉老的 value.
     * 此处结合业务场景: 遇到重复的 key, 抛出异常.
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return
     */
    public V put(String key, V value) {
        if (containsKey(key)) {
            throw new DuplicateKeyException(name + " already contains value for " + key);
        }
        return super.put(key, value);
    }
}
