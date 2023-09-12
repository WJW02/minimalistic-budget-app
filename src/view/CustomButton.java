package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CustomButton extends JButton implements MouseListener {
    private Color normalColor;
    private Color hoverColor;
    private Color pressedColor;

    public CustomButton(Color normalColor, Color hoverColor, Color pressedColor) {
        super();
        customize(normalColor, hoverColor, pressedColor);
    }

    public CustomButton(String text, Color textColor, Color normalColor, Color hoverColor, Color pressedColor) {
        super(text);
        setForeground(textColor);
        customize(normalColor, hoverColor, pressedColor);
    }

    private void customize(Color normalColor, Color hoverColor, Color pressedColor) {
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;
        this.pressedColor = pressedColor;

        setBackground(normalColor);
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mousePressed(e);
        mouseReleased(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setContentAreaFilled(false);
        setOpaque(true);
        setBackground(pressedColor);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setContentAreaFilled(true);
        setBackground(hoverColor);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setBackground(hoverColor);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBackground(normalColor);
    }
}