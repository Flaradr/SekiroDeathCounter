package domain.file;

import domain.FromSoftwareGames;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class SaveFileInformation {

    private Set<PropertyChangeListener> listeners;

    private Path saveFilePath;
    private FromSoftwareGames chosenGame;
    private String stringifiedData;
    private int numberOfDeath;

    public SaveFileInformation() {
        listeners = new HashSet<>(25);
    }

    public Path getSaveFilePath() {
        return saveFilePath;
    }

    public void setSaveFilePath(Path saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    public FromSoftwareGames getChosenGame() {
        return chosenGame;
    }

    public void setChosenGame(FromSoftwareGames chosenGame) {
        this.chosenGame = chosenGame;
    }

    public String getStringifiedData() {
        return stringifiedData;
    }

    public void setStringifiedData(String stringifiedData) {
        this.stringifiedData = stringifiedData;
    }

    public int getNumberOfDeath() {
        return numberOfDeath;
    }

    public void setNumberOfDeath(int numberOfDeath) {
        this.numberOfDeath = numberOfDeath;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.add(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listeners.remove(listener);
    }

    protected void firePropertyChange(String editable, boolean oldValue, boolean newValue) {
        PropertyChangeEvent evt = new PropertyChangeEvent(this, editable, oldValue, newValue);
        for (PropertyChangeListener listener : listeners) {
            listener.propertyChange(evt);
        }
    }
}
