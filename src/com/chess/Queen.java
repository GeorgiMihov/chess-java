package com.chess;

import java.util.LinkedList;
import java.util.List;

public class Queen extends Piece {

    public Queen(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }

    @Override
    public List<Square> getLegalMoves(Board gameBoard) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();
        Square[][] board = gameBoard.getSquareArray();
        
        int x = this.getCurrentSquare().getColumnPosition();
        int y = this.getCurrentSquare().getRowPosition();
        
        int[] occups = getAvailableLinearOccupations(board);
        
        for (int i = occups[0]; i <= occups[1]; i++) {
            if (i != y) legalMoves.add(board[i][x]);
        }
        
        for (int i = occups[2]; i <= occups[3]; i++) {
            if (i != x) legalMoves.add(board[y][i]);
        }
        
        List<Square> bMoves = getAvailableDiagonalOccupations(board);
        
        legalMoves.addAll(bMoves);
        
        return legalMoves;
    }
    
}
