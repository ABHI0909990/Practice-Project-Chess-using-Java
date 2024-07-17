package Chess;

import piece.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel implements Runnable {

    public static int Size1 = 1000;
    public static int Size2 = 680;
    final int FPS = 60;
    Thread gameThread;
    ChessBoard f2 = new ChessBoard();
    Mouse mouse = new Mouse();

    //pieces
    public static ArrayList<pices> pieces = new ArrayList<>();
    public static ArrayList<pices> simPieces = new ArrayList<>();
    ArrayList<pices> promotionP = new ArrayList<>();
    pices active , CheckP;
    public static pices Castling;

    // color
    public static final int WHITE= 0;
    public static final int BLACK= 1;
    int currentColor = WHITE;

    //Boolean
    boolean ismove;
    boolean validP;
    public boolean promotion;
    boolean EndGame;
    boolean Stalemate;

    public Board() {
        setPreferredSize(new Dimension(Size1, Size2));
        setBackground(Color.gray);
        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        //testPromotion();
        setPieces();
        //testIllegal();
        copyPieces(pieces,simPieces);

    }

    public void launch() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    public void setPieces(){

        //White
        pieces.add(new pawn(WHITE,0,6));
        pieces.add(new pawn(WHITE,1,6));
        pieces.add(new pawn(WHITE,2,6));
        pieces.add(new pawn(WHITE,3,6));
        pieces.add(new pawn(WHITE,4,6));
        pieces.add(new pawn(WHITE,5,6));
        pieces.add(new pawn(WHITE,6,6));
        pieces.add(new pawn(WHITE,7,6));
        pieces.add(new Rook(WHITE,0,7));
        pieces.add(new Rook(WHITE,7,7));
        pieces.add(new Knight(WHITE,1,7));
        pieces.add(new Knight(WHITE,6,7));
        pieces.add(new Bishop(WHITE,2,7));
        pieces.add(new Bishop(WHITE,5,7));
        pieces.add(new Queen(WHITE,3,7));
        pieces.add(new King(WHITE,4,7));


        //Black
        pieces.add(new pawn(BLACK,0,1));
        pieces.add(new pawn(BLACK,1,1));
        pieces.add(new pawn(BLACK,2,1));
        pieces.add(new pawn(BLACK,3,1));
        pieces.add(new pawn(BLACK,4,1));
        pieces.add(new pawn(BLACK,5,1));
        pieces.add(new pawn(BLACK,6,1));
        pieces.add(new pawn(BLACK,7,1));
        pieces.add(new Rook(BLACK,0,0));
        pieces.add(new Rook(BLACK,7,0));
        pieces.add(new Knight(BLACK,1,0));
        pieces.add(new Knight(BLACK,6,0));
        pieces.add(new Bishop(BLACK,2,0));
        pieces.add(new Bishop(BLACK,5,0));
        pieces.add(new Queen(BLACK,4,0));
        pieces.add(new King(BLACK,3,0));

    }
    /*
    public void testPromotion(){
        pieces.add(new pawn(WHITE,0,1));
        pieces.add(new pawn(BLACK,5,6));
    }*/

    /*public void testIllegal(){
        pieces.add(new King(WHITE,3,7));
        pieces.add(new pawn(WHITE,7,6));
        pieces.add(new King(BLACK,0,3));
        pieces.add(new Bishop(BLACK,1,4));
        pieces.add(new Queen(BLACK,4,5));
    }*/
    private void copyPieces (ArrayList<pices> source,ArrayList<pices> target){

        target.clear();
        target.addAll(source);
    }

    @Override
    public void run() {
        double drawTime= (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawTime ;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {

        if(promotion){
            promotingP();
        }
        else if(!EndGame && !Stalemate) {
            if(mouse.Pressed){

                if(active==null){

                    for(pices Pieces : simPieces){

                        if(Pieces.color == currentColor &&
                                Pieces.col == mouse.x/ChessBoard.SQUARE_SIZE &&
                                Pieces.row == mouse.y/ChessBoard.SQUARE_SIZE){

                            active = Pieces;
                        }
                    }
                }
                else {
                    simulate();
                }
            }
            if(!mouse.Pressed) {
                if (active != null) {
                    if(validP){
                        copyPieces(simPieces,pieces);
                        active.updatePosition();

                        if(Castling != null){
                            Castling.updatePosition();
                        }

                        if(kingInCheckM() && isCheckMate()){
                            EndGame = true;
                        }
                        else if(isStalemate() && !kingInCheckM()){
                            Stalemate = true;
                        }
                        else {
                            if (isPromotion()) {
                                promotion = true;
                            } else {
                                ChangePlayer();
                            }
                        }
                    }
                    else {
                        copyPieces(pieces,simPieces);
                        active.resetP();
                        active = null;
                    }
                }
            }
        }
    }
    public void simulate() {

        ismove = false;
        validP = false;

        copyPieces(pieces,simPieces);

        if(Castling != null){
            Castling.col = Castling.precol;
            Castling.x = Castling.getX(Castling.col);
            Castling = null;
        }

        active.x = mouse.x - ChessBoard.HalfSquareSize;
        active.y = mouse.y - ChessBoard.HalfSquareSize;
        active.col = active.getCol(active.x);
        active.row = active.getRow(active.y);

        //reachable square
        if(active.ismove(active.col,active.row)){

            ismove = true;

            //Remove piece from list if pieces hit
            if(active.hitP != null){
                 simPieces.remove(active.hitP.getIndex());
            }

            CheckCastling();

            if(!isIilegal(active) && !opponetCanCaptureK()){
                validP = true;
            }
        }

    }
    private boolean isIilegal(pices King){
        if(King.type == Type.KING){
            for(pices Pieces : simPieces){
                if(Pieces != King && Pieces.color != King.color && Pieces.ismove(King.col,King.row)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean opponetCanCaptureK(){
        pices king = getKing(false);
        for(pices Pieces : simPieces){
            if(Pieces.color != king.color && Pieces.ismove(king.col,king.row)){
                return true;
            }
        }
        return false;
    }
    private boolean kingInCheckM(){
        pices King = getKing(true);

        if(active.ismove(King.col,King.row)){
            CheckP = active;
            return true;
        }
        else {
            CheckP = null;
        }
        return false;
    }
    private pices getKing(boolean Opponent){

        pices King = null;

        for(pices Pieces : simPieces){
            if(Opponent){
                if(Pieces.type == Type.KING && Pieces.color != currentColor){
                    King = Pieces;
                }
            }
            else {
                if(Pieces.type == Type.KING && Pieces.color == currentColor){
                    King = Pieces;
                }
            }
        }
        return King;
    }

    private boolean kingCanMove(pices king){

        if(isValidM(king,-1,-1)){ return true;}
        if(isValidM(king,0,-1)){ return true;}
        if(isValidM(king,1,-1)){ return true;}
        if(isValidM(king,-1,0)){ return true;}
        if(isValidM(king,1,0)){ return true;}
        if(isValidM(king,-1,1)) {return true;}
        if(isValidM(king,1,1)) {return true;}
        if(isValidM(king,0,-1)){ return true;}

        return false;
    }

    private boolean isCheckMate() {

        pices king = getKing(true);

        if (kingCanMove(king)) {
            return false;
        }
        else {
            int colD = Math.abs(CheckP.col - king.col);
            int rowD = Math.abs(CheckP.row - king.row);

            if (colD == 0) {
                if (CheckP.row < king.row) {
                    for (int row = CheckP.row; row < king.row; row++) {
                        for (pices Pieces : simPieces) {
                            if (Pieces != king && Pieces.color != currentColor && Pieces.ismove(CheckP.col, row)) {
                                return false;
                            }
                        }
                    }
                }
                if (CheckP.row > king.row) {
                    for (int row = CheckP.row; row > king.row; row--) {
                        for (pices Pieces : simPieces) {
                            if (Pieces != king && Pieces.color != currentColor && Pieces.ismove(CheckP.col, row)) {
                                return false;
                            }
                        }
                    }
                }
            }
            else if (rowD == 0) {
                if (CheckP.col < king.col) {
                    for (int col = CheckP.col; col < king.row; col++) {
                        for (pices Pieces : simPieces) {
                            if (Pieces != king && Pieces.color != currentColor && Pieces.ismove(col, CheckP.row)) {
                                return false;
                            }
                        }
                    }
                }
                if (CheckP.col > king.col) {
                    for (int col = CheckP.col; col > king.row; col--) {
                        for (pices Pieces : simPieces) {
                            if (Pieces != king && Pieces.color != currentColor && Pieces.ismove(col, CheckP.row)) {
                                return false;
                            }
                        }
                    }
                }
            }
            else if (colD == rowD) {
                if (CheckP.row < king.row) {
                    if (CheckP.col < king.col) {
                        for (int col = CheckP.col, row = CheckP.row; col < king.col; col++, row++) {
                            for (pices Pieces : simPieces) {
                                if (Pieces != king && Pieces.color != currentColor && Pieces.ismove(col, row)) {
                                    return true;
                                }
                            }
                        }
                    }
                    if (CheckP.col > king.col) {
                        for (int col = CheckP.col, row = CheckP.row; col > king.col; col--, row++) {
                            for (pices Pieces : simPieces) {
                                if (Pieces != king && Pieces.color != currentColor && Pieces.ismove(col, row)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                if (CheckP.row > king.row) {
                    if (CheckP.col < king.col) {
                        for (int col = CheckP.col, row = CheckP.row; col < king.col; col++, row--) {
                            for (pices Pieces : simPieces) {
                                if (Pieces != king && Pieces.color != currentColor && Pieces.ismove(col, row)) {
                                    return true;
                                }
                            }
                        }
                    }
                    if (CheckP.col > king.col) {
                        for (int col = CheckP.col, row = CheckP.row; col > king.col; col--, row--) {
                            for (pices Pieces : simPieces) {
                                if (Pieces != king && Pieces.color != currentColor && Pieces.ismove(col, row)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean isValidM(pices king,int colP,int rowP){

        boolean isValidM = false;

        king.col += colP;
        king.row += rowP;

        if(king.ismove(king.col,king.row)){

            if(king.hitP != null){
                simPieces.remove(king.hitP.getIndex());
            }
            if(!isIilegal(king)){
                isValidM = true;
            }
        }

        king.resetP();
        copyPieces(pieces,simPieces);

        return isValidM;
    }

    private boolean isStalemate(){

        int count = 0;

        for(pices Pieces : simPieces){
            if(Pieces.color != currentColor){
                count++;
            }
        }

        if(count == 1){
            return !kingCanMove(getKing(true));
        }
        return false;
    }
    private void CheckCastling() {
        if (Castling != null) {
            if (Castling.col == 0) {
                Castling.col += 3;
            }
            else if (Castling.col == 7) {
                Castling.col -= 2;
            }
            Castling.x = Castling.getX(Castling.col);
        }
    }
    private void ChangePlayer(){

        if(currentColor == WHITE) {
            currentColor = BLACK;

            //For Black Piece
            for(pices Pieces : pieces){
                if(Pieces.color == BLACK){
                    Pieces.twoStepped = false;
                }
            }
        }
        else
        {
            currentColor = WHITE;

            //For White Pieces
            for(pices Pieces : pieces){
                if(Pieces.color == WHITE){
                    Pieces.twoStepped = false;
                }
            }
        }
        active = null;
    }

    private boolean isPromotion(){

        if(active.type == Type.PAWN){
            if(currentColor == WHITE && active.row == 0 || currentColor ==BLACK && active.row == 7){
                promotionP.clear();
                promotionP.add(new Rook(currentColor,9,2));
                promotionP.add(new Knight(currentColor,9,3));
                promotionP.add(new Queen(currentColor,9,4));
                promotionP.add(new Bishop(currentColor,9,5));
                return true;
            }
        }
        return false;
    }

    private void promotingP(){

        if(mouse.Pressed) {
            for (pices Pieces : promotionP) {
                if (Pieces.col == mouse.x / ChessBoard.SQUARE_SIZE && Pieces.row == mouse.y / ChessBoard.SQUARE_SIZE) {
                    switch (Pieces.type) {
                        case QUEEN:
                            simPieces.add(new Queen(currentColor, active.col, active.row));
                            break;
                        case ROOK:
                            simPieces.add(new Rook(currentColor, active.col, active.row));
                            break;
                        case KNIGHT:
                            simPieces.add(new Knight(currentColor, active.col, active.row));
                            break;
                        case BISHOP:
                            simPieces.add(new Bishop(currentColor, active.col, active.row));
                            break;
                        default:
                            break;
                    }
                    simPieces.remove(active.getIndex());
                    copyPieces(simPieces, pieces);
                    active = null;
                    promotion = false;
                    ChangePlayer();
                }
            }
        }
    }
    public void paint(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        f2.draw(g2d);

        for(pices p : simPieces){
            p.draw(g2d);
        }
        if(active!=null){
            if(ismove) {

                if(isIilegal(active) || opponetCanCaptureK()) {
                    g2d.setColor(new Color(237, 71, 71));
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                    g2d.fillRect(active.col * ChessBoard.SQUARE_SIZE, active.row * ChessBoard.SQUARE_SIZE, ChessBoard.SQUARE_SIZE, ChessBoard.SQUARE_SIZE);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }
                else {
                    g2d.setColor(new Color(161, 246, 111));
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                    g2d.fillRect(active.col * ChessBoard.SQUARE_SIZE, active.row * ChessBoard.SQUARE_SIZE, ChessBoard.SQUARE_SIZE, ChessBoard.SQUARE_SIZE);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }
            }


            //Draw
            active.draw(g2d);

            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setFont(new Font("Book Antique", Font.PLAIN, 40));
            g2d.setColor(Color.WHITE);

            if (promotion){
                g2d.drawString("Promotion To:",700,150);
                for(pices Pieces : promotionP){
                    g2d.drawImage(Pieces.image,Pieces.getX(Pieces.col),Pieces.getY(Pieces.row),
                            ChessBoard.SQUARE_SIZE,ChessBoard.SQUARE_SIZE,null);
                }
            }
            else {
                if (currentColor == WHITE) {
                    if (CheckP != null && CheckP.color == BLACK) {
                        g2d.setColor(new Color(255, 28, 28));
                        g2d.drawString("The King", 700, 150);
                        g2d.drawString("is in Check", 700, 200);
                    }
                } else {
                    if (CheckP != null && CheckP.color == WHITE) {
                        g2d.setColor(Color.red);
                        g2d.drawString("The King", 700, 150);
                        g2d.drawString("is in Check", 700, 200);
                    }
                }
            }
            if(EndGame){
                String a ;
                if(currentColor == WHITE){
                    a = "White is Won This Game";
                }
                else {
                    a = "Black is Won This Game";
                }
                g2d.setFont(new Font("Arial", Font.PLAIN ,50 ));
                g2d.setColor(Color.yellow);
                g2d.drawString(a,100,300);
            }
            if(Stalemate){
                g2d.setFont(new Font("Arial", Font.PLAIN ,50 ));
                g2d.setColor(Color.yellow);
                g2d.drawString("This Game is Stalement",100,300);
            }
        }
    }
}
