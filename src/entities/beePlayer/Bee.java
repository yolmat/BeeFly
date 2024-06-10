package entities.beePlayer;

import java.awt.*;

public class Bee {

    public int getBeeX(int boardWidth) {
        int beeX = boardWidth / 8;
        return beeX;
    };

    public int getBeeY(int boardWidth) {
        int beeY = boardWidth / 2;
        return beeY;
    };

    public int getBeeWidth() {
        int width = 34;
        return width;
    }

    public int getBeeHeight() {
        int height = 24;
        return height;
    }

    Image img;

    public Bee(Image img) {
        this.img = img;
    }
}
