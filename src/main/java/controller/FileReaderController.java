package controller;

import domain.FromSoftwareGames;
import domain.character.FromSoftwareCharacter;
import domain.filereader.EldenRingSaveFileReader;
import domain.filereader.SekiroSaveFileReader;
import service.EldenRingService;
import service.FromSoftwareService;
import service.SekiroService;

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
