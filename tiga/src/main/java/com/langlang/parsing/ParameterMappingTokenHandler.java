package com.langlang.parsing;

import com.langlang.mapping.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author langlang.ye
 * @date 2021/4/29
 */
public class ParameterMappingTokenHandler implements TokenHandler {

    private List<ParameterMapping> parameterMappings = new ArrayList<>();

    private ParameterMapping buildParameterMapping(String content) {
        ParameterMapping parameterMapping = new ParameterMapping(content);
        return parameterMapping;
    }

    @Override
    public String handleToken(String content) {
        this.parameterMappings.add(buildParameterMapping(content));
        return "?";
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }
}
