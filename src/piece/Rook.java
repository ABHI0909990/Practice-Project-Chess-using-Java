package piece;

import Chess.Board;
import Chess.Type;

public class Rook extends pices{

    public Rook(int color, int col, int row) {
        super(color, col, row);

        type = Type.ROOK;

        if(color == Board.WHITE){
            image = getImage("/piece/w-rook.png");
        }
        else{
            image = getImage("/piece/b-rook.png");
        }
    }
    public boolean ismove(int Targetcol, int Targetrow) {

        if (isWithinBoard(Targetcol, Targetrow) && !isSameS(Targetcol, Targetrow)){
            if (Targetcol == precol || Targetrow == prerow) {
                if(isValidS(Targetcol,Targetrow) && !isStraightLineP(Targetcol, Targetrow)){
                    return true;
                }
            }
        }
        return false;
    }

}
