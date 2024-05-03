package domain.file;

import domain.character.EldenRingCharacter;

import java.nio.file.Path;
import java.util.Optional;

import static util.HexadecimalConverter.*;
import static util.InputStreamReader.readNBytesFromStreamWithOffset;

public class EldenRingSaveFileReader extends SaveFileReader {

    private final static int OFFSET_DEATH_COUNTER = 0x383EC; // Offset to access death counter of first save
    private static final int CHAR_DEATH_BYTES_SIZE = 0x2;

    private static final int SLOT_LENGTH = 0x280000;

    private static final int SAVE_HEADER_START_INDEX = 0x1901D0E;
    private static final int SLOT_BYTES_SIZE = 0x24C;
    private static final int CHARACTER_NAME_LOCATION_IN_SLOT = 0x0;
    private static final int CHARACTER_NAME_BYTES_SIZE = 0x22;
    private static final int CHARACTER_LEVEL_LOCATION_IN_SLOT = 0x22;
    private static final int CHARACTER_LEVEL_BYTES_SIZE = 0x2;
    private static final int TIME_PLAYED_INDEX_IN_SLOT = 0x26;
    private static final int TIME_PLAYED_BYTES_SIZE = 0x4;


    public EldenRingSaveFileReader(Path saveFilePath) {
        super(saveFilePath);
    }

    public Optional<EldenRingCharacter> findById(int saveSlotIndex) {
        if (saveSlotIndex < 0 || saveSlotIndex > 9) {
            throw new IndexOutOfBoundsException("Index out of bound, value should be between 0 and 9.");
        }

        EldenRingCharacter character = new EldenRingCharacter();

        int slotBytesStartIndex = SAVE_HEADER_START_INDEX + (saveSlotIndex * SLOT_BYTES_SIZE);

        character.setName(hexToAscii(bytesToHexadecimal(readNBytesFromStreamWithOffset(saveFilePath.toString(), slotBytesStartIndex + CHARACTER_NAME_LOCATION_IN_SLOT, CHARACTER_NAME_BYTES_SIZE))));
        character.setLevel(hexadecimalToIntLittleEndian(bytesToHexadecimal(readNBytesFromStreamWithOffset(saveFilePath.toString(), slotBytesStartIndex + CHARACTER_LEVEL_LOCATION_IN_SLOT, CHARACTER_LEVEL_BYTES_SIZE))));
        character.setSecondsPlayed(hexadecimalToIntLittleEndian(bytesToHexadecimal(readNBytesFromStreamWithOffset(saveFilePath.toString(), slotBytesStartIndex + TIME_PLAYED_INDEX_IN_SLOT, TIME_PLAYED_BYTES_SIZE))));
        character.setDeathCount(hexadecimalToIntLittleEndian(bytesToHexadecimal(readNBytesFromStreamWithOffset(saveFilePath.toString(), OFFSET_DEATH_COUNTER + saveSlotIndex * SLOT_LENGTH, CHAR_DEATH_BYTES_SIZE))));

        return Optional.of(character);
    }

}
