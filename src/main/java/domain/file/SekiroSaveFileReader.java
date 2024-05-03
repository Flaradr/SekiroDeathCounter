package domain.file;

import domain.character.SekiroCharacter;

import java.nio.file.Path;
import java.util.Optional;

import static util.HexadecimalConverter.bytesToHexadecimal;
import static util.HexadecimalConverter.hexadecimalToIntLittleEndian;
import static util.InputStreamReader.readNBytesFromStreamWithOffset;

public class SekiroSaveFileReader extends SaveFileReader {

    private final static int OFFSET_DEATH_COUNTER = 0x34260; // Offset to access death counter of first save
    private static final int CHAR_DEATH_BYTES_SIZE = 0x2;
    private static final int SLOT_LENGTH = 0x280000;


    public SekiroSaveFileReader(Path saveFilePath) {
        super(saveFilePath);
    }

    public Optional<SekiroCharacter> findById(int saveSlotIndex) {
        if (saveSlotIndex < 0 || saveSlotIndex > 9) {
            throw new IndexOutOfBoundsException("Index out of bound, value should be between 0 and 9.");
        }

        SekiroCharacter character = new SekiroCharacter();

        character.setName("Sekiro");
        character.setDeathCount(hexadecimalToIntLittleEndian(bytesToHexadecimal(readNBytesFromStreamWithOffset(saveFilePath.toString(), OFFSET_DEATH_COUNTER + saveSlotIndex * SLOT_LENGTH, CHAR_DEATH_BYTES_SIZE))));

        return Optional.of(character);
    }
}
