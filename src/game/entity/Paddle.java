package game.entity;

import game.intrfaces.Commons;
import game.intrfaces.Entity;

import java.awt.event.KeyEvent;

public class Paddle extends Sprite implements Entity {
    private int dx;

    public Paddle() {
        init(0, 0);
    }

    @Override
    public void init(int x, int y) {
        this.setImage("src/resources/paddle.png");
        resetState();
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    @Override
    public void move() {
        this.setX(this.getX() + 2 * getDx());
        if (this.getX() <= 0) {
            this.setX(0);
        }
        if (this.getX() >= Commons.WIDTH - getImageWidth()) {
            this.setX(Commons.WIDTH - getImageWidth());
        }
    }

    @Override
    public void resetState() {
        this.setX(Commons.INIT_PADDLE_X);
        this.setY(Commons.INIT_PADDLE_Y);
    }

    void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                this.setDx(-1);
                break;
            case KeyEvent.VK_RIGHT:
                this.setDx(1);
                break;
        }
    }

    void keyReleased(KeyEvent event) {
        int key = event.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                this.setDx(0);
                break;
        }
    }
}
