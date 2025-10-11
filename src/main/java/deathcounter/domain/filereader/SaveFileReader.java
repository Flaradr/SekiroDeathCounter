package deathcounter.domain.filereader;

import java.nio.file.Path;

public abstract class SaveFileReader {

    protected Path saveFilePath;

    public SaveFileReader(Path saveFilePath) {
        this.saveFilePath = saveFilePath;
    }


}
