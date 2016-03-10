package main.model.playback;

import java.io.Serializable;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Dongwoo on 18/02/2016.
 */
public class PlaybackModel extends Observable implements Serializable{

    private int maxTick;
    private int currTick;
    private transient Timer timer;

    public PlaybackModel() {
        maxTick = 0;
        currTick = 0;
        setChanged();
    }

    public void loadModel(PlaybackModel newModel){
        maxTick = newModel.getMaxTick();
        currTick = newModel.getCurrTick();

        setChanged();
        notifyObservers();
    }

    public void clearModel(){
        maxTick = 0;
        currTick = 0;
        if (timer != null){
            killTimer();
        }
        timer = null;

        setChanged();
        notifyObservers();
    }

    public int getMaxTick() {
        return maxTick;
    }

    public void setMaxTick(int maxTick) {
        this.maxTick = maxTick;
        setChanged();
        notifyObservers();
    }

    public int getCurrTick() {
        return currTick;
    }

    public void setCurrTick(int currTick) {
        this.currTick = currTick;
        setChanged();
        notifyObservers();
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void killTimer(){
        timer.cancel();
        timer.purge();
    }
}
