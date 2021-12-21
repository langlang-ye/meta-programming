package com.langlang.config;

import com.langlang.mapping.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author langlang.ye
 * @date 2021/4/28
 */
public class BoundSql {

    private String sqlText; // 解析过后的SQL

    private List<ParameterMapping> parameterMappingList = new ArrayList<>();

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }

    public BoundSql(String sqlText, List<ParameterMapping> parameterMappingList) {
        this.sqlText = sqlText;
        this.parameterMappingList = parameterMappingList;
    }
}
