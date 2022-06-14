package com.mb.smart.trader.usecases.trade;

import com.mb.smart.trader.domains.PriceContext;
import com.mb.smart.trader.domains.VoteType;

public interface TraderVote {

    VoteType executor(PriceContext priceContext);
}
