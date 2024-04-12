package business.entity;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static business.entity.FromSoftwareGames.SEKIRO;
import static util.HexadecimalConverter.bytesToHexadecimal;
import static util.HexadecimalConverter.hexadecimalToIntLittleEndian;
import static util.InputStreamReader.readNBytesFromStreamWithOffset;

public class SekiroFile implements FromSoftwareFile {
    private static final String SAVE_FILE_NAME = "S0000.sl2";
    private static final FromSoftwareGames GAME_NAME = SEKIRO;
    private final static int OFFSET_DEATH_COUNTER = 0x34260; // Offset to access death counter of first save
    private static final int CHAR_DEATH_LENGTH = 0x2;
    private static Path saveFilePath;

    private List<FromSoftwareCharacter> characters;

    public SekiroFile(Path parentPath) {
        saveFilePath = Path.of(parentPath.toString(), SAVE_FILE_NAME);
        characters = new ArrayList<>();
    }

    public void displayFirstSaveSlotInfo() {
        SekiroCharacter character = new SekiroCharacter();
        byte[] bytes = readNBytesFromStreamWithOffset(saveFilePath.toString(),
                OFFSET_DEATH_COUNTER,
                CHAR_DEATH_LENGTH);
        int numberOfDeath = hexadecimalToIntLittleEndian(bytesToHexadecimal(bytes));
        character.setCharacterName("Sekiro");
        character.setDeathCount(numberOfDeath);
        characters.add(character);
        System.out.println(character);
    }

    public void displayNthSaveSlotInfo(int saveSlotIndex) {
        //TODO : Find the slot size of sekiro save
    }
}
