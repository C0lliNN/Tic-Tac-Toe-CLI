package com.raphaelcollin.game;

import static com.raphaelcollin.utils.Constants.ANSI_BLUE;
import static com.raphaelcollin.utils.Constants.ANSI_GREEN;
import static com.raphaelcollin.utils.Constants.ANSI_RESET;
import static com.raphaelcollin.utils.Constants.O;
import static com.raphaelcollin.utils.Constants.X;

public class BoardPrinterImpl implements BoardPrinter {

    @Override
    public void print(char[][] cells) {
        var formattedBoard = getFormattedBoard(cells);

        for (char[] row : formattedBoard) {
            System.out.print(" ".repeat(35));
            for (char cell : row) {
                String colorPrefix = getColorPrefixForLetter(cell);
                System.out.print(colorPrefix + cell + " " + ANSI_RESET);
            }
            System.out.println();
        }
        System.out.println();
    }

    private char[][] getFormattedBoard(char[][] cells) {
        return new char[][]{
                {getFormattedCell(cells, 0,0), '|', getFormattedCell(cells, 0, 1), '|', getFormattedCell(cells, 0, 2)},
                {'-', '+', '-', '+', '-'},
                {getFormattedCell(cells, 1, 0), '|', getFormattedCell(cells, 1, 1), '|', getFormattedCell(cells, 1, 2)},
                {'-', '+', '-', '+', '-'},
                {getFormattedCell(cells, 2, 0), '|', getFormattedCell(cells, 2, 1), '|', getFormattedCell(cells, 2, 2)}
        };
    }

    private char getFormattedCell(char[][] cells, int i, int j) {
        return Character.isWhitespace(cells[i][j]) ?
                Character.forDigit(getCellDigitRepresentation(i, j), 10) :
                cells[i][j];
    }

    private String getColorPrefixForLetter(char cell) {
        if (cell == X) {
            return ANSI_GREEN;
        }
        if (cell == O) {
            return ANSI_BLUE;
        }
        return "";
    }

    private int getCellDigitRepresentation(int i, int j) {
        return i * 3 + (j + 1);
    }
}
