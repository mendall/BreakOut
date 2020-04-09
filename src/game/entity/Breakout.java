package game.entity;

import javax.swing.*;
import java.awt.*;

public class Breakout extends JFrame {

    public Breakout() {
        initUI();
    }

    private void initUI() {
        add(new Board());
        setTitle("Breakout");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var game = new Breakout();
            game.setVisible(true);
        });
    }

}
