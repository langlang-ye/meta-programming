package com.langlang.pojo;

import com.langlang.sqlSession.DefaultSqlSession;
import com.langlang.sqlSession.MapperRegistry;
import com.langlang.type.TypeAliasRegistry;
import com.langlang.type.TypeHandlerRegistry;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author langlang.ye
 * @date 2021/4/28
 */
public class Configuration {

    private DataSource dataSource;

    // key: statementId; value: 封装的mappedStatement 对象
    Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    private MapperRegistry mapperRegistry = new MapperRegistry();

    private TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry(this);

    private TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry(this);

    public void addMapper(Class<?> classType) {
        mapperRegistry.addMapper(classType);
    }

    public void addMappers(String packageName) {
        // 扫描包下面所有的 Mapper 接口, 添加到 mapperRegistry
        mapperRegistry.addMappers(packageName);
    }

    public void addTypeAlias(String packageName) {
        typeAliasRegistry.addTypeAliases(packageName);
    }

    public void addTypeAlias(String alias, String classType) throws ClassNotFoundException {
        typeAliasRegistry.addTypeAlias(alias, classType);
    }

    public <T> T getMapper(Class<?> type, DefaultSqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }

    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }

    public void setTypeAliasRegistry(TypeAliasRegistry typeAliasRegistry) {
        this.typeAliasRegistry = typeAliasRegistry;
    }

    public TypeHandlerRegistry getTypeHandlerRegistry() {
        return typeHandlerRegistry;
    }

    public void setTypeHandlerRegistry(TypeHandlerRegistry typeHandlerRegistry) {
        this.typeHandlerRegistry = typeHandlerRegistry;
    }
}
