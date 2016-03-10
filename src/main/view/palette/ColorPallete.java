package main.view.palette;

import main.model.palette.PaletteModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by dwson on 2/14/16.
 */
public class ColorPallete extends JButton implements Observer{

    private Color currColor;
    private PaletteModel paletteModel;
    private Image img;
    private Graphics2D g2;

    public ColorPallete(PaletteModel paletteModel) {

        super();

        this.paletteModel = paletteModel;

        this.layoutView();
        this.registerControllers();
    }

    private void layoutView(){

        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(true);

        currColor = paletteModel.getSelectedColor();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (img == null){
            img = createImage(50,50);
            g2 = (Graphics2D) img.getGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(Color.DARK_GRAY);
            g2.drawRect(0,0,49,49);
            drawPallete();
        }
        g.drawImage(img, 0, 0, null);
    }

    private void drawPallete(){
        g2.setPaint(currColor);
        g2.fillRect(1,1, 48, 48);
        repaint();
    }

    private void registerControllers(){

        ActionListener changeColor = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser
                        .showDialog(ColorPallete.this, "Choose Color", currColor);
                if (newColor != null){
                    paletteModel.setSelectedColor(newColor);
                }
            }
        };

        this.addActionListener(changeColor);
    }

    @Override
    public void update(Observable o, Object arg) {
//        System.out.println("update from ColorPallete");
        currColor = paletteModel.getSelectedColor();
        setBackground(currColor);
        drawPallete();
    }
}
