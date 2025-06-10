package cleancode.minesweeper.tobe.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellPositionTest {

    @DisplayName("정상적인 숫자를 입력했을때 rowIndex를 반환한다.")
    @Test
    void getRowIndex() {
        //given
        CellPosition position = CellPosition.of(2, 3);

        //when
        int rowIndex = position.getRowIndex();

        //then
        assertEquals(2, rowIndex);
    }

}