package com.chess;

import java.util.LinkedList;
import java.util.List;

public class King extends Piece {

    public King(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }

    @Override
    public List<Square> getLegalMoves(Board gameBoard) {
LinkedList<Square> legalMoves = new LinkedList<Square>();
        
        Square[][] board = gameBoard.getSquareArray();
        
        int x = this.getCurrentSquare().getColumnPosition();
        int y = this.getCurrentSquare().getRowPosition();
        
        for (int i = 1; i > -2; i--) {
            for (int k = 1; k > -2; k--) {
                if(!(i == 0 && k == 0)) {
                    try {
                        if(!board[y + k][x + i].isOccupied() || 
                                board[y + k][x + i].getOccupyingPiece().getColor() 
                                != this.getColor()) {
                            legalMoves.add(board[y + k][x + i]);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                }
            }
        }
        
        return legalMoves;
    }

}
