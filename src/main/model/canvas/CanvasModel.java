package main.model.canvas;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * Created by Dongwoo on 18/02/2016.
 */
public class CanvasModel extends Observable implements Serializable{

    private transient Image drawing;
    private transient Graphics2D g2;

    // image width and height
    private int width, height;

    private Color selectedColor;
    private int selectedWidth;

    private List<Integer> xCurrStroke, yCurrStroke;
    private List<List<Integer>> xStrokes, yStrokes;
    private List<Color> strokeColors;
    private List<Integer> strokeThicknesses;

    private transient Timer timer;
    private transient int currFrame;

    public CanvasModel() {

        xCurrStroke = new ArrayList<>();
        yCurrStroke = new ArrayList<>();
        xStrokes = new ArrayList<>();
        yStrokes = new ArrayList<>();
        strokeColors = new ArrayList<>();
        strokeThicknesses = new ArrayList<>();

        setChanged();
    }

    public Image getDrawing() {
        return drawing;
    }

    public void setDrawing(Image drawing) {
        this.drawing = drawing;
//        System.out.println("Canvas Model: set Drawing");
        setChanged();
//        notifyObservers();
    }

    public Graphics2D getG2() {
        return g2;
    }

    public void setG2(Graphics2D g2) {
        this.g2 = g2;
//        System.out.println("Canvas Model: set G2");
        setChanged();
        notifyObservers();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        setChanged();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        setChanged();
    }

