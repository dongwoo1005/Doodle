package main.view.menuBar;

import main.model.canvas.CanvasModel;
import main.model.menuBar.MenuBarModel;
import main.model.palette.PaletteModel;
import main.model.playback.PlaybackModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Dongwoo on 13/02/2016.
 */
public class MenuBar extends JMenuBar implements Observer{

    JMenu file, view;
    JMenuItem newFile, loadFile, saveFile, exit;
//    JMenuItem saveFileAs;
    ButtonGroup buttonGroup;
    JRadioButtonMenuItem viewFullSize, viewFitSize;
    JFileChooser fc;
    FileNameExtensionFilter textFF, binFF;

    PaletteModel paletteModel;
    PlaybackModel  playbackModel;
    CanvasModel canvasModel;
    MenuBarModel menuBarModel;

    public MenuBar(PaletteModel paletteModel, PlaybackModel playbackModel, CanvasModel canvasModel, MenuBarModel menuBarModel) {

        this.paletteModel = paletteModel;
        this.playbackModel = playbackModel;
        this.canvasModel = canvasModel;
        this.menuBarModel = menuBarModel;

        this.layoutView();
        this.registerControllers();
    }

    public void layoutView(){

        // File Chooser
        fc = new JFileChooser();

        // File Filter
        textFF = new FileNameExtensionFilter("text file (*.txt)", "txt");
        binFF = new FileNameExtensionFilter("serialized binary file (*.ser)", "ser");
        fc.addChoosableFileFilter(textFF);
        fc.addChoosableFileFilter(binFF);
//        fc.setFileFilter(binFF);
        fc.setCurrentDirectory(new File("."));

        // File
        file = new JMenu("File");
        add(file);

        newFile = new JMenuItem("New");
        file.add(newFile);
        loadFile = new JMenuItem("Load");
        file.add(loadFile);
        saveFile = new JMenuItem("Save");
        saveFile.setEnabled(false);
        file.add(saveFile);
//        saveFileAs = new JMenuItem("Save as");
//        file.add(saveFileAs);

        file.addSeparator();

        exit = new JMenuItem("Exit");
        file.add(exit);

        // View
        view = new JMenu("View");
        add(view);

        buttonGroup = new ButtonGroup();

        viewFullSize = new JRadioButtonMenuItem("Actual Size");
        viewFullSize.setSelected(true);
        buttonGroup.add(viewFullSize);
        view.add(viewFullSize);

        viewFitSize = new JRadioButtonMenuItem("Fit to Screen");
        buttonGroup.add(viewFitSize);
        view.add(viewFitSize);
    }

    private void saveFileAsBinary(File file){
//        System.out.println("saving as binary");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(paletteModel);
            oos.writeObject(playbackModel);
            oos.writeObject(canvasModel);
            oos.close();
            fos.close();
        } catch (FileNotFoundException err){
//            System.out.println("File not found Exception:");
            err.printStackTrace();
        } catch (IOException err){
//            System.out.println("IO Exception:");
            err.printStackTrace();
        } catch (Exception err){
//            System.out.println("Exception from Saving");
            err.printStackTrace();
        }
    }

    private int savePrompt(){
        int ret = fc.showSaveDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION){
            File file = fc.getSelectedFile();

            // handle saving
            String extension = "";
            int i = file.toString().lastIndexOf('.');
            if (i>0){
                extension = file.toString().substring(i);
            }
//            System.out.println("extension is " + extension);
            if (extension.equals(".txt")){
//                FileWriter fw = new FileWriter(file.getAbsoluteFile());
//                BufferedWriter bw = new BufferedWriter(fw);
//                bw.write(paletteModel.toString());
////                    bw.write(playbackModel.toString());
////                    bw.write(canvasModel.toString());
//                bw.close();
                saveFileAsBinary(file);
            } else {
                saveFileAsBinary(file);
            }
        }
        return ret;
    }

    private void makeNew(){
        paletteModel.clearModel();
        playbackModel.clearModel();
        canvasModel.clearModel();
        menuBarModel.clearModel();
    }

    public void registerControllers(){

        newFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (true/* there were changes made */){
                    Object[] options = {"Save", "Don't Save", "Cancel"};
                    int ret = JOptionPane.showOptionDialog(null,
                            "Do you want to save changes?",
                            "Doodle!",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]
                    );
                    if (ret == 0){ // Save
                        int saveResult = savePrompt();
                        if (saveResult == JFileChooser.APPROVE_OPTION){
                            makeNew();
                        }
                    } else if (ret == 1){ // Don't Save
                        // clear models and make new models
                        makeNew();
                    } else { // Cancel
                        // do nothing
                    }
//                    System.out.println(ret);
                } else { /* no change was made*/
                    // clear and make new canvas
                    makeNew();
                }

            }
        });

        loadFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ret = fc.showOpenDialog(null);
                if (ret == JFileChooser.APPROVE_OPTION){
                    File file = fc.getSelectedFile();

//                    fc.getFileFilter();
                    // handle opened file
                    try {
                        FileInputStream fis = new FileInputStream(file);
                        ObjectInputStream ois = new ObjectInputStream(fis);

                        paletteModel.loadModel((PaletteModel) ois.readObject());
                        playbackModel.loadModel((PlaybackModel) ois.readObject());
                        canvasModel.loadModel((CanvasModel) ois.readObject());
                        menuBarModel.setChangesMade(false);

                        ois.close();
                        fis.close();

                    } catch (FileNotFoundException err){
//                        System.out.println("File not found Exception:");
                        err.printStackTrace();
                    } catch (IOException err){
//                        System.out.println("IO Exception:");
                        err.printStackTrace();
                    } catch (ClassNotFoundException err){
//                        System.out.println("ClassNotFound Exception:");
                        err.printStackTrace();
                    }
                }
            }
        });

        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePrompt();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        viewFullSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (menuBarModel.isFitSized()){
                    menuBarModel.setFitSized(false);
//                    System.out.println("Set to Full sized");
                }
            }
        });

        viewFitSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!menuBarModel.isFitSized()){
                    menuBarModel.setFitSized(true);
//                    System.out.println("Set to Fit Sized");
                }
            }
        });
    }

    private void updateButtons(){
        if (menuBarModel.isChangesMade()){
            saveFile.setEnabled(true);
        } else {
            saveFile.setEnabled(false);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateButtons();
    }
}
