package com.chess;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

@SuppressWarnings("serial")
public class Square extends JComponent {
    private static final int LIGHT_BOARD_COLOR_IDENTIFIER = 1;

    private Board board;
    private final int color;
    private Piece occupyingPiece;
    private boolean isDisplayingPiece;
    private int columnPosition;
    private int rowPosition;
    
    public Square(Board board, int color, int columnPosition, int rowPosition) {
        this.board = board;
        this.color = color;
        this.isDisplayingPiece = true;
        this.columnPosition = columnPosition;
        this.rowPosition = rowPosition;
        this.setBorder(BorderFactory.createEmptyBorder());
    }
    
    public int getColor() {
        return this.color;
    }
    
    public Piece getOccupyingPiece() {
        return this.occupyingPiece;
    }
    
    public boolean isOccupied() {
        return this.occupyingPiece != null;
    }
    
    public int getColumnPosition() {
        return this.columnPosition;
    }
    
    public int getRowPosition() {
        return this.rowPosition;
    }
    
    public void setIsDisplayingPiece(boolean value) {
        this.isDisplayingPiece = value;
    }
    
    public void put(Piece piece) {
        this.occupyingPiece = piece;
        piece.setPosition(this);
    }
    
    public Piece removePiece() {
        Piece removed = this.occupyingPiece;
        this.occupyingPiece = null;
        return removed;
    }
    
    public void capture(Piece newOccupier) {
        Piece currentOccupier = this.getOccupyingPiece();
        if (currentOccupier.getColor() == 0) board.Bpieces.remove(currentOccupier);
        if (currentOccupier.getColor() == 1) board.Wpieces.remove(currentOccupier);
        this.occupyingPiece = newOccupier;
    }
    
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.paintSquare(graphics);
        graphics.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());

        if(this.isOccupied() && this.isDisplayingPiece) {
            occupyingPiece.draw(graphics);
        }
    }

    private void paintSquare(Graphics graphics) {
        if (this.color == LIGHT_BOARD_COLOR_IDENTIFIER) {
            graphics.setColor(new Color(221,192,127));
        } else {
            graphics.setColor(new Color(101,67,33));
        }
    }
    
    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + columnPosition;
        result = prime * result + rowPosition;
        return result;
    }
}
