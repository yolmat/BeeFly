import javax.swing.*;
import beeFly.BeeFly;
import beeFly.rules.RulesGame;

public class App {
    public static void main(String[] args) throws Exception {

        RulesGame rules = new RulesGame();

        int boardWidth = rules.getRuleWidth();
        int boardHeight = rules.getRuleHeight();

        JFrame frame = new JFrame("beeFly");
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