package service;

import domain.character.FromSoftwareCharacter;

import java.util.List;

public interface FromSoftwareService {

    FromSoftwareCharacter getCharacterById(int id);

    List<? extends FromSoftwareCharacter> getAllCharacters();

    List<String> getAllNames();
}
