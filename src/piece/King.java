package piece;

import Chess.Board;

public class King extends pices{
    public King(int color, int col, int row) {
        super(color, col, row);

        if(color == Board.WHITE){
            image = getImage("/piece/w-king.png");
        }
        else{
            image = getImage("/piece/b-king.png");
        }
    }
}
