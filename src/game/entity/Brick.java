package game.entity;

import game.intrfaces.Entity;

public class Brick extends Sprite implements Entity {

    private boolean destroyed;

    public Brick(int x, int y) {
        init(x, y);
    }

    @Override
    public void init(int x, int y) {
        this.setX(x);
        this.setY(y);
        this.destroyed = false;
        this.setImage("src/resources/brick.png");
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    @Override
    void move() {
        // do nothing
    }

    @Override
    void resetState() {
        // do nothing
    }
}