    private void resizeImage(int width, int height){
        Image temp = drawing.getScaledInstance(width, height, Image.SCALE_FAST);
        drawing = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) drawing.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(temp, 0, 0, null);
        g2.setPaint(selectedColor);
    }

    public void redrawFitSized(int newWidth, int newHeight){
        resizeImage(newWidth, newHeight);

//        System.out.println("Canvas Model: redraw fit sized");
        setChanged();
        notifyObservers();
    }

    public void redrawFullSized(){
        resizeImage(width, height);

//        System.out.println("Canvas Model: redraw full sized");
        setChanged();
        notifyObservers();
    }

    public List<List<Integer>> getxStrokes() {
        return xStrokes;
    }

    public List<List<Integer>> getyStrokes() {
        return yStrokes;
    }

    public List<Color> getStrokeColors() {
        return strokeColors;
    }

    public List<Integer> getStrokeThicknesses() {
        return strokeThicknesses;
    }

    public void loadModel(CanvasModel newModel){

        width = newModel.getWidth();
        height = newModel.getHeight();

        selectedColor = newModel.getSelectedColor();
        selectedWidth = newModel.getSelectedWidth();

        xCurrStroke = newModel.getxCurrStroke();
        yCurrStroke = newModel.getyCurrStroke();
        xStrokes = newModel.getxStrokes();
        yStrokes = newModel.getyStrokes();
        strokeColors = newModel.getStrokeColors();
        strokeThicknesses = newModel.getStrokeThicknesses();

        drawing = null;

//        System.out.println("Canvas Model: load model");
        setChanged();
        notifyObservers();
    }

    public void clearModel(){
        drawing = null; g2 = null;
        width = 0; height = 0;
        selectedColor = null; selectedWidth = 0;

        xCurrStroke = new ArrayList<>();
        yCurrStroke = new ArrayList<>();
        xStrokes = new ArrayList<>();
        yStrokes = new ArrayList<>();
        strokeColors = new ArrayList<>();
        strokeThicknesses = new ArrayList<>();

        if (timer != null){
            timer.cancel();
            timer.purge();
        }
        timer = null;

//        System.out.println("Canvas Model: clear model");
        setChanged();
        notifyObservers();
    }

    public void clearXCurrStroke(){
        xCurrStroke = new ArrayList<>();
        setChanged();
    }

    public void clearYCurrStroke(){
        yCurrStroke = new ArrayList<>();
        setChanged();
    }

    public void addToXCurrStroke(int x){
        xCurrStroke.add(x);
        setChanged();
    }

    public void addToYCurrStroke(int y){
        yCurrStroke.add(y);
        setChanged();
    }

    public void removeXStroke(int index){
        xStrokes.subList(index, xStrokes.size()).clear();
        setChanged();
    }

    public void removeYStroke(int index){
        yStrokes.subList(index, yStrokes.size()).clear();
        setChanged();
    }

    public void removeStrokeColors(int index){
        strokeColors.subList(index, strokeColors.size()).clear();
        setChanged();
    }

    public void removeStrokeThicknesses(int index){
        strokeThicknesses.subList(index, strokeThicknesses.size()).clear();
        setChanged();
    }

    public void addToxStrokes(List<Integer> stroke){
        xStrokes.add(stroke);
//        System.out.println("Canvas Model: add to xstrokes");
        setChanged();
        notifyObservers();
    }

    public void addToyStrokes(List<Integer> stroke){
        yStrokes.add(stroke);
//        System.out.println("Canvas Model: add to ystrokes");
        setChanged();
        notifyObservers();
    }

    public List<Integer> getxCurrStroke(){
        return xCurrStroke;
    }

    public List<Integer> getyCurrStroke(){
        return yCurrStroke;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
//        System.out.println("Canvas Model: set selected color");
        setChanged();
//        notifyObservers();
    }

    public int getSelectedWidth() {
        return selectedWidth;
    }

    public void setSelectedWidth(int selectedWidth) {
        this.selectedWidth = selectedWidth;
//        System.out.println("Canvas Model: set selected width");
        setChanged();
//        notifyObservers();
    }

    public void addToStrokeColors(Color currColor){
        strokeColors.add(currColor);
        setChanged();
    }

    public void addToStrokeThicknesses(int currWidth){
        strokeThicknesses.add(currWidth);
        setChanged();
    }

    public void setPallete(){
        if (g2 != null){
            g2.setPaint(selectedColor);
            g2.setStroke(new BasicStroke(selectedWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        }
    }

    public void clear(){
        g2.setPaint(Color.WHITE);
        g2.fillRect(0,0, drawing.getWidth(null), drawing.getHeight(null));
        g2.setPaint(selectedColor);
        setChanged();
//        System.out.println("Canvas Model: clear");
        notifyObservers();
    }

    public void drawAll(){
        GeneralPath polyline;
        List<Integer> xStroke, yStroke;
//        clear();
//        System.out.println(xStrokes.size());
        for (int i=0; i<xStrokes.size(); ++i){
            xStroke = xStrokes.get(i);
            yStroke = yStrokes.get(i);
            polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, xStroke.size());
            polyline.moveTo(xStroke.get(0), yStroke.get(0));
            for (int j=1; j<xStroke.size(); ++j){
                polyline.lineTo(xStroke.get(j), yStroke.get(j));
            }
            g2.setPaint(strokeColors.get(i));
            g2.setStroke(new BasicStroke(strokeThicknesses.get(i), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.draw(polyline);
//            System.out.println("draw" + i);
//            System.out.println("Canvas Model: draw All");
            setChanged();
            notifyObservers();
        }
    }

    public void drawToNthStroke(int n){
        GeneralPath polyline;
        List<Integer> xStroke, yStroke;
        clear();
//        System.out.println(xStrokes.size());
        for (int i=0; i<n; ++i){
            xStroke = xStrokes.get(i);
            yStroke = yStrokes.get(i);
            polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, xStroke.size());
            polyline.moveTo(xStroke.get(0), yStroke.get(0));
            for (int j=1; j<xStroke.size(); ++j){
                polyline.lineTo(xStroke.get(j), yStroke.get(j));
            }
            g2.setPaint(strokeColors.get(i));
            g2.setStroke(new BasicStroke(strokeThicknesses.get(i), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.draw(polyline);
//            System.out.println("draw" + i);
//            System.out.println("Canvas Model: draw to Nth Stroke");
            setChanged();
            notifyObservers();
        }
    }

    private void killTimer() {
        timer.cancel();
        timer.purge();
    }

    public void drawNthStroke(int n){
//        GeneralPath polyline;
        List<Integer> xStroke, yStroke;
        xStroke = xStrokes.get(n);
        yStroke = yStrokes.get(n);
        currFrame = 0;
        timer = new Timer();
        int FPS = 24;
        double timeLPF = (double) xStroke.size() / (double) FPS;
        int LPF = (int) Math.ceil(timeLPF); // Length Per Frame
//        System.out.println("stroke size = " + xStroke.size());
//        System.out.println("LPF = " + LPF);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int startIndex = currFrame * LPF;
                if (startIndex < xStroke.size()) {
//                    System.out.println("DRAW FRAME " + currFrame);
                    GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, LPF+2);
                    polyline.moveTo(xStroke.get(startIndex), yStroke.get(startIndex));
//                    System.out.println("polyline move to");
                    for (int j = startIndex; j < startIndex + LPF + 1; ++j) {
//                        System.out.println("in for loop: " + j);
                        if (j < xStroke.size()){
                            polyline.lineTo(xStroke.get(j), yStroke.get(j));
                        } else {
                            break;
                        }
                    }
//                    System.out.println("draw nth stroke: set property");
                    g2.setPaint(strokeColors.get(n));
                    g2.setStroke(new BasicStroke(strokeThicknesses.get(n), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
//                    System.out.println("DRAW POLYLINE + " + currFrame);
                    g2.draw(polyline);
                } else {
                    killTimer();
                    return;
                }
                currFrame++;
//                System.out.println("Canvas Model: draw Nth Stroke: currFrame = " + n + " " + currFrame );
                setChanged();
                notifyObservers();
            }
        }, 1000/FPS, 1000/FPS);
    }
}
