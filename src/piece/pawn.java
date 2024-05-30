package piece;

import Chess.Board;

public class pawn extends pices{

    public pawn(int color, int col, int row) {
        super(color, col, row);

        if(color == Board.WHITE){
            image = getImage("/piece/w-pawn.png");
        }
        else{
            image = getImage("/piece/b-pawn.png");
        }
    }
}
