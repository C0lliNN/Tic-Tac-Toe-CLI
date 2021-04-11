package com.raphaelcollin;

import com.raphaelcollin.game.Board;
import com.raphaelcollin.game.BoardPrinter;
import com.raphaelcollin.game.BoardPrinterImpl;
import com.raphaelcollin.game.Game;

public class Main {

    public static void main(String[] args) {
        BoardPrinter printer = new BoardPrinterImpl();
        Board board = new Board(printer);

        Game game = new Game(board);
        game.start();
    }
}
