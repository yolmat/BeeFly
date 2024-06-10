package entities.beePlayer;

import java.awt.*;

public class Pipe {
    public int getPipeX(int boardWidth) {
        return boardWidth;
    };

    public int getPipeY() {
        return 0;
    };

    public int getPipeWidth() {
        int width = 64;
        return width;
    }

    public int getPipe  Height() {
        int height = 512;
        return height;
    }

    public boolean getPassed() {
        return false;
    }

    Image img;

    public Pipe(Image img) {
        this.img = img;
    }

}
