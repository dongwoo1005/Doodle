package main.model.menuBar;

import java.util.Observable;

/**
 * Created by Dongwoo on 25/02/2016.
 */
public class MenuBarModel extends Observable{
//    String fileName = "Untitled";
    boolean fitSized;
    boolean fitButtonPressed;
    boolean changesMade;

    public MenuBarModel() {
        fitSized = false;
        fitButtonPressed = false;
        changesMade = false;

        setChanged();
    }

    public void clearModel(){
        changesMade = false;

        setChanged();
        notifyObservers();
    }

    public boolean isFitSized() {
        return fitSized;
    }

    public void setFitSized(boolean fitSized) {
        this.fitSized = fitSized;
        setChanged();
        notifyObservers();
    }

    public boolean isFitButtonPressed() {
        return fitButtonPressed;
    }

    public void setFitButtonPressed(boolean fitButtonPressed) {
        this.fitButtonPressed = fitButtonPressed;
        setChanged();
//        notifyObservers();
    }

    public boolean isChangesMade() {
        return changesMade;
    }

    public void setChangesMade(boolean changesMade) {
        this.changesMade = changesMade;
        setChanged();
        notifyObservers();
    }
}
