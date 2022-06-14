package com.mb.smart.trader.domains.parameters;

import lombok.Data;

@Data
public class Parameter {

    private ParameterType parameterType;

    private Object value;
}
