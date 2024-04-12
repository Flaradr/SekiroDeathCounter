import business.entity.EldenRingFile;
import business.entity.FromSoftwareFile;
import business.entity.FromSoftwareGames;
import business.entity.SekiroFile;

import java.nio.file.Path;
import java.util.Scanner;

public class Application {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        FromSoftwareGames chosenGame = chooseGame();
        Path saveParentPath = inputFilePath();
        FromSoftwareFile saveFile = null;

        switch (chosenGame) {
            case ELDEN_RING -> saveFile = new EldenRingFile(saveParentPath);
            case SEKIRO ->  saveFile = new SekiroFile(saveParentPath);
        }
        saveFile.displayFirstSaveSlotInfo();
    }

    private static FromSoftwareGames chooseGame() {
        int count = 0;
        for (FromSoftwareGames game : FromSoftwareGames.values()) {
            System.out.println("Press " + ++count + " for " + game.name());
        }
        System.out.print("Enter a number : ");
        int gameId = Integer.parseInt(scanner.nextLine());
        FromSoftwareGames gameChosen = FromSoftwareGames.fromId(gameId);
        System.out.println("You've chosen : " + gameChosen.name());
        return gameChosen;
    }

    private static Path inputFilePath() {
        System.out.print("Give the path of the save file : ");
        String savePath = scanner.nextLine();
        return Path.of(savePath);
    }


}
