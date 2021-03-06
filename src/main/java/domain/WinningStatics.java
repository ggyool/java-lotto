package domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WinningStatics {
    private final Map<Prize, Integer> numberPerPrize = new LinkedHashMap<>();

    {
        for (final Prize prize : Prize.values()) {
            numberPerPrize.put(prize, 0);
        }
    }

    public WinningStatics(final List<Prize> prizes) {
        prizes.forEach(prize -> numberPerPrize.
                computeIfPresent(prize, (Prize key, Integer value) -> value + 1));
    }

    public double calculateProfitRate(final LottoMoney lottoMoney) {
        final long totalProfit = calculateTotalProfit();
        return lottoMoney.toScale(totalProfit);
    }

    private long calculateTotalProfit() {
        long totalProfit = 0L;

        for (Entry<Prize, Integer> entry : numberPerPrize.entrySet()) {
            long money = entry.getKey().getMoney();
            int number = entry.getValue();
            totalProfit += money * number;
        }
        return totalProfit;
    }

    public Map<Prize, Integer> toMap() {
        return Collections.unmodifiableMap(numberPerPrize);
    }
}
