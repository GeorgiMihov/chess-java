package com.chess;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public abstract class Piece {
    private final int color;
    private Square currentSquare;
    private BufferedImage image;
    
    public Piece(int color, Square initializingSquare, String imageFileName) {
        this.color = color;
        this.currentSquare = initializingSquare;
        this.initializeImage(imageFileName);
    }

    private void initializeImage(String imageFileName) {
        try {
            if (this.image == null) {
                this.image = ImageIO.read(getClass().getResource(imageFileName));
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public boolean move(Square destination) {
        Piece occupant = destination.getOccupyingPiece();
        
        if (occupant != null) {
            if (occupant.getColor() == this.color) {
                return false;
            } else {
                destination.capture(this);
            }
        }
        
        currentSquare.removePiece();
        this.setCurrentSquare(destination);
        currentSquare.put(this);
        return true;
    }
    
    public Square getCurrentSquare() {
        return this.currentSquare;
    }
    
    public void setCurrentSquare(Square newSquare) {
        this.currentSquare = newSquare;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public Image getImage() {
        return this.image;
    }
    
    public void draw(Graphics graphics) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        
        graphics.drawImage(this.image, x, y, null);
    }
    
    public int[] getAvailableLinearOccupations(Square[][] board) {
        int column = this.currentSquare.getColumnPosition();
        int row = this.currentSquare.getRowPosition();

        int closestOccupationAbove = this.getAvailableOccupationAbove(board, column, row);
        int closestOccupationBelow = this.getAvailableOccupationBelow(board, column, row);
        int closestOccupationLeft = this.getAvailableOccupationLeft(board, column, row);
        int closestOccupationRight = this.getAvailableOccupationRight(board, column, row);
        int[] occupations = {closestOccupationAbove, closestOccupationBelow, closestOccupationLeft, closestOccupationRight};
        
        return occupations;
    }

    private int getAvailableOccupationAbove(Square[][] board, int column, int row) {
        int closestOccupationAbove = 0;

        for (int i = 0; i < row; i++) {
            if (board[i][column].isOccupied()) {
                if (board[i][column].getOccupyingPiece().getColor() != this.color) {
                    closestOccupationAbove = i;
                } else {
                    closestOccupationAbove = i + 1;
                }
            }
        }

        return closestOccupationAbove;
    }

    private int getAvailableOccupationRight(Square[][] board, int column, int row) {
        int closestOccupationRight = 7;

        for (int i = 7; i > column; i--) {
            if (board[row][i].isOccupied()) {
                if (board[row][i].getOccupyingPiece().getColor() != this.color) {
                    closestOccupationRight = i;
                } else {
                    closestOccupationRight = i - 1;
                }
            }
        }

        return closestOccupationRight;
    }

    private int getAvailableOccupationBelow(Square[][] board, int column, int row) {
        int closestOccupationBelow = 7;

        for (int i = 7; i > row; i--) {
            if (board[i][column].isOccupied()) {
                if (board[i][column].getOccupyingPiece().getColor() != this.color) {
                    closestOccupationBelow = i;
                } else {
                    closestOccupationBelow = i - 1;
                }
            }
        }

        return closestOccupationBelow;
    }

    private int getAvailableOccupationLeft(Square[][] board, int column, int row) {
        int closestOccupationLeft = 0;

        for (int i = 0; i < column; i++) {
            if (board[row][i].isOccupied()) {
                if (board[row][i].getOccupyingPiece().getColor() != this.color) {
                    closestOccupationLeft = i;
                } else {
                    closestOccupationLeft = i + 1;
                }
            }
        }

        return closestOccupationLeft;
    }
    
    public List<Square> getAvailableDiagonalOccupations(Square[][] board) {
        LinkedList<Square> diagonalOccupations = new LinkedList<>();

        this.getTopLeftOccupations(diagonalOccupations, board);
        this.getTopRightOccupations(diagonalOccupations, board);
        this.getBottomLeftOccupations(diagonalOccupations, board);
        this.getBottomRightOccupations(diagonalOccupations, board);

        return diagonalOccupations;
    }

    private void getTopLeftOccupations(LinkedList<Square> diagonalOccupations, Square[][] board) {
        int diagonalColumn = currentSquare.getColumnPosition() - 1;
        int diagonalRow = currentSquare.getRowPosition() - 1;

        while (diagonalColumn >= 0 && diagonalRow >= 0) {
            if (board[diagonalRow][diagonalColumn].isOccupied()) {
                if (board[diagonalRow][diagonalColumn].getOccupyingPiece().getColor() != this.color) {
                    diagonalOccupations.add(board[diagonalRow][diagonalColumn]);
                }
                break;
            } else {
                diagonalOccupations.add(board[diagonalRow][diagonalColumn]);
                diagonalRow--;
                diagonalColumn--;
            }
        }
    }

    private void getTopRightOccupations(LinkedList<Square> diagonalOccupations, Square[][] board) {
        int diagonalColumn = currentSquare.getColumnPosition() + 1;
        int diagonalRow = currentSquare.getRowPosition() - 1;

        while (diagonalColumn < 8 && diagonalRow >= 0) {
            if (board[diagonalRow][diagonalColumn].isOccupied()) {
                if (board[diagonalRow][diagonalColumn].getOccupyingPiece().getColor() != this.color) {
                    diagonalOccupations.add(board[diagonalRow][diagonalColumn]);
                }
                break;
            } else {
                diagonalOccupations.add(board[diagonalRow][diagonalColumn]);
                diagonalRow--;
                diagonalColumn++;
            }
        }
    }

    private void getBottomLeftOccupations(LinkedList<Square> diagonalOccupations, Square[][] board) {
        int diagonalColumn = currentSquare.getColumnPosition() - 1;
        int diagonalRow = currentSquare.getRowPosition() + 1;

        while (diagonalColumn >= 0 && diagonalRow < 8) {
            if (board[diagonalRow][diagonalColumn].isOccupied()) {
                if (board[diagonalRow][diagonalColumn].getOccupyingPiece().getColor() != this.color) {
                    diagonalOccupations.add(board[diagonalRow][diagonalColumn]);
                }
                break;
            } else {
                diagonalOccupations.add(board[diagonalRow][diagonalColumn]);
                diagonalRow++;
                diagonalColumn--;
            }
        }
    }

    private void getBottomRightOccupations(LinkedList<Square> diagonalOccupations, Square[][] board) {
        int diagonalColumn = currentSquare.getColumnPosition() + 1;
        int diagonalRow = currentSquare.getRowPosition() + 1;

        while (diagonalColumn < 8 && diagonalRow < 8) {
            if (board[diagonalRow][diagonalColumn].isOccupied()) {
                if (board[diagonalRow][diagonalColumn].getOccupyingPiece().getColor() != this.color) {
                    diagonalOccupations.add(board[diagonalRow][diagonalColumn]);
                }
                break;
            } else {
                diagonalOccupations.add(board[diagonalRow][diagonalColumn]);
                diagonalRow++;
                diagonalColumn++;
            }
        }
    }
    
    // No implementation, to be implemented by each subclass
    public abstract List<Square> getLegalMoves(Board gameBoard);
}