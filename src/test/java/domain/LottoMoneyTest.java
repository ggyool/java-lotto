package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static domain.LottoMoney.SINGLE_LOTTO_PRICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

class LottoMoneyTest {
    @DisplayName("유효한 값이면 객체 생성 성공")
    @ParameterizedTest
    @ValueSource(strings = {"1000", "10000", "1000000"})
    void create_validInput_success(final String value) {
        assertThatCode(() -> new LottoMoney(value))
                .doesNotThrowAnyException();
    }

    @DisplayName("객체 생성 실패 : null 입력")
    @Test
    void create_nullInput_exceptionThrown() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new LottoMoney(null))
                .withMessageContaining("null");
    }

    @DisplayName("객체 생성 실패 : 정수가 아닌 값 입력")
    @ParameterizedTest
    @ValueSource(strings = {"abc", "&^*", ""})
    void create_notNumber_exceptionThrown(final String value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new LottoMoney(value))
                .withMessageContaining("정수");
    }

    @DisplayName("객체 생성 실패 : 양의 정수가 아닌 값 입력")
    @ParameterizedTest
    @ValueSource(strings = {"-1000", "-1", "0"})
    void create_notPositiveNumber_exceptionThrown(final String value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new LottoMoney(value))
                .withMessageContaining("양의 정수");
    }

    @DisplayName("객체 생성 실패 : 범위에서 벗어난 금액인 경우")
    @ParameterizedTest
    @ValueSource(strings = {"999", "1000001"})
    void create_outOfRange_exceptionThrown(final String value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new LottoMoney(value))
                .withMessageContaining("범위를 벗어났습니다");
    }

    @DisplayName("객체 생성 실패 : 로또 한장 단위로 떨어지지 않는 경우")
    @ParameterizedTest
    @ValueSource(strings = {"1001", "99999"})
    void create_divisible_exceptionThrown(final String value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new LottoMoney(value))
                .withMessageContaining(SINGLE_LOTTO_PRICE + "원 단위의 금액이 아닙니다");
    }

    @DisplayName("동일한 객체 비교")
    @Test
    void equals() {
        final LottoMoney lottoMoney = new LottoMoney("1000");
        assertThat(lottoMoney)
                .isEqualTo(new LottoMoney("1000"));
    }

    @DisplayName("로또 머니를 로또 티켓 수로 변환")
    @Test
    void toTicketQuantity() {
        final LottoMoney lottoMoney = new LottoMoney("14000");
        assertThat(lottoMoney.toTicketQuantity())
                .isEqualTo(14);
    }

    @DisplayName("티켓으로 전환하기에 충분한 돈이 있는지 확인")
    @Test
    void isExchangeable() {
        final LottoMoney lottoMoney = new LottoMoney("14000");
        assertAll(
                () -> assertThat(lottoMoney.isExchangeable(14))
                        .isEqualTo(true),
                () -> assertThat(lottoMoney.isExchangeable(15))
                        .isEqualTo(false)
        );
    }
}
