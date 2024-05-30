package piece;

import Chess.Board;

public class Queen extends pices{
    public Queen(int color, int col, int row) {
        super(color, col, row);

        if(color == Board.WHITE){
            image = getImage("/piece/w-queen.png");
        }
        else{
            image = getImage("/piece/b-queen.png");
        }
    }
}
