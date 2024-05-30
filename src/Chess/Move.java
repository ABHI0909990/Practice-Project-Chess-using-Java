package Chess;

import piece.pices;

public class Move {

    int oldcol;
    int oldrow;
    int newcol;
    int newrow;

    pices Pices;
    pices capture;

    public Move(Board board,pices Pices,int newcol,int newrow){

        this.oldcol = Pices.col;
        this.oldrow = Pices.row;
        this.newcol = newcol;
        this.oldrow = newrow;

        this.Pices = Pices;
        this.capture = board.getPieces(newcol,newrow);


    }

}
