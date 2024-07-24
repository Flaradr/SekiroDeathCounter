package domain.file;

import domain.FromSoftwareGames;

import java.nio.file.Path;

public class FileInformation {
    private Path filePath;
    private FromSoftwareGames chosenGame;
    private String stringifiedData;


    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public FromSoftwareGames getChosenGame() {
        return chosenGame;
    }

    public void setChosenGame(FromSoftwareGames chosenGame) {
        this.chosenGame = chosenGame;
    }

    public String getStringifiedData() {
        return stringifiedData;
    }

    public void setStringifiedData(String stringifiedData) {
        this.stringifiedData = stringifiedData;
    }
}
