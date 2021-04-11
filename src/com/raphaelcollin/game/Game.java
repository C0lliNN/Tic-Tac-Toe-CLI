package com.raphaelcollin.game;

import com.raphaelcollin.exceptions.CellNotEmptyException;
import com.raphaelcollin.exceptions.InvalidPositionException;

import java.util.Optional;
import java.util.Scanner;

import static com.raphaelcollin.utils.Constants.ANSI_GREEN;
import static com.raphaelcollin.utils.Constants.ANSI_PURPLE;
import static com.raphaelcollin.utils.Constants.ANSI_RED;
import static com.raphaelcollin.utils.Constants.ANSI_RESET;
import static com.raphaelcollin.utils.Constants.ANSI_YELLOW;
import static com.raphaelcollin.utils.Constants.O;
import static com.raphaelcollin.utils.Constants.X;

public class Game {
    private final Scanner scanner = new Scanner(System.in);
    private final Board board;
    private char currentPlayer;

    public Game(Board board) {
        this.board = board;
        this.currentPlayer = X;
    }

    public void start() {
        Optional<String> winner = getWinner();

        while(winner.isEmpty()) {
            printHeader();
            printBoard();
            printCurrentPlayer();

            Optional<Integer> position = getPositionFromUser();
            position.ifPresent(this::insertAt);

            winner = getWinner();
            cleanScreen();
        }

        printHeader();
        printBoard();
        printWinner(winner.get());
    }

    private void printBoard() {
        board.print();
    }

    private void printCurrentPlayer() {
        System.out.println("Current Player: " + ANSI_YELLOW + currentPlayer);
        System.out.print(ANSI_RESET);
    }

    private void printHeader() {
        System.out.println(ANSI_PURPLE);
        System.out.println("=".repeat(80));
        System.out.println(" ".repeat(34) + "TIC TAC TOE");
        System.out.println("=".repeat(80));
        System.out.println(ANSI_RESET);
    }

    private Optional<Integer> getPositionFromUser() {
        try {
            System.out.print("Enter the position: ");
            int position = scanner.nextInt();

            return Optional.of(position);
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Invalid position format. Try again" + ANSI_RESET);
            pause();

            return Optional.empty();
        }
    }

    private void insertAt(int position) {
        try {
            board.insertAt(position, currentPlayer);
            updateCurrentPlayer();
        } catch (InvalidPositionException | CellNotEmptyException e) {
            System.out.println(ANSI_RED + "Error: " + e.getMessage() + ANSI_RESET);
            pause();
        }
    }

    private void updateCurrentPlayer() {
        currentPlayer = currentPlayer == X ? O : X;
    }

    private Optional<String> getWinner() {
        return board.getWinner();
    }

    private void printWinner(String winner) {
        System.out.println("The game is over !!!");
        System.out.println();
        System.out.println("Winner: " + ANSI_GREEN + winner);
        System.out.println(ANSI_RESET);
    }

    private void cleanScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void pause() {
        System.out.println(ANSI_GREEN + "Press enter to continue..." + ANSI_RESET);
        new Scanner(System.in).nextLine();
    }
}
