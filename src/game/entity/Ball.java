package game.entity;

import game.intrfaces.Commons;
import game.intrfaces.Entity;

public class Ball extends Sprite implements Entity {

    private int xDir;
    private int yDir;

    public Ball() {
        init(1, -1);
    }

    @Override
    public void init(int x, int y) {
        this.xDir = x;
        this.yDir = y;
        this.setImage("src/resources/ball.png");
        resetState();
    }

    @Override
    public void resetState() {
        setX(Commons.INIT_BALL_X);
        setY(Commons.INIT_BALL_Y);
    }

    @Override
    public void move() {
        setX(getX() + this.xDir);
        setY(getY() + 2 * this.yDir);

        if (getX() <= 0) {
            this.xDir = 1;
        }
        if (getX() == Commons.WIDTH - getImageWidth()) {
            this.xDir = -1;
        }
        if (getY() <= 0) {
            this.yDir = 1;
        }
    }

    public void setxDir(int xDir) {
        this.xDir = xDir;
    }

    public void setyDir(int yDir) {
        this.yDir = yDir;
    }

    public int getyDir() {
        return yDir;
    }
}
