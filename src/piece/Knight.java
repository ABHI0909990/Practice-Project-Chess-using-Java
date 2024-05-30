package piece;

import Chess.Board;

public class Knight extends pices{
    public Knight(int color, int col, int row) {
        super(color, col, row);

        if(color == Board.WHITE){
            image = getImage("/piece/w-knight.png");
        }
        else{
            image = getImage("/piece/b-knight.png");
        }
    }
}
