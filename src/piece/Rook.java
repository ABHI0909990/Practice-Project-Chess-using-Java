package piece;

import Chess.Board;

public class Rook extends pices{

    public Rook(int color, int col, int row) {
        super(color, col, row);

        if(color == Board.WHITE){
            image = getImage("/piece/w-rook.png");
        }
        else{
            image = getImage("/piece/b-rook.png");
        }
    }

}
