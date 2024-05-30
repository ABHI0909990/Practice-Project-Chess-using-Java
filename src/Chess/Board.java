package Chess;

import piece.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel implements Runnable {

    public static int Size1 = 680;
    public static int Size2 = 680;
    final int FPS = 60;
    Thread gameThread;
    ChessBoard f2 = new ChessBoard();
    Mouse mouse = new Mouse();

    //pieces
    public static ArrayList<pices> pieces = new ArrayList<>();
    public static ArrayList<pices> simPieces = new ArrayList<>();
    pices active;

    // color
    public static final int WHITE= 0;
    public static final int BLACK= 1;
    int currentColor = WHITE;

    public Board() {
        setPreferredSize(new Dimension(Size1, Size2));
        setBackground(Color.gray);
        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        setPieces();
        copyPieces(pieces,simPieces);

    }

    public void launch() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public pices getPieces(int col , int row){
        for(pices piece : pieces){
            if(piece.col == col && piece.row == row){
                return piece;
            }
        }

        return null;
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
                active.updatePosition();
                active = null;
            }
        }
    }
    public void simulate() {

        active.x = mouse.x - ChessBoard.HalfSquareSize;
        active.y = mouse.y - ChessBoard.HalfSquareSize;
        active.col = active.getCol(active.x);
        active.row = active.getRow(active.y);

    }

    public void paint(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        f2.draw(g2d);

        for(pices p : simPieces){
            p.draw(g2d);
        }
        if(active !=null){

            g2d.setColor(Color.white);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            g2d.fillRect(active.col*ChessBoard.SQUARE_SIZE, active.row*ChessBoard.SQUARE_SIZE,
                    ChessBoard.SQUARE_SIZE, ChessBoard.SQUARE_SIZE);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

            //Draw
            active.draw(g2d);

        }
    }
}
