package com.chess;

import java.util.List;

public class Bishop extends Piece {

    public Bishop(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }
    
    @Override
    public List<Square> getLegalMoves(Board b) {
        Square[][] board = b.getSquareArray();
        int x = this.getPosition().getColumnPosition();
        int y = this.getPosition().getRowPosition();
        
        return getDiagonalOccupations(board, x, y);
    }
}
