package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class FileWriterWrapper {

    public static void writeIntInFile(Path path, int value) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(path.toString()))) {
            out.write(String.valueOf(value));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
