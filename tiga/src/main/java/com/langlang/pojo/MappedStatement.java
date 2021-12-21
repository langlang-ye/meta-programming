package com.langlang.pojo;

/**
 * @author langlang.ye
 * @date 2021/4/28
 */
public class MappedStatement {

    // id 标识
    private String id;

    // 返回值类型
    private String resultType;

    // 参数类型
    private String parameterType;

    // SQL语句
    private String sql;

    // sql 操作类型
    private SqlCommandType sqlCommandType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }
}
