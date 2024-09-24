package deathcounter.controller;

import deathcounter.domain.FromSoftwareGames;
import deathcounter.domain.character.FromSoftwareCharacter;
import deathcounter.domain.filereader.EldenRingSaveFileReader;
import deathcounter.domain.filereader.SekiroSaveFileReader;
import deathcounter.service.EldenRingService;
import deathcounter.service.FromSoftwareService;
import deathcounter.service.SekiroService;

import java.nio.file.Path;
import java.util.List;

public class FileReaderController {

    FromSoftwareService service;

    public FileReaderController(FromSoftwareGames game, Path path) {
        switch (game) {
            case ELDEN_RING -> service = new EldenRingService(new EldenRingSaveFileReader(path));
            case SEKIRO -> service = new SekiroService(new SekiroSaveFileReader(path));
        }

    }

    public FromSoftwareCharacter getCharacterById(int slotIndex) {
        return service.getCharacterById(slotIndex);
    }

    public List<? extends FromSoftwareCharacter> getAll() {
        return service.getAllCharacters();
    }

    public List<String> getAllCharactersNames() {
        return service.getAllNames();
    }
}
