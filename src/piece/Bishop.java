package piece;


import Chess.Board;

public class Bishop extends pices{


    public Bishop(int color, int col, int row) {
        super(color, col, row);

        if(color == Board.WHITE){
            image = getImage("w-bishop.png");
        }
        else{
            image = getImage("b-bishop.png");
        }
    }
    public boolean ismove(int Targetcol, int Targetrow) {

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
