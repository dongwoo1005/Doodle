package main.view.palette;

import main.model.palette.PaletteModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by dwson on 2/14/16.
 */
public class StrokeThickness extends JButton implements Observer{

    private PaletteModel paletteModel;
    private int currThickness;
    private Color currColor;
    private Image img;
    private Graphics2D g2;
    private int currY, oldY;


    public StrokeThickness(PaletteModel paletteModel) {

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
        setFocusable(false);

        currThickness = paletteModel.getStrokeThickness();
        currColor = paletteModel.getSelectedColor();
    }

    private void registerControllers(){
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                oldY = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                currY = e.getY();
                if (currY > oldY){
                    // adjust thickness
                    paletteModel.setStrokeThickness(paletteModel.getStrokeThickness()-1);
                    // update old coord
                    oldY = currY;
                }
                else if (currY < oldY){
                    // Adjust thickness
                    paletteModel.setStrokeThickness(paletteModel.getStrokeThickness()+1);
                    //update old coord
                    oldY = currY;
                }
            }
        };
        this.addMouseMotionListener(mouseAdapter);
        this.addMouseListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (img == null){
            img = createImage(50,50);
            g2 = (Graphics2D) img.getGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            drawDot();
        }
        g.drawImage(img, 0, 0, null);
    }

    private void drawDot(){
        g2.setPaint(Color.WHITE);
        g2.fillRect(0,0, 50, 50);
        g2.setStroke(new BasicStroke(1));
        g2.setPaint(Color.DARK_GRAY);
        g2.drawRect(0,0,49,50);

        g2.setPaint(currColor);
        g2.setStroke(new BasicStroke(currThickness,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
        g2.drawLine(24, 25, 25, 25);
        repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
//        System.out.println("update StrokeThickness");
        currThickness = paletteModel.getStrokeThickness();
        currColor = paletteModel.getSelectedColor();
        drawDot();
    }
}
