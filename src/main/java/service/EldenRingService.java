package service;

import domain.character.EldenRingCharacter;
import domain.file.EldenRingSaveFileReader;
import exception.CharacterNotFoundException;

public class EldenRingService implements FromSoftwareService {

    private final EldenRingSaveFileReader content;

    public EldenRingService(EldenRingSaveFileReader content) {
        this.content = content;
    }

    public EldenRingCharacter getCharacterById(int saveSlotIndex) throws CharacterNotFoundException {
        return content.findById(saveSlotIndex)
                .orElseThrow(() -> new CharacterNotFoundException("Character not found for index " + saveSlotIndex));
    }
}
