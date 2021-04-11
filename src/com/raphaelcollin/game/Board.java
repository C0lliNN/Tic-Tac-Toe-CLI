package com.raphaelcollin.game;

import com.raphaelcollin.exceptions.CellNotEmptyException;
import com.raphaelcollin.exceptions.InvalidPositionException;

import java.util.Arrays;
import java.util.Optional;

import static java.lang.String.format;

public class Board {
    private final BoardPrinter boardPrinter;
    private final char[][] cells;

    public Board(BoardPrinter boardPrinter) {
        this.cells = new char[][]{
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };

        this.boardPrinter = boardPrinter;
    }

    public void print() {
        boardPrinter.print(cells);
    }

    public void insertAt(int number, char symbol) throws InvalidPositionException, CellNotEmptyException {
        if (number < 1 || number > 9) {
            throw new InvalidPositionException(format("Position %d must be between 1 and 9", number));
        }

        int i = getRowIndexFromDigit(number);
        int j = getColumnIndexFromDigit(number);

        if (!Character.isWhitespace(cells[i][j])) {
            throw new CellNotEmptyException(format("Cell in position %d is not empty", number));
        }

        cells[i][j] =  symbol;
    }

    private int getRowIndexFromDigit(int digit) {
        return (digit - 1) / 3;
    }

    private int getColumnIndexFromDigit(int digit) {
        int i = getRowIndexFromDigit(digit);
        return (digit - 1) - (i * 3);
    }

    public Optional<String> getWinner() {
        Optional<String> winner = getWinnerFromRows();
        if (winner.isPresent()) return winner;

        winner = getWinnerFromColumns();
        if (winner.isPresent()) return winner;

        winner = getWinnerFromDiagonal();
        if (winner.isPresent()) return winner;

        winner = getWinnerFromAntiDiagonal();
        if (winner.isPresent()) return winner;

        boolean draw = isDraw();
        if (draw) return Optional.of("DRAW");

        return Optional.empty();
    }

    private Optional<String> getWinnerFromRows() {
        for (int i = 0; i < cells.length; i++) {
            if (cells[i][0] == cells[i][1] && cells[i][1] == cells[i][2]) {
                if (Character.isWhitespace(cells[i][0])) {
                    return Optional.empty();
                } else {
                    return Optional.of(Character.toString(cells[i][0]));
                }
            }
        }
        return Optional.empty();
    }

    private Optional<String> getWinnerFromColumns() {
        for (int i = 0; i < cells.length; i++) {
            if (cells[0][i] == cells[1][i] && cells[1][i] == cells[2][i]) {
                if (Character.isWhitespace(cells[0][i])) {
                    return Optional.empty();
                } else {
                    return Optional.of(Character.toString(cells[0][i]));
                }
            }
        }
        return Optional.empty();
    }

    private Optional<String> getWinnerFromDiagonal() {
        if (cells[0][0] == cells[1][1] && cells[1][1] == cells[2][2]) {
            if (Character.isWhitespace(cells[0][0])) {
                return Optional.empty();
            } else {
                return Optional.of(Character.toString(cells[0][0]));
            }
        }
        return Optional.empty();
    }

    private Optional<String> getWinnerFromAntiDiagonal() {
        if (cells[0][2] == cells[1][1] && cells[1][1] == cells[2][0]) {
            if (Character.isWhitespace(cells[0][2])) {
                return Optional.empty();
            } else {
                return Optional.of(Character.toString(cells[0][2]));
            }
        }
        return Optional.empty();
    }

    private boolean isDraw() {
        return Arrays
                .stream(cells)
                .noneMatch(this::isRowEmpty);
    }

    private boolean isRowEmpty(char[] row) {
        return Character.isWhitespace(row[0])
                || Character.isWhitespace(row[1])
                || Character.isWhitespace(row[2]);
    }
}
