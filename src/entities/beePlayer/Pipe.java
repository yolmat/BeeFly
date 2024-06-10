package entities.beePlayer;

import java.awt.*;

import beeFly.rules.RulesGame;

public class Pipe {

    RulesGame rules = new RulesGame();
    int boardWidth = rules.getRuleWidth();

    public int y = 0;
    public int x = boardWidth;

    public int width = 64;
    public int height = 512;

    public boolean passed = false;

    public int getPipeX() {
        return boardWidth;
    };

    public int getPipeY() {
        return y;
    };

    public int getPipeWidth() {
        return width;
    }

    public int getPipeHeight() {
        return height;
    }

    public boolean getPassed() {
        return passed;
    }

    public Image img;

    public Pipe(Image img) {
        this.img = img;
    }

}
