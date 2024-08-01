package service;

import domain.character.FromSoftwareCharacter;
import domain.character.SekiroCharacter;
import domain.filereader.SekiroSaveFileReader;
import exception.CharacterNotFoundException;

import java.util.Collections;
import java.util.List;

public class SekiroService implements FromSoftwareService {

    private final SekiroSaveFileReader content;

    public SekiroService(SekiroSaveFileReader content) {
        this.content = content;
    }

    public SekiroCharacter getCharacterById(int saveSlotIndex) throws CharacterNotFoundException {
        return content.findById(saveSlotIndex)
                .orElseThrow(() -> new CharacterNotFoundException("Character not found for index " + saveSlotIndex));
    }

    public List<? extends FromSoftwareCharacter> getAllCharacters() {
        return Collections.emptyList();
    }

    public List<String> getAllNames() {
        return Collections.emptyList();
    }
}
