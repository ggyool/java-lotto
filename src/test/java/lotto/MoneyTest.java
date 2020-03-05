package lotto;

import domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MoneyTest {

    @Test
    @DisplayName("구매금액이 음수가 입력되었는지 확인")
    void checkNegativeAmountTest() {
        assertThatThrownBy(() -> {
            new Money(-1);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("구매 금액은 음수일 수 없습니다.");
    }

    @Test
    @DisplayName("구매금액이 로또 한장의 가격보다 낮은지 확인")
    void checkUnderLottoPriceTest() {
        assertThatThrownBy(() -> {
            new Money(500);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("로또 한장 가격보다 낮은 금액을 입력하셨습니다.");
    }

    @Test
    @DisplayName("구매금액에 따른 로또 개수가 제대로 계산되는지 확인")
    void lottoCountByPurchaseAmountTest() {
        Money money = new Money(14800);
        assertThat(money.getLottoCount()).isEqualTo(14);
    }

    @Test
    @DisplayName("로또 계산 결과를 바탕으로 수익률 계산 결과 확인")
    void calculateProfitRatioByLottoResultTest() {
        int[] winningNumbers = {1, 2, 3, 4, 5, 6};
        int bonusNumber = 7;
        WinningNumber winningNumber = new WinningNumber(winningNumbers, bonusNumber);
        int[] inputLottoNumbers = {1, 2, 3, 4, 5, 7};
        LottoGenerator lottoGenerator = new ManualLottoGenerator(inputLottoNumbers);
        Lotto lotto = lottoGenerator.generateLotto();
        Lottos lottos = new Lottos();
        lottos.addLotto(lotto);

        Money money = new Money(1500);
        LottoResult lottoResult = new LottoResult(lottos, winningNumber);
        int profitRatio = money.calculateProfitRatio(lottoResult);
        assertThat(profitRatio).isEqualTo(3_000_000);
    }

}