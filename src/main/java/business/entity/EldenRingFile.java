package business.entity;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static business.entity.FromSoftwareGames.ELDEN_RING;
import static util.HexadecimalConverter.*;
import static util.InputStreamReader.readNBytesFromStreamWithOffset;

public class EldenRingFile implements FromSoftwareFile {

    private static final String SAVE_FILE_NAME = "ER0000.sl2";
    private static final FromSoftwareGames GAME_NAME = ELDEN_RING;
    private final static int OFFSET_DEATH_COUNTER = 0x3874F; // Offset to access death counter of first save

    public static final int SLOT_START_INDEX = 0x310;
    public static final int SLOT_LENGTH = 0x280000;
    public static final int SAVE_HEADERS_SECTION_START_INDEX = 0x19003B0;
    public static final int SAVE_HEADERS_SECTION_LENGTH = 0x60000;
    public static final int SAVE_HEADER_START_INDEX = 0x1901D0E;
    public static final int SAVE_SLOT_HEADER_LENGTH = 0x24C;
    public static final int CHAR_ACTIVE_STATUS_START_INDEX = 0x1901D04;

    private static final int CHAR_NAME_LENGTH = 0x22;
    private static final int CHAR_LEVEL_LOCATION = 0x22;
    private static final int CHAR_PLAYED_START_INDEX = 0x26;
    private static final int CHAR_DEATH_LENGTH = 0x2;
    private static Path saveFilePath;

    private List<EldenRingCharacter> characters;

    public EldenRingFile(Path parentPath) {
        saveFilePath = Path.of(parentPath.toString(), SAVE_FILE_NAME);
        characters = new ArrayList<>();
    }

    public void displayFirstSaveSlotInfo() {
        displayNthSaveSlotInfo(0);
    }

    public void displayNthSaveSlotInfo(int saveSlotIndex) {
        EldenRingCharacter character = new EldenRingCharacter();
        character.setSlotIndex(0);
        character.setCharacterName(hexToAscii(bytesToHexadecimal(readNBytesFromStreamWithOffset(saveFilePath.toString(), SAVE_HEADER_START_INDEX, CHAR_NAME_LENGTH))));
        character.setCharacterLevel(hexadecimalToIntLittleEndian(bytesToHexadecimal(readNBytesFromStreamWithOffset(saveFilePath.toString(), SAVE_HEADER_START_INDEX + CHAR_LEVEL_LOCATION, 2))));

        byte[] bytes = readNBytesFromStreamWithOffset(saveFilePath.toString(),
                OFFSET_DEATH_COUNTER,
                CHAR_DEATH_LENGTH);
        int numberOfDeath = hexadecimalToIntLittleEndian(bytesToHexadecimal(bytes));
        character.setDeathCount(numberOfDeath);

        System.out.println(character);
        characters.add(new EldenRingCharacter());
    }

    public void getCharactersInfo() {
        for (int index = 0; index < 9; index++) {
            EldenRingCharacter character = new EldenRingCharacter();
            character.setSlotIndex(index);
            character.setCharacterName(hexToAscii(bytesToHexadecimal(readNBytesFromStreamWithOffset(saveFilePath.toString(), SAVE_HEADER_START_INDEX + (index * SAVE_SLOT_HEADER_LENGTH), CHAR_NAME_LENGTH))));
            character.setCharacterLevel(hexadecimalToIntLittleEndian(bytesToHexadecimal(readNBytesFromStreamWithOffset(saveFilePath.toString(), SAVE_HEADER_START_INDEX + (index * SAVE_SLOT_HEADER_LENGTH) + CHAR_LEVEL_LOCATION, 2))));

            byte[] bytes = readNBytesFromStreamWithOffset(saveFilePath.toString(),
                    OFFSET_DEATH_COUNTER,
                    CHAR_DEATH_LENGTH);
            int numberOfDeath = hexadecimalToIntLittleEndian(bytesToHexadecimal(bytes));
            character.setDeathCount(numberOfDeath);

            System.out.println(character);

            characters.add(character);
        }
    }
}