package game.entity;

import javax.swing.*;
import java.awt.*;

public abstract class Sprite extends MovableObject {

    private int x;
    private int y;
    private int imageWidth;
    private int imageHeight;
    private Image image;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setImage(String path) {
        this.image = new ImageIcon(path).getImage();
        setImageDimensions();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }

    private void setImageDimensions() {
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }

    void drawObject(Graphics2D graphics2D, Board board) {
        graphics2D.drawImage(image, x, y, imageWidth, imageHeight, board);
    }

}
