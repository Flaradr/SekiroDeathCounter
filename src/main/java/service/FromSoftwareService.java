package service;

import domain.character.FromSoftwareCharacter;

public interface FromSoftwareService {

    FromSoftwareCharacter getCharacterById(int id);

}
