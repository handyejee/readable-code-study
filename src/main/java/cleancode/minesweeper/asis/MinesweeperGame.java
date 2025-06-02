package cleancode.minesweeper.asis;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

public class MinesweeperGame {

    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;
    public static final Scanner SCANNER = new Scanner(System.in);
    private static final Cell[][] BOARD = new Cell[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    private static final Integer[][] NEARBY_LAND_MINE_COUNTS = new Integer[8][10];
    private static final boolean[][] LAND_MINES = new boolean[8][10];
    public static final int LAND_MINE_COUNT = 10;

    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public static void main(String[] args) {
        showGameStartComments();
        initializeGame();

        while (true) {
            try{
                showBoard();

                if (doesUserWinTheGame()) {
                    System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                    break;
                }
                if (doesUserLoseTheGame()) {
                    System.out.println("지뢰를 밟았습니다. GAME OVER!");
                    break;
                }

                String cellInput = getCellInputFromUser();
                String userActionInput = getUserActionInputFromUser();
                actOnCell(cellInput, userActionInput);
            } catch (AppException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("프로그램에 문제가 생겼습니다");
            }
        }
    }

    private static void actOnCell(String cellInput, String userActionInput) {
        int selectedColIndex = getSelectedColIndex(cellInput);
        int selectedRowIndex = getSelectedRowIndex(cellInput);

        if (doesUserChooseToPlantFlag(userActionInput)) {
            BOARD[selectedRowIndex][selectedColIndex] = Cell.ofFlag();
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (isLandMindCell(selectedRowIndex, selectedColIndex)) {
                BOARD[selectedRowIndex][selectedColIndex] = Cell.ofLandMine();
                changeGameStatusToLose();
                return;
            }
            open(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }
        throw new AppException("잘못된 번호를 선택하셨습니다.");
    }

    private static void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private static boolean isLandMindCell(int selectedRowIndex, int selectedColIndex) {
        return LAND_MINES[selectedRowIndex][selectedColIndex];
    }

    private static boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private static boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private static int getSelectedRowIndex(String input) {
        char cellInputRow = input.charAt(1);
        int selectedRowIndex = convertRowFrom(cellInputRow);
        return selectedRowIndex;
    }

    private static int getSelectedColIndex(String input) {
        char cellInputCol = input.charAt(0);
        int selectedColIndex = convertColFrom(cellInputCol); // 메서드 시그니처를 전치사로 자연스럽게 연결(from)
        return selectedColIndex;
    }

    private static String getUserActionInputFromUser() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        return SCANNER.nextLine();
    }

    private static String getCellInputFromUser() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
        return SCANNER.nextLine();
    }

    private static boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private static boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private static void checkIfGameIsOver() {
        boolean isAllOpened = isAllCellOpened();
        if (isAllOpened) {
            changeGameStatusToWin();
        }
    }

    private static void changeGameStatusToWin() {
        gameStatus = 1;
    }

    private static boolean isAllCellOpened() {
        return Arrays.stream(BOARD)
                .flatMap(Arrays::stream)
//                .noneMatch(cell -> cell.getSign().equals(CLOSED_CELL_SIGN)); // 무례한 예
                .noneMatch(Cell::isClosed);
    }

    private static int convertRowFrom(char cellInputRow) {
        int rowIndex = Character.getNumericValue(cellInputRow ) - 1;
        if (rowIndex >= BOARD_ROW_SIZE) {
            throw new AppException("잘못된 입력입니다.");
        }

        return rowIndex;
    }

    private static int convertColFrom(char cellInputCol) {
        int selectedColIndex;
        switch (cellInputCol) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'j':
                return 9;
            default:
                throw new AppException("잘못된 입력입니다.");
        }
    }

    private static void showBoard() {
        System.out.println("   a b c d e f g h i j");
        for (int row = 0; row < 8; row++) {
            System.out.printf("%d  ", row + 1);
            for (int column = 0; column < 10; column++) {
                System.out.print(BOARD[row][column] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void initializeGame() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                BOARD[row][col] = Cell.ofClosed();
            }
        }

        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(10);
            int row = new Random().nextInt(8);
            LAND_MINES[row][col] = true;
        }

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 10; column++) {
                if (isLandMindCell(row, column)) { // 지뢰 cell이 아니면 count를 해서 할당
                    NEARBY_LAND_MINE_COUNTS[row][column] = 0;
                    continue;
                }
                int count = countNearByLandMines(row, column);
                NEARBY_LAND_MINE_COUNTS[row][column] = count;
            }
        }
    }

    private static int countNearByLandMines(int row, int column) {
        int count = 0;
        if (row - 1 >= 0 && column - 1 >= 0 && isLandMindCell(row - 1,
                column - 1)) { // 현재 칸 기준으로 왼쪽 대각선에 있으면
            count++;
        }
        if (row - 1 >= 0 && isLandMindCell(row - 1, column)) {
            count++;
        }
        if (row - 1 >= 0 && column + 1 < 10 && isLandMindCell(row - 1, column + 1)) {
            count++;
        }
        if (column - 1 >= 0 && isLandMindCell(row, column - 1)) {
            count++;
        }
        if (column + 1 < 10 && isLandMindCell(row, column + 1)) {
            count++;
        }
        if (row + 1 < 8 && column - 1 >= 0 && isLandMindCell(row + 1, column - 1)) {
            count++;
        }
        if (row + 1 < 8 && isLandMindCell(row + 1, column)) {
            count++;
        }
        if (row + 1 < 8 && column + 1 < 10 && isLandMindCell(row + 1, column + 1)) {
            count++;
        }
        return count;
    }

    private static void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void open(int row, int col) {
        if (row < 0 || row >= 8 || col < 0 || col >= 10) {
            return;
        }
        if (BOARD[row][col].doesNotClosed()) {
            return;
        }
        if (isLandMindCell(row, col)) {
            return;
        }
        if (NEARBY_LAND_MINE_COUNTS[row][col] != 0) {
            BOARD[row][col] = Cell.ofNearbyMineCount(NEARBY_LAND_MINE_COUNTS[row][col]);
            return;
        } else {
            BOARD[row][col] = Cell.ofOpened();
        }
        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }

}
