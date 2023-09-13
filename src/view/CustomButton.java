package view;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A class providing buttons with custom colors.
 * <p>
 * Buttons can be customized to have different colors when hovered or pressed.
 *
 * @author WJW02
 */
public class CustomButton extends JButton implements MouseListener {
    /**
     * The background color of the button when no action is applied.
     */
    private Color normalColor;
    /**
     * The background color of the button when hovered.
     */
    private Color hoverColor;
    /**
     * The background color of the button when pressed.
     */
    private Color pressedColor;

    /**
     * Creates a custom button without text.
     *
     * @param normalColor the background color of the button when no action is applied
     * @param hoverColor the background color of the button when hovered
     * @param pressedColor the background color of the button when pressed
     */
    public CustomButton(Color normalColor, Color hoverColor, Color pressedColor) {
        super();
        customize(normalColor, hoverColor, pressedColor);
    }

    /**
     * Creates a custom button with text.
     *
     * @param text the text of the button
     * @param textColor the color of the text on the button
     * @param normalColor the background color of the button when no action is applied
     * @param hoverColor the background color of the button when hovered
     * @param pressedColor the background color of the button when pressed
     */
    public CustomButton(String text, Color textColor, Color normalColor, Color hoverColor, Color pressedColor) {
        super(text);
        setForeground(textColor);
        customize(normalColor, hoverColor, pressedColor);
    }

    /**
     * Customizes the button with colors.
     *
     * @param normalColor the background color of the button when no action is applied
     * @param hoverColor the background color of the button when hovered
     * @param pressedColor the background color of the button when pressed
     */
    private void customize(Color normalColor, Color hoverColor, Color pressedColor) {
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;
        this.pressedColor = pressedColor;

        setBackground(normalColor);
        addMouseListener(this);
    }

    /**
     * Sets the background color of the button to {@link #pressedColor} when pressed and then to hoverColor when released.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        mousePressed(e);
        mouseReleased(e);
    }

    /**
     * Sets the background color of the button to {@link #pressedColor} when pressed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        setBackground(pressedColor);
    }

    /**
     * Sets the background color of the button to {@link #hoverColor} when released.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        setBackground(hoverColor);
    }

    /**
     * Sets the background color of the button to {@link #hoverColor} when entered.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        setBackground(hoverColor);
    }

    /**
     * Sets the background color of the button to {@link #normalColor} when exited.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        setBackground(normalColor);
    }
}