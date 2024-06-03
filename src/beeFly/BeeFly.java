package beeFly;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BeeFly extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;

    // Imagens
    Image backgroundImg;
    Image beeImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // Abelha Classe
    int beeX = boardWidth / 8;
    int beeY = boardWidth / 2;
    int beeWidth = 34;
    int beeHeight = 24;

    class Bee {
        int x = beeX;
        int y = beeY;
        int width = beeWidth;
        int height = beeHeight;
        Image img;

        Bee(Image img) {
            this.img = img;
        }
    }

    // Canos Classe
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    // Logica do jogo
    Bee bee;
    int velocityX = -4; // mova os tubos para a velocidade esquerda (simula o movimento da abelha para a
                        // direita)
    int velocityY = 0; // mova a velocidade da abelha para cima/para baixo.
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipeTimer;
    boolean gameOver = false;
    double score = 0;

    public BeeFly() {

        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        // load images
        backgroundImg = new ImageIcon(getClass().getResource("./beeFlybg.png")).getImage();
        beeImg = new ImageIcon(getClass().getResource("./beeFly.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        // Abelha
        bee = new Bee(beeImg);
        pipes = new ArrayList<Pipe>();

        // colocar temporizador de tubos
        placePipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código a ser executado
                placePipes();
            }
        });
        placePipeTimer.start();

        // Tempo do Jogo
        gameLoop = new Timer(1000 / 60, this); // quanto tempo leva para iniciar o cronômetro, milissegundos passados
                                               // ​​entre os quadros
        gameLoop.start();
    }

    void placePipes() {
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4;

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Fundo
        g.drawImage(backgroundImg, 0, 0, this.boardWidth, this.boardHeight, null);

        // Abelha
        g.drawImage(beeImg, bee.x, bee.y, bee.width, bee.height, null);

        // Canos
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // Pontos
        g.setColor(Color.white);

        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Você Perdeu: " + String.valueOf((int) score), 10, 35);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }

    }

    public void move() {
        // Abelha
        velocityY += gravity;
        bee.y += velocityY;
        bee.y = Math.max(bee.y, 0); // aplique gravidade ao bee.y atual, limite o bee.y ao topo da tela

        // pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (!pipe.passed && bee.x > pipe.x + pipe.width) {
                score += 0.5; // 0,5 porque existem 2 tubos! então 0,5 * 2 = 1, 1 para cada conjunto de tubos
                pipe.passed = true;
            }

            if (collision(bee, pipe)) {
                gameOver = true;
            }
        }

        if (bee.y > boardHeight) {
            gameOver = true;
        }
    }

    boolean collision(Bee a, Pipe b) {
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) { // chamado a cada x milissegundos pelo temporizador gameLoop
        move();
        repaint();
        if (gameOver) {
            placePipeTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            velocityY = -9;

            if (gameOver) {

                bee.y = beeY;
                velocityY = 0;
                pipes.clear();
                gameOver = false;
                score = 0;
                gameLoop.start();
                placePipeTimer.start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
