package service;

import domain.character.SekiroCharacter;
import domain.file.SekiroSaveFileReader;
import exception.CharacterNotFoundException;

public class SekiroService implements FromSoftwareService {

    private SekiroSaveFileReader content;

    public SekiroService(SekiroSaveFileReader content) {
        this.content = content;
    }

    public SekiroCharacter getCharacterById(int saveSlotIndex) throws CharacterNotFoundException {
        return content.findById(saveSlotIndex)
                .orElseThrow(() -> new CharacterNotFoundException("Character not found for index " + saveSlotIndex));
    }
}
