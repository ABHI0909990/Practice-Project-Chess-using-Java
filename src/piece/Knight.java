package piece;

import Chess.Board;
import Chess.Type;

public class Knight extends pices{
    public Knight(int color, int col, int row) {
        super(color, col, row);

        type = Type.KNIGHT;
        if(color == Board.WHITE){
            image = getImage("/piece/w-knight.png");
        }
        else{
            image = getImage("/piece/b-knight.png");
        }
    }

    @Override
    public boolean ismove(int Targetcol, int Targetrow) {

        if (isWithinBoard(Targetcol, Targetrow)) {
            if (Math.abs(Targetcol - precol) * Math.abs(Targetrow - prerow) == 2) {
                return isValidS(Targetcol, Targetrow);
            }
        }
        return false;
    }
}
