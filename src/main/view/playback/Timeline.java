package main.view.playback;

import main.model.canvas.CanvasModel;
import main.model.playback.PlaybackModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalSliderUI;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Dongwoo on 17/02/2016.
 */
public class Timeline extends JSlider implements Observer{

    private int min = 0;
    private int max;
    private int currValue;
    private int orientation = JSlider.HORIZONTAL;

    PlaybackModel playbackModel;
    CanvasModel canvasModel;


    public Timeline(PlaybackModel playbackModel, CanvasModel canvasModel) {

        this.playbackModel = playbackModel;
        this.canvasModel = canvasModel;

        max = playbackModel.getMaxTick();

        this.layoutView();
        this.registerControllers();
    }

    private void layoutView(){
        setMinimum(min);
        setMaximum(max);
        setValue(max);
        setOrientation(orientation);
        setSnapToTicks(true);
        setPaintTicks(true);

        setBackground(Color.DARK_GRAY);

        if (max>0){
            setMajorTickSpacing(1);
        }
    }

    private void registerControllers(){
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                JSlider source = (JSlider) e.getSource();
//                System.out.println("Timeline: stateChanged - setCurrTick to " +  source.getValue());
                if (getValueIsAdjusting()){
//                    System.out.println("Timeline: get value is adjusting");
                    playbackModel.setCurrTick(source.getValue());
                    canvasModel.drawToNthStroke(source.getValue());
                }
            }
        };
        this.addChangeListener(changeListener);
    }

    private void refreshSlider(){
//        System.out.println("Timeline - refreshSlider - setMaximum");
        setMaximum(max);
//        System.out.println("Timeline - refreshSlider - setValue");
        setValue(currValue);
        if (max>50){
            setMajorTickSpacing(10);
            setMinorTickSpacing(1);
        } else if (max>10){
            setMajorTickSpacing(5);
            setMinorTickSpacing(1);
        } else if (max>0){
            setMajorTickSpacing(1);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
//        System.out.println("update from Timeline");
        max = playbackModel.getMaxTick();
        currValue = playbackModel.getCurrTick();
//        System.out.println("total:" + playbackModel.getMaxTick() + ", curr:" + playbackModel.getCurrTick());
        refreshSlider();
    }
}
