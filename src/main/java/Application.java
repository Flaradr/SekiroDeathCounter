import domain.file.FileInformation;
import gui.DeathCounterGUI;

public class Application {

    public static void main(String[] args) {

        FileInformation myFileInformation = new FileInformation();
        DeathCounterGUI gui = new DeathCounterGUI(myFileInformation);
    }

}
