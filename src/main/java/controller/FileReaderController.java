package controller;

import domain.FromSoftwareGames;
import domain.character.FromSoftwareCharacter;
import domain.filereader.EldenRingSaveFileReader;
import domain.filereader.SekiroSaveFileReader;
import service.EldenRingService;
import service.FromSoftwareService;
import service.SekiroService;

import java.nio.file.Path;

public class FileReaderController {

    FromSoftwareService service;

    public FileReaderController(FromSoftwareGames game, Path path) {
        switch (game) {
            case ELDEN_RING -> service = new EldenRingService(new EldenRingSaveFileReader(path));
            case SEKIRO -> service = new SekiroService(new SekiroSaveFileReader(path));
        }

    }

    public FromSoftwareCharacter get(int slotIndex) {
        return service.getCharacterById(slotIndex);
    }
}
