package cleancode.minesweeper.tobe.io.sign;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CellSignFinderTest {

    @DisplayName("빈 셀에 대해서 빈 셀을 반환한다")
    @Test
    void findCellSignFrom_EmptyCell() {
        //given
        CellSnapshot emptySnapshot = CellSnapshot.ofEmpty();

        //when
        String sign = cellSignFinder.findCellSignFrom(emptySnapshot);

        //then
        assertThat(sign).isEqualTo("■");
    }

    @DisplayName("깃발 셀에 대해서 깃발 셀을 반환한다")
    @Test
    void findCellSignFrom_FlagCell() {
        //given
        CellSnapshot flagSnapshot = CellSnapshot.ofFlag();

        //when
        String sign = cellSignFinder.findCellSignFrom(flagSnapshot);

        //then
        assertThat(sign).isEqualTo("⚑");
    }

}