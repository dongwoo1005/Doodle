package main.view.playback;

import main.model.canvas.CanvasModel;
import main.model.playback.PlaybackModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by dwson on 2/13/16.
 */
public class Playback extends JPanel implements Observer{

    PlaybackModel playbackModel;
    CanvasModel canvasModel;
    MyButton playButton, startButton, endButton;

    public Playback(PlaybackModel playbackModel, CanvasModel canvasModel) {
        super();

        this.playbackModel = playbackModel;
        this.canvasModel = canvasModel;

        this.layoutView();
        this.registerControllers();
    }

    private void layoutView(){

//        this.setPreferredSize(new Dimension(1024,150));
//        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        setBackground(Color.DARK_GRAY);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Add Play button
        playButton = new MyButton("Play");
        playButton.setEnabled(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.225;
        c.weighty = 1;

//        c.insets = new Insets(50,0,50,0);

        add(playButton, c);

        // Add timeline

        Timeline timeline = new Timeline(playbackModel, canvasModel);
        playbackModel.addObserver(timeline);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(0,0,0,0);

        add(timeline, c);

        // Add Start button
        startButton = new MyButton("Start");
        startButton.setEnabled(false);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 0.05;
        c.weighty = 1;
//        c.insets = new Insets(50,0,50,0);

        add(startButton, c);

        // Add End button
        endButton = new MyButton("End");
        endButton.setEnabled(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 3;
        c.gridy = 0;
        c.weightx = 0.05;
        c.weighty = 1;
//        c.insets = new Insets(50,0,50,0);

        add(endButton, c);
    }

    private void registerControllers(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == startButton){
                    playbackModel.setCurrTick(0);
                    canvasModel.clear();
                } else if (e.getSource() == endButton){
                    playbackModel.setCurrTick(playbackModel.getMaxTick());
                    canvasModel.drawAll();
                } else if (e.getSource() == playButton){
                    playbackModel.setTimer(new Timer());
                    playbackModel.setCurrTick(0);
                    canvasModel.clear();
                    playbackModel.getTimer().scheduleAtFixedRate(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    if (playbackModel.getCurrTick() < playbackModel.getMaxTick()) {
                                        playbackModel.setCurrTick(playbackModel.getCurrTick() + 1);
                                        canvasModel.drawNthStroke(playbackModel.getCurrTick() - 1);
                                    }
                                    else if (playbackModel.getCurrTick() == playbackModel.getMaxTick()) {
                                        playbackModel.killTimer();
                                    }
                                }
                            },
                            1000, 1000
                    );

                }
            }
        };

        playButton.addActionListener(actionListener);
        startButton.addActionListener(actionListener);
        endButton.addActionListener(actionListener);
    }

    private void refreshButtons(){

        if (playbackModel.getMaxTick() > 0){
            playButton.setEnabled(true);
        } else if (playbackModel.getMaxTick() == 0){
            playButton.setEnabled(false);
        }

        if (playbackModel.getMaxTick() > 0){
            if (playbackModel.getCurrTick() > 0){
                startButton.setEnabled(true);
            } else {
                startButton.setEnabled(false);
            }
            if (playbackModel.getCurrTick() == playbackModel.getMaxTick()){
                endButton.setEnabled(false);
            } else {
                endButton.setEnabled(true);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        refreshButtons();
    }
}
