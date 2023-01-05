package com.langlang.config;

import com.langlang.annotations.Delete;
import com.langlang.annotations.Insert;
import com.langlang.annotations.Select;
import com.langlang.annotations.Update;
import com.langlang.pojo.Configuration;
import com.langlang.pojo.MappedStatement;
import com.langlang.pojo.SqlCommandType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MapperAnnotationBuilder {

    private final Set<Class<? extends Annotation>> sqlAnnotationTypes = new HashSet<Class<? extends Annotation>>();
    private final Set<Class<? extends Annotation>> sqlProviderAnnotationTypes = new HashSet<Class<? extends Annotation>>();

    private final Configuration configuration;
    private final Class<?> type;

    public MapperAnnotationBuilder(Configuration configuration, Class<?> type) {
        this.configuration = configuration;
        this.type = type;

        sqlAnnotationTypes.add(Select.class);
        sqlAnnotationTypes.add(Insert.class);
        sqlAnnotationTypes.add(Update.class);
        sqlAnnotationTypes.add(Delete.class);


//        sqlProviderAnnotationTypes.add(SelectProvider.class);
//        sqlProviderAnnotationTypes.add(InsertProvider.class);
//        sqlProviderAnnotationTypes.add(UpdateProvider.class);
//        sqlProviderAnnotationTypes.add(DeleteProvider.class);
    }

    public void parse() {
        Method[] methods = type.getMethods();
        for (Method method : methods) {
            parseStatement(method);
        }
    }

    void parseStatement(Method method) {
        Class<?> parameterTypeClass = getParameterType(method);
        String parameterTypeClassName = null;
        if (parameterTypeClass != null) {
            parameterTypeClassName = parameterTypeClass.getName();
        }
        String sqlSource = getSqlSourceFromAnnotations(method, parameterTypeClass);

        if (sqlSource != null) {
            SqlCommandType sqlCommandType = getSqlCommandType(method);
            Class<?> returnType = getReturnType(method);
            String returnTypeName = returnType.getName();

            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(method.getName());
            mappedStatement.setParameterType(parameterTypeClassName);
            mappedStatement.setResultType(returnTypeName);
            mappedStatement.setSql(sqlSource);
            mappedStatement.setSqlCommandType(sqlCommandType);

            String key = type.getName() + "." + method.getName();
            configuration.getMappedStatementMap().put(key, mappedStatement);
        }

    }

    private SqlCommandType getSqlCommandType(Method method) {
        Class<? extends Annotation> type = getSqlAnnotationType(method);
        return SqlCommandType.valueOf(type.getSimpleName().toUpperCase(Locale.ENGLISH));
    }

    private Class<?> getReturnType(Method method) {
        Class<?> returnType = TypeParameterResolver.resolveReturnType(method, type);
        return returnType;
    }

    private String getSqlSourceFromAnnotations(Method method, Class<?> parameterType) {

        try {
            Class<? extends Annotation> sqlAnnotationType = getSqlAnnotationType(method);
            Class<? extends Annotation> sqlProviderAnnotationType = getSqlProviderAnnotationType(method);

            if (sqlAnnotationType != null) {
                /*if (sqlProviderAnnotationType != null) {
                    throw new Exception("You cannot supply both a static SQL and SqlProvider to method named " + method.getName());
                }*/
                Annotation sqlAnnotation = method.getAnnotation(sqlAnnotationType);
                final String[] strings = (String[]) sqlAnnotation.getClass().getMethod("value").invoke(sqlAnnotation);
                return buildSqlSourceFromStrings(strings, parameterType);
            } else if (sqlProviderAnnotationType != null) {
                Annotation sqlProviderAnnotation = method.getAnnotation(sqlProviderAnnotationType);
                //return new ProviderSqlSource(assistant.getConfiguration(), sqlProviderAnnotation, type, method);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private String buildSqlSourceFromStrings(String[] strings, Class<?> parameterTypeClass) {
        final StringBuilder sql = new StringBuilder();
        for (String fragment : strings) {
            sql.append(fragment);
            sql.append(" ");
        }
        return sql.toString();
    }

    private Class<? extends Annotation> getSqlAnnotationType(Method method) {
        return chooseAnnotationType(method, sqlAnnotationTypes);
    }

    private Class<? extends Annotation> getSqlProviderAnnotationType(Method method) {
        return chooseAnnotationType(method, sqlProviderAnnotationTypes);
    }

    private Class<? extends Annotation> chooseAnnotationType(Method method, Set<Class<? extends Annotation>> types) {
        for (Class<? extends Annotation> type : types) {
            Annotation annotation = method.getAnnotation(type);
            if (annotation != null) {
                return type;
            }
        }
        return null;
    }


    private Class<?> getParameterType(Method method) {
        Class<?> parameterType = null;
        Class<?>[] parameterTypes = method.getParameterTypes();

        for (Class<?> currentParameterType : parameterTypes) {
            if (parameterType == null) {
                parameterType = currentParameterType;
            } else {
                // 暂时没解析
                parameterType = HashMap.class;
            }

        }

        return parameterType;

    }

}
