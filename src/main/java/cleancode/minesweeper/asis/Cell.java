package cleancode.minesweeper.asis;

public class Cell {

    public static final String FLAG_SIGN = "⚑";
    public static final String LAND_MINE_SIGN = "☼";
    public static final String UNCHECKED_SIGN = "□";
    public static final String EMPTY_SIGN = "■";


    private final String sign;
    private int nearbyLandMineCount;
    private boolean isLandMine;

    // cell 이 가진 속성: 근처 지뢰 숫자, 지뢰 여부
    // cell 의 상태: 깃발 유무, 열렸다/닫혔다, 사용자가 확인함

    public Cell(String sign, int nearbyLandMineCount, boolean isLandMine) {
        this.sign = sign;
        this.nearbyLandMineCount = nearbyLandMineCount;
        this.isLandMine = isLandMine;
    }

    public static Cell of(String sign, int nearbyLandMineCount, boolean isLandMine) {
        return new Cell(sign, nearbyLandMineCount, isLandMine);
    }

    public static Cell ofFlag() {
        return of(FLAG_SIGN);
    }

    public static Cell ofLandMine() {
        return of(LAND_MINE_SIGN);
    }

    public static Cell ofClosed() {
        return of(UNCHECKED_SIGN);
    }

    public static Cell ofOpened() {
        return of(EMPTY_SIGN);
    }

    public static Cell ofNearbyMineCount(int count) {
        return of(String.valueOf(count));
    }

    public String getSign() {
        return sign;
    }

    public boolean isClosed() {
        return UNCHECKED_SIGN.equals(this.sign);
    }

    public boolean doesNotClosed() {
        return !isClosed();
    }
}
