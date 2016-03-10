package main.view.playback;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.net.URL;

/**
 * Created by Dongwoo on 17/02/2016.
 */
public class MyButton extends JButton {

    private Color pressedColor = Color.DARK_GRAY.darker();
    private Color rolledOverColor = Color.LIGHT_GRAY;
    private Color normalColor = new Color(0x404040);



    public MyButton(String iconName) {

        final ImageIcon normalIcon = new ImageIcon(
                new ImageIcon(getClass().getResource("/resources/icons/" + iconName + ".png"))
                        .getImage().getScaledInstance(16,16, Image.SCALE_SMOOTH));

        final ImageIcon rolledOverIcon = new ImageIcon(
                new ImageIcon(getClass().getResource("/resources/icons/" + iconName + "White.png"))
                .getImage().getScaledInstance(16,16, Image.SCALE_SMOOTH));

        final ImageIcon pressedIcon = new ImageIcon(
                new ImageIcon(getClass().getResource("/resources/icons/" + iconName + "DarkGray.png"))
                        .getImage().getScaledInstance(16,16, Image.SCALE_SMOOTH));

        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(true);

        setBackground(normalColor);
//        setForeground(Color.WHITE);

        setIcon(normalIcon);

        setSize(32,32);

        addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (getModel().isPressed()){
                    setBackground(normalColor);
                    setIcon(pressedIcon);
                } else if (getModel().isRollover()){
                    setBackground(normalColor);
                    setIcon(rolledOverIcon);
                } else {
                    setBackground(normalColor);
                    setIcon(normalIcon);
                }
            }
        });
    }

}
