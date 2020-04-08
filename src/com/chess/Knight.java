package com.chess;

import java.util.LinkedList;
import java.util.List;

public class Knight extends Piece {

    public Knight(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }

    @Override
    public List<Square> getLegalMoves(Board gameBoard) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();
        Square[][] board = gameBoard.getSquareArray();
        
        int x = this.getCurrentSquare().getColumnPosition();
        int y = this.getCurrentSquare().getRowPosition();
        
        for (int i = 2; i > -3; i--) {
            for (int k = 2; k > -3; k--) {
                if(Math.abs(i) == 2 ^ Math.abs(k) == 2) {
                    if (k != 0 && i != 0) {
                        try {
                            legalMoves.add(board[y + k][x + i]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            continue;
                        }
                    }
                }
            }
        }
        
        return legalMoves;
    }

}
