package piece;

import Chess.ChessBoard;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

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
        return (x);
    }
    public int getRow(int y) {
        return (y);
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