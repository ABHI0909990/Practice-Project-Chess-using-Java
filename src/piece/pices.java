package piece;

import Chess.ChessBoard;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class pices {

    public BufferedImage image;
    public int x, y;
    public int col, row, precol, prerow;
    public int color;

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
            // Check if resource stream is null before attempting to read the image
            if (getClass().getResourceAsStream(imagePath) != null) {
                image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            } else {
                System.err.println("Image resource not found: " + imagePath);
            }
        } catch(IOException e) {
            e.printStackTrace();
            // Handle the exception gracefully, e.g., show an error message or log the error.
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
        return (x + ChessBoard.HalfSquareSize/ChessBoard.SQUARE_SIZE);
    }
    public int getRow(int y) {
        return (y + ChessBoard.HalfSquareSize / ChessBoard.SQUARE_SIZE);
    }
    public void updatePosition(){

        x = getX(col);
        y = getY(row);
        precol = getCol(x);
        prerow = getRow(y);
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