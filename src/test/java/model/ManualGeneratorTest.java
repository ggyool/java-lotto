package model;

import lotto.model.Lotto;
import lotto.model.LottoNumber;
import lotto.model.lottogenerator.ManualGenerator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ManualGeneratorTest {
    @Test
    void 로또_수동생성_확인() {
        List<LottoNumber> numbers = Arrays.asList(LottoNumber.of(1), LottoNumber.of(2),
                LottoNumber.of(3), LottoNumber.of(4), LottoNumber.of(5), LottoNumber.of(6));
        Lotto lotto = new Lotto(numbers);

        ManualGenerator manualGenerator = new ManualGenerator("1, 2, 3, 4, 5, 6");
        assertThat(manualGenerator.generate()).isEqualTo(lotto);
    }
}