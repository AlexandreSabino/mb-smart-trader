package com.mb.smart.trader.gateways;

import com.mb.smart.trader.domains.parameters.Parameter;
import com.mb.smart.trader.domains.parameters.ParameterType;

import java.util.Map;

public interface ParameterGateway {

    Map<ParameterType, Parameter> groupAllParametersByType();
}
