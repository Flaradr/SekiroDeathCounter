package domain.file;

import domain.FromSoftwareGames;
import domain.character.FromSoftwareCharacter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class SaveFileInformation {

    private Set<PropertyChangeListener> listeners;

    private Path saveFilePath;
    private FromSoftwareGames chosenGame;
    private FromSoftwareCharacter character;


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

    public void setCharacter(FromSoftwareCharacter character) {
        this.character = character;
        refreshData();
    }

    public int getCharacterNumberOfDeath() {
        return character.getDeathCount();
    }

    public FromSoftwareCharacter getCharacter() {
        return character;
    }

    public void refreshData() {
        firePropertyChange("", null, null);
    }

    public int getNumberOfDeath() {
        return character.getDeathCount();
    }

    public void updateNumberOfDeath(int numberOfDeath) {
        character.setDeathCount(numberOfDeath);
        refreshData();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.add(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listeners.remove(listener);
    }

    protected void firePropertyChange(String editable, String oldValue, String newValue) {
        PropertyChangeEvent evt = new PropertyChangeEvent(this, editable, oldValue, newValue);
        for (PropertyChangeListener listener : listeners) {
            listener.propertyChange(evt);
        }
    }
}
