package com.langlang.sqlSession;

public interface Cache {

    String getId();

    void putObject(Object key, Object value);

    Object getObject(Object key);

    Object removeObject(Object key);

    void clear();

    int getSize();

}
