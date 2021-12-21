package com.langlang.sqlSession;

import com.langlang.pojo.Configuration;

import java.util.List;

/**
 * @author langlang.ye
 * @date 2021/4/28
 */
public interface SqlSession {

    // 获取 Configuration 实例对象
    Configuration getConfiguration();

    // 新增
    int insert(String statementId, Object... params) throws Exception;

    // 查询所有
    <E> List<E> selectList(String statementId, Object... params) throws Exception;

    // 根据条件查询单个
    <T> T selectOne(String statementId, Object... params) throws Exception;

    // 更新
    int update(String statementId, Object... params) throws Exception;

    // 删除
    int delete(String statementId, Object... params) throws Exception;

    // 为 Dao 接口生成代理实现类
    <T> T getMapper(Class<?> mapperClass);


}
