package gui.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.Serial;

public abstract class Dialog extends JDialog {

    public static final String ESCAPE_KEY_NAME = "ESCAPE";
    public static final String CLOSE_BUTTON_LABEL = "Fermer";
    public static final int MARGIN_SIZE = 30;
    public static final int PANEL_WIDTH = 480;
    public static final int PANEL_HEIGHT = 150;
    private final JPanel centerPanel;
    private final JPanel bottomPanel;

    private final JLabel message;
    private final JButton buttonClose;

    protected Dialog() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setResizable(true);
        setLayout(new BorderLayout());
        setSize(PANEL_WIDTH, PANEL_HEIGHT);

        centerPanel = new JPanel();
        message = new JLabel("");

        centerPanel.add(message);


        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, MARGIN_SIZE, 15));
        buttonClose = new JButton();
        buttonClose.setText(CLOSE_BUTTON_LABEL);
        buttonClose.addActionListener((ActionEvent e) -> dispose());
        bottomPanel.add(buttonClose);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        registerEscapeKey();
        centerDialogOnTheScreen();
    }

    protected Dialog(String message) {
        this();
        this.message.setText(String.format("<html><div WIDTH=%d>%s</div></html>", this.getWidth() - MARGIN_SIZE, message));
    }

    /**
     * Make the [Escape] key to behave like the [Close] button.
     */
    public void registerEscapeKey() {
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
            @Serial
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                buttonClose.doClick();
            }
        };

        this.rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, ESCAPE_KEY_NAME);
        this.rootPane.getActionMap().put(ESCAPE_KEY_NAME, escapeAction);
    }

    private void centerDialogOnTheScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dialogSize = this.getSize();
        int centerPosX = (screenSize.width - dialogSize.width) / 2;
        int centerPosY = (screenSize.height - dialogSize.height) / 2;
        setLocation(centerPosX, centerPosY);
    }

    public static void displayMessage(DialogType dialogType, String message) {
        switch (dialogType) {
            case WARNING -> new WarningDialog(message);
            case ERROR -> new ErrorDialog(message);
            case INFO -> new InfoDialog(message);
        }
    }
}
