package piece;

import Chess.Board;
import Chess.Type;


public class King extends pices{


    public King(int color, int col, int row) {
        super(color, col, row);

        type = Type.KING;
        if(color == Board.WHITE){
            image = getImage("/piece/w-king.png");
        }
        else{
            image = getImage("/piece/b-king.png");
        }
    }

    public boolean ismove(int Targetcol,int Targetrow){

        if(isWithinBoard(Targetcol,Targetrow)) {

            if (Math.abs(Targetcol - precol) + Math.abs(Targetrow - prerow) == 1 ||
                    Math.abs(Targetcol - precol) * Math.abs(Targetrow - prerow) == 1) {
                return isValidS(Targetcol, Targetrow);
            }

            //Casting
            if (!MovedP) {

                //Right
                if (Targetcol == precol + 2 && Targetrow == prerow && !isStraightLineP(Targetcol, Targetrow)) {
                    for (pices Pieces : Board.simPieces) {
                        if (Pieces.col == precol + 3 && Pieces.row == prerow && !Pieces.MovedP) {
                            Board.Castling = Pieces;
                            return true;
                        }
                    }
                }

                //Left
                if(Targetcol == precol - 2 && Targetrow == prerow && !isStraightLineP(Targetcol, Targetrow)) {
                    pices[] p = new pices[2];
                    for (pices Pieces : Board.simPieces) {

                        if (Pieces.col == precol - 3 && Pieces.row == Targetrow) {
                            p[0] = Pieces;
                        }
                        if (Pieces.col == precol - 4 && Pieces.row == Targetrow) {
                            p[1] = Pieces;
                        }
                        if (p[0] == null && p[1] != null && !p[1].MovedP) {
                            Board.Castling = p[1];
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}