package com.github.chMatvey.springConcepts.screensaver;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

@Component
public abstract class ColorFrame extends JFrame {

    public ColorFrame() throws HeadlessException {
        setSize(200, 200);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void showRandomPlace() {
        Random random = new Random();
        setLocation(random.nextInt(1920), random.nextInt(1080));
        getContentPane().setBackground(getColor());
        repaint();
    }

    protected abstract Color getColor();
}
