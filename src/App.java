import javax.swing.*;
import beeFly.BeeFly;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 360;
        int boardHeight = 640;

        JFrame frame = new JFrame("Flappy Bird");
        // frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BeeFly jogoBeeFly = new BeeFly();
        frame.add(jogoBeeFly);
        frame.pack();
        jogoBeeFly.requestFocus();
        frame.setVisible(true);
    }
}