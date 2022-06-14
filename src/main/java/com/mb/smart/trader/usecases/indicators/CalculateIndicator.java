package com.mb.smart.trader.usecases.indicators;

import com.mb.smart.trader.domains.PriceContext;

public interface CalculateIndicator {

    void putIndicator(final PriceContext priceContext);
}
