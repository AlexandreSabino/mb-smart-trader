package com.mb.smart.trader.usecases.trade;

import com.mb.smart.trader.domains.Price;
import com.mb.smart.trader.domains.PriceContext;
import com.mb.smart.trader.domains.VoteType;
import com.mb.smart.trader.usecases.indicators.CalculateAllIndicators;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TraderManager {

    private final CalculateAllIndicators calculateAllIndicators;

    private final List<TraderVote> traderVotes;

    public OperationSuggest whichOperation(final List<Price> prices, final Price currentPrice) {
        final PriceContext priceContext = calculateAllIndicators.execute(prices, currentPrice);
        final List<VoteType> votes = traderVotes.stream()
                .map(voter -> voter.executor(priceContext))
                .collect(Collectors.toList());

        if (votes.contains(VoteType.SALE_SURE)) {
            return OperationSuggest.SALE;
        } else if (votes.contains(VoteType.BUY_SURE)) {
            return OperationSuggest.BUY;
        }

        final long votesBuy = count(votes, VoteType.BUY);
        final long votesSale = count(votes, VoteType.SALE);
        final long votesNeutral = count(votes, VoteType.NEUTRAL);

        final boolean buyIsBetter = isBetter(votesBuy, votesSale, votesNeutral);
        if (buyIsBetter) {
            return OperationSuggest.BUY;
        }

        final boolean saleIsBetter = isBetter(votesSale, votesBuy, votesNeutral);
        if (saleIsBetter) {
            return OperationSuggest.SALE;
        }

        return OperationSuggest.NEUTRAL;
    }

    private boolean isBetter(long analyzeVote, long voteToCompareOne, long voteToCompareTwo) {
        return (analyzeVote > voteToCompareOne) && (analyzeVote > voteToCompareTwo);
    }

    private long count(List<VoteType> votes, VoteType vote) {
        return votes.stream().filter(v -> v == vote).count();
    }
}
