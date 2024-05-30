package Chess;

import java.awt.*;

public class ChessBoard {

    final int MAX_ROW = 8;
    final int MAX_COL = 8;
    public static final int SQUARE_SIZE = 85;
    public static final int HalfSquareSize = SQUARE_SIZE / 2;



    public void draw(Graphics2D g2) {

        for (int row = 0; row < MAX_ROW; row++) {

            for (int col = 0; col < MAX_COL; col++) {
                g2.setColor((col+row)%2==0? new Color(250, 175, 123): new Color (184, 104, 42));
                g2.fillRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }
}
