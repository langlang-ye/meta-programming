package com.langlang.executor;

import com.langlang.config.BoundSql;
import com.langlang.mapping.ParameterMapping;
import com.langlang.pojo.Configuration;
import com.langlang.pojo.MappedStatement;
import com.langlang.type.TypeAliasRegistry;
import com.langlang.type.TypeHandlerRegistry;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author langlang.ye
 * @date 2021/5/11
 */
public class SimpleExecutor extends BaseExecutor {

    @Override
    protected <E> List<E> doQuery(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object param) throws Exception {
        PreparedStatement preparedStatement = parameterHandler(configuration, mappedStatement, boundSql, param);
        ResultSet resultSet = preparedStatement.executeQuery();

        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getTypeAliasClass(configuration, resultType);
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

        List<Object> list = new ArrayList<>();
        while (resultSet.next()) {

            if (typeHandlerRegistry.hasTypeHandler(resultTypeClass)) {  // 判断基础类型
                // 简单类型
                Object object = resultSet.getObject(1);
                list.add(object);
            } else {
                // jdk9中clazz.newInstance()被Deprecated掉了; 推荐 getDeclaredConstructor().newInstance()
                Object o = resultTypeClass.getDeclaredConstructor().newInstance();
                // 解析元数据
                ResultSetMetaData metaData = resultSet.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnName(i); // 字段名
                    Object value = resultSet.getObject(columnName); // 字段值

                    // 使用反射或者内省, 根据数据库表和实体类的对应关系, 完成封装
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    writeMethod.invoke(o, value);
                }
                list.add(o);
            }
        }
        return (List<E>) list;
    }

    @Override
    protected int doUpdate(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object params) throws Exception {
        PreparedStatement preparedStatement = parameterHandler(configuration, mappedStatement, boundSql, params);
        int result = preparedStatement.executeUpdate();
        return result;
    }

    private Class<?> getTypeAliasClass(Configuration configuration, String parameterType) {
        TypeAliasRegistry typeAliasRegistry = configuration.getTypeAliasRegistry();
        return typeAliasRegistry.resolveAlias(parameterType);
    }

    /**
     * 获取预处理对象, 并绑定查询参数
     * @param configuration
     * @param mappedStatement
     * @param boundSql
     * @param param
     * @return
     * @throws Exception
     */
    public PreparedStatement parameterHandler(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object param) throws Exception {
        Connection connection = configuration.getDataSource().getConnection(); // 获取连接

        System.out.println("==>  Preparing: " + boundSql.getSqlText());
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText()); // 获取预处理对象

        String parameterType = mappedStatement.getParameterType();
        Class<?> clazz = getTypeAliasClass(configuration, parameterType);
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        List<Object> columnValues = new ArrayList<>();
        for (int i = 0; i < parameterMappingList.size(); i++) { // 使用下标遍历, 后面设置参数有用到
            String content = parameterMappingList.get(i).getContent();
            Object value;

            if (typeHandlerRegistry.hasTypeHandler(clazz)) {  // 判断基础类型
                value = param;
            } else if (param instanceof Map) {  // 判断 map 类型
                Map<String, Object> map = (Map) param;
                value = map.get(content);

            } else {
                Field declaredField = clazz.getDeclaredField(content);
                declaredField.setAccessible(true);
                value = declaredField.get(param);
            }
            preparedStatement.setObject(i + 1, value);
            columnValues.add(value);
        }
        String paramsLog = convertColumnValuesToString(columnValues);
        System.out.println("==> Parameters:  " + paramsLog);
        return preparedStatement;
    }

    private String convertColumnValuesToString(List<Object> columnValues) {
        List<Object> useParamList = new ArrayList<Object>(columnValues.size());
        for (Object value : columnValues) {
            if (value == null) {
                useParamList.add("null");
            } else {
                useParamList.add(objectValueString(value) + "(" + value.getClass().getSimpleName() + ")");
            }
        }
        // 集合转为字符串
        String parameters = useParamList.toString();
        return parameters.substring(1, parameters.length() - 1);
    }

    private String objectValueString(Object value) {
        return value.toString();
    }

}
