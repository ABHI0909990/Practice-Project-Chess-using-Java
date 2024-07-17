package piece;

import Chess.Board;
import Chess.ChessBoard;
import Chess.Type;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class pices {

    public Type type;
    public BufferedImage image;
    public int x, y;
    public int col, row, precol, prerow;
    public int color;
    public pices hitP;
    public boolean MovedP , twoStepped;

    public pices (int color, int col, int row){

        this.color = color;
        this.col = col;
        this.row = row;
        x = getX(col);
        precol = col;
        prerow = row;
        y = getY(row);
    }

    public BufferedImage getImage(String imagePath) {
        BufferedImage image = null;
        try {
            if (getClass().getResourceAsStream(imagePath) != null) {
                image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            } else {
                System.err.println("Image resource not found: " + imagePath);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    public int getX(int col){
        return col * ChessBoard.SQUARE_SIZE;
    }
    public int getY(int row){
        return row * ChessBoard.SQUARE_SIZE;
    }
    public int getCol(int x){
        return (x + ChessBoard.HalfSquareSize) / ChessBoard.SQUARE_SIZE;
    }
    public int getRow(int y) {
        return (y + ChessBoard.HalfSquareSize) / ChessBoard.SQUARE_SIZE;
    }
    public int getIndex(){
        for(int index = 0; index < Board.simPieces.size(); index++){
            if(Board.simPieces.get(index) == this) {
                return index;
            }
        }
        return 0;
    }
    public void updatePosition(){

        if(type == Type.PAWN){
            if(Math.abs(row - prerow) == 2){
                twoStepped = true;
            }
        }

        x = getX(col);
        y = getY(row);
        precol = getCol(x);
        prerow = getRow(y);
        MovedP = true;

    }
    public boolean ismove (int Targetcol,int Targetrow){
        return false;
    }
    public boolean isWithinBoard (int Targetcol,int Targetrow){

        return Targetcol >= 0 && Targetcol <= 7 && Targetrow >= 0 && Targetrow <= 7;
    }
    public boolean isSameS(int Targetcol,int Targetrow){
        return Targetcol != precol || Targetrow != prerow;
    }
    public pices iscaptureP(int Targetcol, int Targetrow){

        for(pices Pieces : Board.simPieces){
            if(Pieces.col == Targetcol && Pieces.row == Targetrow && Pieces != this){
                return Pieces;
            }
        }
        return null;
    }
    public boolean isValidS (int Targetcol, int Targetrow){
        hitP =  iscaptureP(Targetcol,Targetrow);

        if(hitP == null){
            return true;
        }
        else {
            if (hitP.color != this.color){
                return true;
            }
            else {
                hitP = null;
            }
        }
        return false;
    }
    public boolean isStraightLineP (int Targetcol, int Targetrow){

        //Left for
        for(int c = precol-1; c > Targetcol;c--){
            for(pices Pieces : Board.simPieces){
                if(Pieces.col == c && Pieces.row == Targetrow){
                    hitP = Pieces;
                    return false;
                }
            }
        }

        //Right for
        for(int c = precol+1; c < Targetcol;c++){
            for(pices Pieces : Board.simPieces){
                if(Pieces.col == c && Pieces.row == Targetrow){
                    hitP = Pieces;
                    return false;
                }
            }
        }
        //Moving Up
        for(int r = prerow-1; r > Targetrow;r--){
            for(pices Pieces : Board.simPieces){
                if(Pieces.col == Targetcol && Pieces.row == r){
                    hitP = Pieces;
                    return false;
                }
            }
        }
        //Moving down
        for(int r = prerow+1; r < Targetrow;r++){
            for(pices Pieces : Board.simPieces){
                if(Pieces.col == Targetcol && Pieces.row == r){
                    hitP = Pieces;
                    return false;
                }
            }
        }
        return true;
    }
    public boolean isDiagonalLineP(int Targetcol , int Targetrow){

        if(Targetrow < prerow){

            //for Left Up
            for(int c = precol - 1;c > Targetcol; c--){
                int diff = Math.abs(c-precol);
                for(pices Pieces : Board.simPieces){
                    if(Pieces.col == c && Pieces.row == prerow - diff){
                        hitP = Pieces;
                        return false;
                    }
                }
            }
            //For Right Up
            for(int c = precol + 1;c < Targetcol; c++){
                int diff = Math.abs(c-precol);
                for(pices Pieces : Board.simPieces){
                    if(Pieces.col == c && Pieces.row == prerow - diff){
                        hitP = Pieces;
                        return false;
                    }
                }
            }
        }
        if(Targetrow > prerow){

            //down left
            for(int c = precol - 1;c > Targetcol; c--){
                int diff = Math.abs(c-precol);
                for(pices Pieces : Board.simPieces){
                    if(Pieces.col == c && Pieces.row == prerow + diff){
                        hitP = Pieces;
                        return false;
                    }
                }
            }
            //down Right
            for(int c = precol + 1;c < Targetcol; c++){
                int diff = Math.abs(c-precol);
                for(pices Pieces : Board.simPieces){
                    if(Pieces.col == c && Pieces.row == prerow + diff){
                        hitP = Pieces;
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public void resetP(){
        col = precol;
        row = prerow;
        x = getX(col);
        y = getY(row);
    }

    public void draw(Graphics2D g3){

        g3.drawImage(image,
                x,
                y,
                ChessBoard.SQUARE_SIZE,
                85,
                null);
    }
}