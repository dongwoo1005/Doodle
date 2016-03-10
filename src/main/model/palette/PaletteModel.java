package main.model.palette;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Observable;

/**
 * Created by Dongwoo on 17/02/2016.
 */
public class PaletteModel extends Observable implements Serializable{

    private Color selectedColor = Color.ORANGE;
    private int strokeThickness = 10;
    private int maxThickness = 45;
    private int minThickness = 5;

    public PaletteModel(){
        setChanged();
    }

    public void loadModel(PaletteModel newModel){
        selectedColor = newModel.getSelectedColor();
        strokeThickness = newModel.getStrokeThickness();
        maxThickness = newModel.getMaxThickness();
        minThickness = newModel.getMinThickness();

        setChanged();
        notifyObservers();
    }

//    public void loadModel(FileInputStream fstream){
//        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
//        String line;
//        boolean startReading = false;
//        int lineNo = 0;
//        while ((line = br.readLine()) != null){
//            if (line == "PaletteModel") {
//                startReading = true;
//            } else if(line == "DONE") {
//                startReading = false;
//            } else {
//                if (startReading){
//                    lineNo++;
//
//                    private Color selectedColor = Color.ORANGE;
//                    private int strokeThickness = 10;
//                    private int maxThickness = 45;
//                    private int minThickness = 5;
//
//                    switch (lineNo){
//                        case 1:
//                            selectedColor =
//                            break;
//                        case 2:
//                            strokeThickness =
//                            break;
//                        case 3:
//                            break;
//                        case 4:
//                            break;
//                    }
//                }
//            }
//        }
//    }

    public void clearModel(){
        selectedColor = Color.ORANGE;
        strokeThickness = 10;
        maxThickness = 45;
        minThickness = 5;

        setChanged();
        notifyObservers();
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public int getStrokeThickness() {
        return strokeThickness;
    }

    public int getMaxThickness() {
        return maxThickness;
    }

    public int getMinThickness() {
        return minThickness;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
        setChanged();
        notifyObservers();
    }

    public void setStrokeThickness(int strokeThickness) {
        if (strokeThickness < minThickness){
            this.strokeThickness = minThickness;
        } else if (strokeThickness > maxThickness){
            this.strokeThickness = maxThickness;
        } else {
            this.strokeThickness = strokeThickness;
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "PaletteModel\n" + selectedColor.toString() + "\n" + strokeThickness + "\n" + maxThickness + "\n" + minThickness + "\nDONE";
    }


}
