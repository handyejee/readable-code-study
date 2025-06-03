package cleancode.minesweeper.tobe.cell;

import cleancode.minesweeper.tobe.Cell;

public class LandMineCell extends Cell {

    public static final String LAND_MINE_SIGN = "☼";

    @Override
    public boolean isLandMine() {
        return true;
    }

    @Override
    public boolean hasLandMineCount() {
        return false;
    }

    @Override
    public String getSign() {
        if (isOpened) {
            return LAND_MINE_SIGN;
        }
        if (isFlagged) {
            return FLAG_SIGN;
        }
        return UNCHECKED_SIGN;
    }
}
