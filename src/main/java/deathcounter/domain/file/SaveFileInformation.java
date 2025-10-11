package deathcounter.domain.file;

import deathcounter.domain.FromSoftwareGames;
import deathcounter.domain.character.FromSoftwareCharacter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class SaveFileInformation {

    public static final String SAVE_FILE_PATH_FIELD_NAME = "saveFilePath";
    public static final String CHOSEN_GAME_FIELD_NAME = "chosenGame";
    public static final String CHARACTER_FIELD_NAME = "character";
    public static final String SLOT_INDEX_FIELD_NAME = "slotIndex";

    private final Set<PropertyChangeListener> listeners;

    private Path saveFilePath;
    private FromSoftwareGames chosenGame;
    private FromSoftwareCharacter character;
    private int slotIndex;

    public SaveFileInformation() {
        listeners = new HashSet<>(25);
        slotIndex = 0;
    }

    public Path getSaveFilePath() {
        return saveFilePath;
    }

    public void setSaveFilePath(Path saveFilePath) {
        Path previousPath = this.saveFilePath;
        this.saveFilePath = saveFilePath;
        firePropertyChange(SAVE_FILE_PATH_FIELD_NAME, previousPath, saveFilePath);
    }

    public FromSoftwareGames getChosenGame() {
        return chosenGame;
    }

    public void setChosenGame(FromSoftwareGames chosenGame) {
        FromSoftwareGames previousGame = this.chosenGame;
        this.chosenGame = chosenGame;
        firePropertyChange(CHOSEN_GAME_FIELD_NAME, previousGame, chosenGame);

    }

    public FromSoftwareCharacter getCharacter() {
        return character;
    }

    public void setCharacter(FromSoftwareCharacter character) {
        FromSoftwareCharacter previousCharacter = this.character;
        this.character = character;
        firePropertyChange(CHARACTER_FIELD_NAME, previousCharacter, character);
    }

    public int getSlotIndex() {
        return this.slotIndex;
    }

    public void setSlotIndex(int slotIndex) {
        int previousIndex = this.slotIndex;
        this.slotIndex = slotIndex;
        firePropertyChange(SLOT_INDEX_FIELD_NAME, previousIndex, slotIndex);
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

    protected void firePropertyChange(String editable, Object oldValue, Object newValue) {
        PropertyChangeEvent evt = new PropertyChangeEvent(this, editable, oldValue, newValue);
        for (PropertyChangeListener listener : listeners) {
            listener.propertyChange(evt);
        }
    }
}
