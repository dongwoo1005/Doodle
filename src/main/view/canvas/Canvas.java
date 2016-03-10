package main.view.canvas;

import main.model.canvas.CanvasModel;
import main.model.menuBar.MenuBarModel;
import main.model.palette.PaletteModel;
import main.model.playback.PlaybackModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by dwson on 2/13/16.
 */
public class Canvas extends JComponent implements Observer{

    private int currX, currY, oldX, oldY;

    PaletteModel paletteModel;
    PlaybackModel playbackModel;
    CanvasModel canvasModel;
    MenuBarModel menuBarModel;

    public Canvas(PaletteModel paletteModel, PlaybackModel playbackModel, CanvasModel canvasModel, MenuBarModel menuBarModel) {
//        super();
        this.paletteModel = paletteModel;
        this.playbackModel = playbackModel;
        this.canvasModel = canvasModel;
        this.menuBarModel = menuBarModel;

        setDoubleBuffered(false);
        this.layoutView();
        this.registerControllers();
    }

    private void layoutView(){
//        ImageIcon ii = new ImageIcon(canvasModel.getDrawing());
//        JScrollPane scrollPane = new JScrollPane(new JLabel(ii));
//        add(scrollPane);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (canvasModel.getDrawing() == null){

            int width = canvasModel.getWidth()==0 ?  getSize().width : canvasModel.getWidth();
            int height = canvasModel.getHeight()==0 ? getSize().height : canvasModel.getHeight();

            canvasModel.setWidth(width);
            canvasModel.setHeight(height);

            canvasModel.setDrawing(createImage(width, height));
            canvasModel.setG2((Graphics2D) canvasModel.getDrawing().getGraphics());
            canvasModel.getG2().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (!canvasModel.getxStrokes().isEmpty()){
                clear();
                canvasModel.drawToNthStroke(playbackModel.getCurrTick());
            } else {
                clear();
            }

//            System.out.println(getSize().width + " " + getSize().height);
        }
        g.drawImage(canvasModel.getDrawing(), 0, 0, null);
    }

    public void clear(){
        canvasModel.setSelectedColor(paletteModel.getSelectedColor());
        canvasModel.clear();
        repaint();
//        System.out.println("Cleared");
    }

    private void registerControllers(){
        MouseAdapter mousePressed = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
                canvasModel.clearXCurrStroke();
                canvasModel.clearYCurrStroke();
//                System.out.println("Mouse Pressed on Canvas");
            }
        };

        MouseMotionAdapter mouseDragged = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

//                System.out.println("Mouse Dragged on Canvas");

                currX = e.getX();
                currY = e.getY();

                if (canvasModel.getG2() != null){
                    // draw
                    canvasModel.getG2().setStroke(new BasicStroke(
                            paletteModel.getStrokeThickness(),
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND));
                    canvasModel.getG2().drawLine(oldX, oldY, currX, currY);
                    repaint();
                    // save polyline point for the stroke
                    if (canvasModel.getxCurrStroke().isEmpty()){
                        canvasModel.addToXCurrStroke(oldX);
                        canvasModel.addToYCurrStroke(oldY);
                    }
                    canvasModel.addToXCurrStroke(currX);
                    canvasModel.addToYCurrStroke(currY);
                    // set old coord
                    oldX = currX;
                    oldY = currY;

                }
            }
        };

        MouseAdapter mouseReleased = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

//                System.out.println("Mouse Released on Canvas");

                currX = e.getX();
                currY = e.getY();

                if (canvasModel.getG2() != null){
                    // draw
                    canvasModel.getG2().setStroke(new BasicStroke(
                            paletteModel.getStrokeThickness(),
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND));
                    canvasModel.getG2().drawLine(oldX, oldY, currX, currY);
                    repaint();

                    // save the last stroke polyline point
                    canvasModel.addToXCurrStroke(currX);
                    canvasModel.addToYCurrStroke(currY);

//                    System.out.println("Stroked");

                    // remove if stroked in the past stroke view
                    if (playbackModel.getCurrTick() < playbackModel.getMaxTick()){
                        canvasModel.removeXStroke(playbackModel.getCurrTick());
                        canvasModel.removeYStroke(playbackModel.getCurrTick());
                        canvasModel.removeStrokeColors(playbackModel.getCurrTick());
                        canvasModel.removeStrokeThicknesses(playbackModel.getCurrTick());
                    }

                    // save the list of stroke points
                    canvasModel.addToxStrokes(canvasModel.getxCurrStroke());
                    canvasModel.addToyStrokes(canvasModel.getyCurrStroke());
                    canvasModel.addToStrokeColors(paletteModel.getSelectedColor());
                    canvasModel.addToStrokeThicknesses(paletteModel.getStrokeThickness());

                    // Update Timeline JSlider
                    playbackModel.setMaxTick(playbackModel.getCurrTick()+1);
                    playbackModel.setCurrTick(playbackModel.getCurrTick()+1);
                    if (!menuBarModel.isChangesMade()){
                        menuBarModel.setChangesMade(true);
                    }
                }
//                System.out.println("Mouse Pressed" + currX +","+currY);
            }
        };

        this.addMouseListener(mousePressed);
        this.addMouseListener(mouseReleased);
        this.addMouseMotionListener(mouseDragged);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (menuBarModel.isFitSized()){
//                    System.out.println("Redrawing FitSized");
                    canvasModel.redrawFitSized(getSize().width, getSize().height);
                }
            }
        });
    }

    private void updateFromPallete(){
        canvasModel.setSelectedColor(paletteModel.getSelectedColor());
        canvasModel.setSelectedWidth(paletteModel.getStrokeThickness());
        canvasModel.setPallete();
    }

    @Override
    public void update(Observable o, Object arg) {
//        System.out.println("update from Canvas");
        updateFromPallete();
        if (menuBarModel.isFitSized() && !menuBarModel.isFitButtonPressed()){
            menuBarModel.setFitButtonPressed(true);
            canvasModel.redrawFitSized(getSize().width, getSize().height);
        } else if (!menuBarModel.isFitSized() && menuBarModel.isFitButtonPressed()){
            menuBarModel.setFitButtonPressed(false);
            canvasModel.redrawFullSized();
        }

        repaint();

//        System.out.println("total:" + playbackModel.getMaxTick() + ", curr:" + playbackModel.getCurrTick());
    }
}
