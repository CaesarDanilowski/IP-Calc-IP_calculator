package com.cezarydanilowski;

import javax.swing.*;
import java.awt.*;

public class PaintComponent extends JComponent {
    private int width;
    private int height;
    private Image image;

    public PaintComponent(String path, int width, int height) {
        image = new ImageIcon(path).getImage();
        this.width = width;
        this.height = height;
    }

    public void paintComponent(Graphics g) {
        if (image == null) {
            return;
        }

        g.drawImage(image, 0, 0, width, height,null);
    }

    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
