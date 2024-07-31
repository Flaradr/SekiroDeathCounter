package gui.dialog;

public class ErrorDialog extends Dialog {

    public static final String ERROR_MODAL_TITLE = "Erreur";

    public ErrorDialog(String errorMessage) {
        super(errorMessage);
        setTitle(ERROR_MODAL_TITLE);
        setVisible(true);
    }
}