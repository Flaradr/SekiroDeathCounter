package deathcounter.gui.dialog;

public class WarningDialog extends Dialog {

    public static final String WARNING_MODAL_LABEL = "Warning";

    public WarningDialog(String errorMessage) {
        super(errorMessage);
        setTitle(WARNING_MODAL_LABEL);
        setVisible(true);
    }

}
