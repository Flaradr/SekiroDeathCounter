package util;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamReader {

    /**
     * Read every bytes of a file.
     *
     * @param filePath The absolute path of the file to be read
     */
    public static void readFile(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            if (inputStream.read() != -1) {
                System.out.println((inputStream.read()));
            }
        } catch (IOException ex) {
            System.out.println("Error while reading the file {}" + filePath + " - " + ex);
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
            System.out.println("Error while reading the file {}" + filePath + " - " + ex);
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
            System.out.println("Error while reading the file {}" + filePath + " - " + ex);
        }
        return bytes;
    }
}
