package gui.dialog;

public class WarningDialog extends Dialog {

    public static final String ERROR_MODAL_LABEL = "Warning";

    public WarningDialog(String errorMessage) {
        super(errorMessage);
        setTitle(ERROR_MODAL_LABEL);
        setVisible(true);
    }

}
