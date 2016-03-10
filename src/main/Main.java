package main;

import main.model.canvas.CanvasModel;
import main.model.menuBar.MenuBarModel;
import main.model.palette.PaletteModel;
import main.model.playback.PlaybackModel;
import main.view.canvas.Canvas;
import main.view.menuBar.MenuBar;
import main.view.palette.Palette;
import main.view.playback.Playback;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dongwoo on 13/02/2016.
 */
public class Main {

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void createAndShowGUI(){
        JFrame frame = new JFrame("Doodle!");

        Container pane = frame.getContentPane();
        pane.setLayout(new GridBagLayout());
        pane.setPreferredSize(new Dimension(1024,768));
        GridBagConstraints c = new GridBagConstraints();

        // Create Models
        PaletteModel paletteModel = new PaletteModel();
        PlaybackModel playbackModel = new PlaybackModel();
        CanvasModel canvasModel = new CanvasModel();
        MenuBarModel menuBarModel = new MenuBarModel();

        // Create menuBar
        MenuBar menuBar = new MenuBar(paletteModel, playbackModel, canvasModel, menuBarModel);
        menuBarModel.addObserver(menuBar);
//        paletteModel.addObserver(menuBar);
//        playbackModel.addObserver(menuBar);
//        canvasModel.addObserver(canvasModel);
        frame.setJMenuBar(menuBar);

        // Create mainPanel - canvas and pallete
        JPanel mainPanel = new JPanel();
//        JScrollPane mainPanel = ;
//                JScrollPane canvasPane = new JScrollPane(canvas, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        mainPanel.setLayout(new OverlayLayout(mainPanel));
        mainPanel.setSize(2000, 2000);

        // Create palette
        Palette palette = new Palette(paletteModel);
        palette.setMaximumSize(new Dimension(50,100));
        palette.setAlignmentX(0.0f);
        palette.setAlignmentY(0.5f);

        // Create canvas
        Canvas canvas = new Canvas(paletteModel, playbackModel, canvasModel, menuBarModel);
        paletteModel.addObserver(canvas);
        playbackModel.addObserver(canvas);
        menuBarModel.addObserver(canvas);
        canvasModel.addObserver(canvas);
//        canvas.setSize(2000,2000);
//        canvasModel.addObserver(canvas);
//        canvas.setPreferredSize(new Dimension(100, 100));
//        JScrollPane canvasPane = new JScrollPane(canvas, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(canvas);
        mainPanel.add(palette,0);

        JScrollPane scrollPane = new JScrollPane(mainPanel);

        // constraints for scrollPane(main panel)
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.999;
//        pane.add(mainPanel, c);
        pane.add(scrollPane, c);

        // Create playback
        Playback playback = new Playback(playbackModel, canvasModel);
        playbackModel.addObserver(playback);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 0.001;

        pane.add(playback, c);

        // Create the Window
//        frame.setPreferredSize(new Dimension(1024,768));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
