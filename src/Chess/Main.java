package Chess;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame F1 = new JFrame("Chess");
        F1.setLayout(new BorderLayout());
        F1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        F1.setLocationRelativeTo(null);

        Board B1 = new Board();
        F1.add(B1);
        F1.pack();

        F1.setLocationRelativeTo(null);
        F1.setVisible(true);

        F1.pack();
        F1.setVisible(true);

        B1.launch();

    }
}
