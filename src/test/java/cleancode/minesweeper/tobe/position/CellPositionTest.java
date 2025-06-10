package cleancode.minesweeper.tobe.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CellPositionTest {

    @DisplayName("지정한 행 인덱스를 반환한다.")
    @Test
    void getRowIndex() {
        //given
        CellPosition position = CellPosition.of(2, 3);

        //when
        int rowIndex = position.getRowIndex();

        //then
        assertThat(rowIndex).isEqualTo(2);
    }

    @DisplayName("지정한 열 인덱스를 반환한다")
    @Test
    void getColumnIndex() {
        //given
        CellPosition position = CellPosition.of(2, 3);

        //when
         int colIndex = position.getColIndex();

        //then
        assertThat(colIndex).isEqualTo(3);
    }

    @DisplayName("좌표를 이동했을 때 음수가 되면 예외가 발생한다")
    @Test
    void calculatePosition() {
        //given
        CellPosition position = CellPosition.of(0, 0);

        //when
        RelativePosition relative = RelativePosition.of(-1, -1);

        //then
        assertThatThrownBy(() -> position.calculatePositionBy(relative))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("움직일 수 있는 좌표가 아닙니다");
    }
}