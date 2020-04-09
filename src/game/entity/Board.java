package game.entity;

import game.intrfaces.Commons;
import game.intrfaces.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Board extends JPanel implements Entity {
    private Timer timer;
    private String message = "Game over";
    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private boolean inGame = true;
    private ArrayList<Sprite> sprites = new ArrayList<>();
    private ArrayList<Sprite> movableObjects = new ArrayList<>();

    public Board() {
        init(0, 0);
    }

    @Override
    public void init(int x, int y) {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));
        gameInit();
    }

    private void gameInit() {
        bricks = new Brick[Commons.N_OF_BRICKS_PER_LINE * Commons.N_OF_BRICKS_PER_COLUMN];
        ball = new Ball();
        paddle = new Paddle();
        int k = 0;
        sprites.add(ball);
        sprites.add(paddle);
        movableObjects.add(ball);
        movableObjects.add(paddle);

        for (int i = 0; i < Commons.N_OF_BRICKS_PER_COLUMN; i++) {
            for (int j = 0; j < Commons.N_OF_BRICKS_PER_LINE; j++) {
                bricks[k] = new Brick(j * 40 + 30, i * 10 + 50);
                sprites.add(bricks[k]);
                k++;
            }
        }

        timer = new Timer(Commons.PERIOD, new GameCycle());
        timer.start();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        var g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        if (inGame) {
            drawObjects(g2d);
        } else {
            gameFinished(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics2D graphics2D) {
        for (Sprite sprite :
                sprites) {
            sprite.drawObject(graphics2D, this);
        }
    }

    private void gameFinished(Graphics2D graphics2D) {
        var font = new Font("Verdana", Font.BOLD, 18);
        FontMetrics fontMetrics = this.getFontMetrics(font);
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(font);
        graphics2D.drawString(message, (Commons.WIDTH - fontMetrics.stringWidth(message)) / 2, Commons.WIDTH / 2);
    }

    private class TAdapter extends KeyAdapter {
        public void keyReleased(KeyEvent event) {
            paddle.keyReleased(event);
        }

        public void keyPressed(KeyEvent event) {
            paddle.keyPressed(event);
        }
    }

    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            doGameCycle();
        }
    }

    private void doGameCycle() {
        for (Sprite sprite :
                movableObjects) {
            sprite.move();
        }
        checkCollision();
        repaint();
    }

    public void stopGame() {
        inGame = false;
        timer.stop();
    }

    private void checkCollision() {
        if (ball.getRectangle().getMaxY() > Commons.BOTTOM_EDGE) {
            stopGame();
        }

        int amountOfBricks = Commons.N_OF_BRICKS_PER_LINE * Commons.N_OF_BRICKS_PER_COLUMN;
        for (int i = 0, j = 0; i < amountOfBricks; i++) {

            if (bricks[i].isDestroyed()) {

                j++;
            }

            if (j == amountOfBricks) {

                message = "congratulation!";
                stopGame();
            }
        }

        if ((ball.getRectangle()).intersects(paddle.getRectangle())) {

            int paddleLPos = (int) paddle.getRectangle().getMinX();
            int ballLPos = (int) ball.getRectangle().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {

                ball.setxDir(-1);
                ball.setyDir(-1);
            }

            if (ballLPos >= first && ballLPos < second) {

                ball.setxDir(-1);
                ball.setyDir(-1 * ball.getyDir());
            }

            if (ballLPos >= second && ballLPos < third) {

                ball.setxDir(0);
                ball.setyDir(-1);
            }

            if (ballLPos >= third && ballLPos < fourth) {

                ball.setxDir(1);
                ball.setyDir(-1 * ball.getyDir());
            }

            if (ballLPos > fourth) {

                ball.setxDir(1);
                ball.setyDir(-1);
            }
        }

        for (int i = 0; i < amountOfBricks; i++) {

            if ((ball.getRectangle()).intersects(bricks[i].getRectangle())) {

                int ballLeft = (int) ball.getRectangle().getMinX();
                int ballHeight = (int) ball.getRectangle().getHeight();
                int ballWidth = (int) ball.getRectangle().getWidth();
                int ballTop = (int) ball.getRectangle().getMinY();

                var pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                var pointLeft = new Point(ballLeft - 1, ballTop);
                var pointTop = new Point(ballLeft, ballTop - 1);
                var pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                if (!bricks[i].isDestroyed()) {

                    if (bricks[i].getRectangle().contains(pointRight)) {

                        ball.setxDir(-1);
                    } else if (bricks[i].getRectangle().contains(pointLeft)) {

                        ball.setxDir(1);
                    }

                    if (bricks[i].getRectangle().contains(pointTop)) {

                        ball.setyDir(1);
                    } else if (bricks[i].getRectangle().contains(pointBottom)) {

                        ball.setyDir(-1);
                    }

                    sprites.remove(bricks[i]);
                    bricks[i].setDestroyed(true);
                }
            }
        }
    }

}
