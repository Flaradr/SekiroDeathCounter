package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.Serial;

public class ErrorDialog extends JDialog {

    private final JPanel centerPanel;
    private final JPanel bottomPanel;

    private final JLabel errorLabel;
    private final JButton buttonClose;

    public ErrorDialog(String errorMessage) {
        this(errorMessage, null);
    }

    public ErrorDialog(String errorMessage, Throwable exception) {
        setTitle("Erreur");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setResizable(true);
        setLayout(new BorderLayout());

        centerPanel = new JPanel();
        errorLabel = new JLabel(errorMessage);

        centerPanel.add(errorLabel);


        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
        buttonClose = new JButton();
        buttonClose.setText("Fermer");
        buttonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        bottomPanel.add(buttonClose);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setSize(480, 150);

        registerEscapeKey();
        centerDialogOnTheScreen();
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

        this.rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        this.rootPane.getActionMap().put("ESCAPE", escapeAction);
    }

    private void centerDialogOnTheScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dialogSize = this.getSize();
        int centerPosX = (screenSize.width - dialogSize.width) / 2;
        int centerPosY = (screenSize.height - dialogSize.height) / 2;
        setLocation(centerPosX, centerPosY);
    }
}
