package main.view.palette;

import main.model.palette.PaletteModel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicToolBarUI;
import java.awt.*;

/**
 * Created by dwson on 2/13/16.
 */
public class Palette extends JPanel {

    PaletteModel paletteModel;

    public Palette(PaletteModel paletteModel) {

        super();
        this.paletteModel = paletteModel;

        this.layoutView();
    }

    private void layoutView(){

//        this.setPreferredSize(new Dimension(150,500));
//        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();



        // Add stoke thickness
        StrokeThickness strokeThickness = new StrokeThickness(paletteModel);
        paletteModel.addObserver(strokeThickness);

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.5;

        add(strokeThickness, c);

        // Add color pallete
        ColorPallete colorPallete = new ColorPallete(paletteModel);
        paletteModel.addObserver(colorPallete);

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 0.5;

        add(colorPallete, c);
    }
}
