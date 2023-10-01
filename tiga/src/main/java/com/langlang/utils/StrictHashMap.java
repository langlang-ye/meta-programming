package com.langlang.utils;

import com.langlang.exceptions.DuplicateKeyException;

import java.util.HashMap;

public class StrictHashMap<V> extends HashMap<String, V> {

    private final String name;

    public StrictHashMap(String name) {
        super();
        this.name = name;
    }

    public V put(String key, V value) {
        if (containsKey(key)) {
            throw new DuplicateKeyException(key + " already contains value for " + key);
        }

        return super.put(key, value);
    }
}
