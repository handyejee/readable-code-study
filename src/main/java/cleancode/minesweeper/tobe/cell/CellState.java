package cleancode.minesweeper.tobe.cell;

public class CellState {

    protected boolean isFlagged;
    protected boolean isOpened;

    private CellState(boolean isFlagged, boolean isOpened) {
        this.isFlagged = isFlagged;
        this.isOpened = isOpened;
    }

    // cell이 처음 생성되면 열려있지 않고 깃발이 꽂혀있지 않아서
    public static CellState initialize() {
        return new CellState(false, false);
    }

    public void flag() {
        this.isFlagged = true;
    }

    public void open() {
        this.isOpened = true;
    }

    public boolean isChecked() {
        return isFlagged || isOpened;
    }

    public boolean isOpened() {
        return isOpened;
    }
 }
