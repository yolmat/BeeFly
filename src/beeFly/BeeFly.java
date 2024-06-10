package beeFly;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

import beeFly.rules.RulesGame;
import entities.beePlayer.Bee;
import entities.beePlayer.Pipe;

public class BeeFly extends JPanel implements ActionListener, KeyListener {

    // Invoca classe de regras
    RulesGame rules = new RulesGame();

    // Variaveis das regras
    int boardWidth = rules.getRuleWidth();
    int boardHeight = rules.getRuleHeight();

    // Variaveis de imagens
    Image backgroundImg;
    Image beeImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // invoca classe Bee
    Bee bee = new Bee(beeImg);

    // Valores de Bee
    // Localização da entidade
    int beeX = bee.getBeeX(boardWidth);
    int beeY = bee.getBeeY(boardWidth);

    // Tamanhos da entidade
    int beeWidth = bee.getBeeWidth();
    int beeHeight = bee.getBeeHeight();

    // Invoca classe Pipe
    Pipe pipe = new Pipe(topPipeImg);

    // Localização da entidade
    int pipeX = pipe.getPipeX();
    int pipeY = pipe.getPipeY();

    // Tamanho da entidade
    int pipeHeight = pipe.getPipeHeight();
    int pipeWidth = pipe.getPipeWidth();

    // Logica do jogo
    int velocityX = -4; // mova os tubos para a velocidade esquerda (simula o movimento da abelha para a
                        // direita)
    int velocityY = 0; // mova a velocidade da abelha para cima/para baixo.
    int gravity = 1;

    // Cria um array para inputar os Pipe
    ArrayList<Pipe> pipes;
    Random random = new Random();

    // Variaveis de jogo
    Timer gameLoop;
    Timer placePipeTimer;
    boolean gameOver = false;
    double score = 0;

    // O jogo
    public BeeFly() {

        // Define o tamanho da tela
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        // Carrega Imagens
        backgroundImg = new ImageIcon(getClass().getResource("./beeFlybg.png")).getImage();
        beeImg = new ImageIcon(getClass().getResource("./beeFly.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        // invoca a abelha para dentro do jogo
        bee = new Bee(beeImg);

        // invoca os canos para dentro do jogo
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

    // Cria as regras os canos para a parte de cima e para a parte de baixp
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

    // Cria o visual dos canos para cima e para baixo
    public void draw(Graphics g) {
        // Fundo
        g.drawImage(backgroundImg, 0, 0, this.boardWidth, this.boardHeight, null);

        // Abelha
        g.drawImage(beeImg, beeX, beeY, beeWidth, beeHeight, null);

        // Canos
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // Visual do contador de pontos
        g.setColor(Color.white);

        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Você Perdeu: " + String.valueOf((int) score), 10, 35);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }

    }

    // Regras para a movimentação da abelha
    public void move() {
        // Abelha
        velocityY += gravity;
        beeY += velocityY;
        beeY = Math.max(beeY, 0); // aplique gravidade ao beeY atual, limite o beeY ao topo da tela

        // Invoca os canos e deixa eles de sendo generados de forma continua
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            // Contabilização dos da abelha apos passar um cano
            if (!pipe.passed && beeX > pipe.x + pipe.width) {
                score += 0.5; // 0,5 porque existem 2 tubos! então 0,5 * 2 = 1, 1 para cada conjunto de tubos
                pipe.passed = true;
            }

            // Apos colisão gameOver
            if (collision(bee, pipe)) {
                gameOver = true;
            }
        }

        if (beeY > boardHeight) {
            gameOver = true;
        }
    }

    // Identifica colisão
    boolean collision(Bee a, Pipe b) {
        return beeX < b.x + b.width &&
                beeX + beeWidth > b.x &&
                beeY < b.y + b.height &&
                beeY + beeHeight > b.y;
    }

    // Contabilizador
    @Override
    public void actionPerformed(ActionEvent e) { // chamado a cada x milissegundos pelo temporizador gameLoop
        move();
        repaint();
        if (gameOver) {
            placePipeTimer.stop();
            gameLoop.stop();
        }
    }

    // Tecla para jogar "space"
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            velocityY = -9;

            if (gameOver) {

                beeY = bee.getBeeY(boardWidth);
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
