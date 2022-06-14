package com.mb.smart.trader.usecases.indicators;

import com.mb.smart.trader.domains.Price;
import com.mb.smart.trader.domains.PriceContext;
import com.mb.smart.trader.gateways.ParameterGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculateAllIndicators {

    private final List<CalculateIndicator> calculateIndicators;

    private final ParameterGateway parameterGateway;

    public PriceContext execute(final List<Price> prices, final Price currentPrice) {
        final PriceContext context = PriceContext.builder()
                .prices(prices)
                .currentPrice(currentPrice)
                .indicators(new HashMap<>())
                .parameters(parameterGateway.groupAllParametersByType())
                .build();
        calculateIndicators.forEach(calculateIndicator -> calculateIndicator.putIndicator(context));
        return context;
    }
}
