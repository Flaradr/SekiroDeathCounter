package service;

import domain.character.EldenRingCharacter;
import domain.character.FromSoftwareCharacter;
import domain.filereader.EldenRingSaveFileReader;
import exception.CharacterNotFoundException;

import java.util.List;

public class EldenRingService implements FromSoftwareService {

    private final EldenRingSaveFileReader content;

    public EldenRingService(EldenRingSaveFileReader content) {
        this.content = content;
    }

    public EldenRingCharacter getCharacterById(int saveSlotIndex) throws CharacterNotFoundException {
        return content.findById(saveSlotIndex)
                .orElseThrow(() -> new CharacterNotFoundException("Character not found for index " + saveSlotIndex));
    }

    public List<? extends FromSoftwareCharacter> getAllCharacters() {
        return content.findAll();
    }

    public List<String> getAllNames() {
        return content.getAllCharactersNames();
    }
}
