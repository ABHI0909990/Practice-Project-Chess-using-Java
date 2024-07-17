package piece;

import Chess.Board;
import Chess.Type;

public class pawn extends pices{

    public pawn(int color, int col, int row) {
        super(color, col, row);

        type = Type.PAWN;
        if(color == Board.WHITE){
            image = getImage("/piece/w-pawn.png");
        }
        else{
            image = getImage("/piece/b-pawn.png");
        }
    }
    public boolean ismove(int Targetcol,int Targetrow){

        if(isWithinBoard(Targetcol,Targetrow) && isSameS(Targetcol, Targetrow)){

            int MoveV;
            if(color == Board.WHITE){
                MoveV = -1;
            }
            else{
                MoveV = 1;
            }

            hitP = iscaptureP(Targetcol,Targetrow);

            if(Targetcol == precol && Targetrow == prerow + MoveV &&  hitP == null){
                return true;
            }

            if(Math.abs(Targetcol-precol) == 1 && Targetrow == prerow + MoveV && hitP != null && hitP.color != color){
                return true;
            }

            if(Targetcol == precol && Targetrow == prerow + MoveV * 2 && hitP == null && !MovedP && isStraightLineP(Targetcol, Targetrow)){
                return true;
            }
            if(Math.abs(Targetcol - precol) == 1 && Targetrow == prerow + MoveV){

                for(pices Pieces : Board.simPieces){
                    if(Pieces.col == Targetcol && Pieces.row == prerow && !Pieces.twoStepped){
                        hitP = Pieces;
                        return true;
                    }
                }
            }

        }
        return false;
    }
}
