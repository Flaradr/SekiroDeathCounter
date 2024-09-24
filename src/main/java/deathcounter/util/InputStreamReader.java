package deathcounter.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static deathcounter.util.HexadecimalConverter.bytesToHexadecimal;

public class InputStreamReader {

    private static Logger logger = LogManager.getLogger(InputStreamReader.class);

    /**
     * Read every byte of a file.
     *
     * @param filePath The absolute path of the file to be read
     */
    public static void readFile(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            if (inputStream.read() != -1) {
                System.out.println((inputStream.read()));
            }
        } catch (IOException ex) {
            logger.error("Error while reading the file {} - {}", filePath, ex);
        }
    }

    /**
     * Return n first bytes of a file.
     *
     * @param filePath    The absolute path of the file to be read
     * @param bytesToRead Number of bytes to read
     */
    public static byte[] readNBytesFromFile(String filePath, int bytesToRead) {
        byte[] bytes = new byte[0];
        try (InputStream inputStream = new FileInputStream(filePath)) {
            bytes = inputStream.readNBytes(bytesToRead);
        } catch (IOException ex) {
            logger.error("Error while reading the file {} - {}", filePath, ex);
        }
        return bytes;
    }

    /**
     * Read n first bytes of a file.
     *
     * @param filePath    The absolute path of the file to be read
     * @param offSet      Number of bytes to be skipped
     * @param bytesToRead Number of bytes to read
     */
    public static byte[] readNBytesFromStreamWithOffset(String filePath, int offSet, int bytesToRead) {
        byte[] bytes = new byte[0];
        try (InputStream inputStream = new FileInputStream(filePath)) {
            long skippedBytes = inputStream.skip(offSet);
            if (skippedBytes == offSet) {
                bytes = inputStream.readNBytes(bytesToRead);
            } else {
                throw new IOException("Number of bytes skipped is inferior to the offset");
            }
        } catch (IOException ex) {
            logger.error("Error while reading the file {} - {}", filePath, ex);
        }
        return bytes;
    }

    /**
     * Return the position of N patterns
     *
     * @param filePath  The absolute path of the file to be read
     * @param nbPattern Number of pattern to find
     */
    public static List<Integer> searchNPatternInFile(String filePath, int nbPattern) {
        List<Integer> positions = new ArrayList<>();
        List<CompletableFuture<Integer>> futureList = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            int i = 0;
            while (inputStream.available() > 0 && i < nbPattern) {
                byte[] buffer = inputStream.readNBytes(2600000);
                int offset = 2600000 * i;
                futureList.add(CompletableFuture
                        .supplyAsync(() -> compute(buffer))
                        .thenApply(value -> value + offset));
                i++;
            }
            futureList.forEach(res -> positions.add(res.join()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return positions;
    }

    /**
     * Hack to find the position of death counter in a save slot of Elden Ring save file
     * TODO : Use a pattern to be reusable for each game.
     *
     * @param bytes The bytes to read
     * @return
     */
    public static int compute(byte[] bytes) {
        int position = 0;
        int patternFound = 0;
        InputStream inputStream = new ByteArrayInputStream(bytes);
        try {
            while (inputStream.available() > 0 && patternFound < 1) {
                position++;
                if (bytesToHexadecimal(inputStream.readNBytes(1)).equals("FF")) {
                    position++;
                    if (bytesToHexadecimal(inputStream.readNBytes(1)).equals("FF")) {
                        position++;
                        if (bytesToHexadecimal(inputStream.readNBytes(1)).equals("FF")) {
                            position++;
                            if (bytesToHexadecimal(inputStream.readNBytes(1)).equals("FF")) {
                                position++;
                                if (bytesToHexadecimal(inputStream.readNBytes(1)).equals("00")) {
                                    position++;
                                    if (bytesToHexadecimal(inputStream.readNBytes(1)).equals("08")) {
                                        position = position - 6;
                                        patternFound++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return position;
    }
}


