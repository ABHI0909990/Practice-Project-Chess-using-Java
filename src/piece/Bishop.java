package piece;

import Chess.Board;

public class Bishop extends pices{


    public Bishop(int color, int col, int row) {
        super(color, col, row);

        if(color == Board.WHITE){
            image = getImage("/piece/w-bishop.png");
        }
        else{
            image = getImage("/piece/b-bishop.png");
        }
    }
}
