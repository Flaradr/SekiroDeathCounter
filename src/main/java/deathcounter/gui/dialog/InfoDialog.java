package deathcounter.gui.dialog;

public class InfoDialog extends Dialog {
    public static final String INFO_MODAL_TITLE = "Info";

    public InfoDialog(String errorMessage) {
        super(errorMessage);
        setTitle(INFO_MODAL_TITLE);
        setVisible(true);
    }
}