package com.langlang.binding;

import com.langlang.pojo.Configuration;
import com.langlang.pojo.MappedStatement;
import com.langlang.pojo.SqlCommandType;
import com.langlang.sqlSession.SqlSession;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author langlang.ye
 * @date 2021/5/1
 */
public class MapperMethod {

    // SqlCommand 对象
    private final SqlCommand command;

    // MethodSignature 对象
    private final MethodSignature method;

    public SqlCommand getCommand() {
        return command;
    }

    public MethodSignature getMethod() {
        return method;
    }

    public MapperMethod(Configuration configuration, Class<?> mapperInterface, Method method) {
        this.command = new SqlCommand(configuration, mapperInterface, method);
        this.method = new MethodSignature(method);
    }

    public Object execute(SqlSession sqlSession, Object... params) throws Exception {
        Object result = null;
        switch (command.getType()) {
            case INSERT : {
                result = sqlSession.insert(command.getName(), params);
                break;
            }
            case SELECT : {
                if (method.isReturnsMany()) {
                    result = sqlSession.selectList(command.getName(), params);
                } else {
                    result = sqlSession.selectOne(command.getName(), params);
                }
                break;
            }
            case UPDATE: {
                result = sqlSession.update(command.getName(), params);
                break;
            }
            case DELETE: {
                result = sqlSession.delete(command.getName(), params);
                break;
            }
        }

        return result;
    }

    public static class SqlCommand {

        private String name;
        private SqlCommandType type; // sql 命令类型

        public String getName() {
            return name;
        }

        public SqlCommandType getType() {
            return type;
        }

        public SqlCommand(Configuration configuration, Class<?> mapperInterface, Method method) {
            String id = method.getName();
            String namespace = mapperInterface.getName();
            String statementId = namespace + "." + id;
            MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);

            this.name = statementId;
            this.type = mappedStatement.getSqlCommandType();
        }
    }

    /**
     * 方法签名: 判断是否带有泛型
     * 默认: false
     */
    public static class MethodSignature {

        // 返回是否为集合
        private boolean returnsMany;
        
        public MethodSignature (Method method) {
            // 判断是否存在泛型
            Type genericReturnType = method.getGenericReturnType();
            if (genericReturnType instanceof ParameterizedType) {
                returnsMany = true;
            }
        }

        // 返回是否带有泛型
        public boolean isReturnsMany() {
            return returnsMany;
        }

    }


}
