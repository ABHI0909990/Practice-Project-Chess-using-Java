package piece;

import Chess.Board;
import Chess.Type;

public class Queen extends pices{
    public Queen(int color, int col, int row) {
        super(color, col, row);

        type = Type.QUEEN;
        if(color == Board.WHITE){
            image = getImage("/piece/w-queen.png");
        }
        else{
            image = getImage("/piece/b-queen.png");
        }
    }

    public boolean ismove(int Targetcol, int Targetrow) {

        //Rook
        if (isWithinBoard(Targetcol, Targetrow) && !isSameS(Targetcol, Targetrow)){
            if (Targetcol == precol || Targetrow == prerow) {
                if(isValidS(Targetcol,Targetrow) && !isStraightLineP(Targetcol, Targetrow)){
                    return true;
                }
            }
        }
        //Bishop
        if (isWithinBoard(Targetcol, Targetrow) && !isSameS(Targetcol, Targetrow)){
            if (Math.abs(Targetcol - precol) == Math.abs(Targetrow - prerow)) {
                if(isValidS(Targetcol,Targetrow) && !isDiagonalLineP(Targetcol, Targetrow)){
                    return true;
                }

            }
        }
        return false;
    }
}
