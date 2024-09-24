package deathcounter.domain.filereader;

import deathcounter.domain.character.EldenRingCharacter;
import deathcounter.domain.character.FromSoftwareCharacter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static deathcounter.util.HexadecimalConverter.*;
import static deathcounter.util.InputStreamReader.readNBytesFromStreamWithOffset;
import static deathcounter.util.InputStreamReader.searchNPatternInFile;

public class EldenRingSaveFileReader extends SaveFileReader {
    private final static String DEFAULT_EMPTY_NAME = "\u0000".repeat(34);

    private final static int OFFSET_DEATH_COUNTER = 0x383EC; // Offset to access death counter of first save
    private static final int CHAR_DEATH_BYTES_SIZE = 0x2;

    private static final int SLOT_LENGTH = 0x28000;

    private static final int SAVE_HEADER_START_INDEX = 0x1901D0E;
    private static final int SLOT_BYTES_SIZE = 0x24C;
    private static final int CHARACTER_NAME_LOCATION_IN_SLOT = 0x0;
    private static final int CHARACTER_NAME_BYTES_SIZE = 0x22;
    private static final int CHARACTER_LEVEL_LOCATION_IN_SLOT = 0x22;
    private static final int CHARACTER_LEVEL_BYTES_SIZE = 0x2;
    private static final int TIME_PLAYED_INDEX_IN_SLOT = 0x26;
    private static final int TIME_PLAYED_BYTES_SIZE = 0x4;
    public static final int MINIMUM_NUMBER_OF_SAVE = 0;
    public static final int MAXIMUM_NUMBER_OF_SAVE = 9;
 

    public EldenRingSaveFileReader(Path saveFilePath) {
        super(saveFilePath);
    }

    public Optional<EldenRingCharacter> findById(int saveSlotIndex) {
        if (saveSlotIndex < MINIMUM_NUMBER_OF_SAVE || saveSlotIndex > MAXIMUM_NUMBER_OF_SAVE) {
            throw new IndexOutOfBoundsException("Error while finding character by id : index value should be between 0 and 9.");
        }
        List<Integer> position = searchNPatternInFile(saveFilePath.toString(), 4);
        EldenRingCharacter character = getEldenRingCharacter(saveSlotIndex, position.get(saveSlotIndex) - 4);
        return Optional.of(character);
    }

    public List<? extends FromSoftwareCharacter> findAll() {
        List<EldenRingCharacter> characters = new ArrayList<>();

        List<String> names = getAllCharactersNames();
        List<Integer> positions = getDeathCounterPositionForCharacters(names);

        for (int i = 0; i < names.size(); i++) {
            characters.add(getEldenRingCharacter(i, positions.get(i)));
        }

        return characters;
    }

    private List<Integer> getDeathCounterPositionForCharacters(List<String> names) {
        List<Integer> positions = searchNPatternInFile(saveFilePath.toString(), names.size());
        // hack needed to compute the position of the number of death which is always 4 bytes before FF FF FF FF 00 08
        positions.replaceAll(position -> position - 4);

        positions = positions
                .stream()
                .sorted()
                .collect(Collectors.toList());
        return positions;
    }

    private EldenRingCharacter getEldenRingCharacter(Integer saveSlotIndex, Integer deathCounterPosition) {
        int slotBytesStartIndex = SAVE_HEADER_START_INDEX + (saveSlotIndex * SLOT_BYTES_SIZE);
        EldenRingCharacter character = new EldenRingCharacter();
        character.setName(hexToAscii(bytesToHexadecimal(readNBytesFromStreamWithOffset(saveFilePath.toString(), slotBytesStartIndex + CHARACTER_NAME_LOCATION_IN_SLOT, CHARACTER_NAME_BYTES_SIZE))));
        character.setLevel(hexadecimalToIntLittleEndian(bytesToHexadecimal(readNBytesFromStreamWithOffset(saveFilePath.toString(), slotBytesStartIndex + CHARACTER_LEVEL_LOCATION_IN_SLOT, CHARACTER_LEVEL_BYTES_SIZE))));
        character.setSecondsPlayed(hexadecimalToIntLittleEndian(bytesToHexadecimal(readNBytesFromStreamWithOffset(saveFilePath.toString(), slotBytesStartIndex + TIME_PLAYED_INDEX_IN_SLOT, TIME_PLAYED_BYTES_SIZE))));
        character.setDeathCount(hexadecimalToIntLittleEndian(bytesToHexadecimal(readNBytesFromStreamWithOffset(saveFilePath.toString(), deathCounterPosition, CHAR_DEATH_BYTES_SIZE))));
        return character;
    }


    public List<String> getAllCharactersNames() {
        List<String> names = new ArrayList<>();
        String name;
        int i = MINIMUM_NUMBER_OF_SAVE;
        do {
            int slotBytesStartIndex = SAVE_HEADER_START_INDEX + (i * SLOT_BYTES_SIZE);
            name = hexToAscii(bytesToHexadecimal(readNBytesFromStreamWithOffset(saveFilePath.toString(), slotBytesStartIndex + CHARACTER_NAME_LOCATION_IN_SLOT, CHARACTER_NAME_BYTES_SIZE)));
            names.add(name);
            i++;
        } while (!name.equals(DEFAULT_EMPTY_NAME) && i < MAXIMUM_NUMBER_OF_SAVE);

        names.remove(DEFAULT_EMPTY_NAME);
        return names;
    }

}
