package gui;

import business.entity.EldenRingFile;
import business.entity.FromSoftwareFile;
import business.entity.FromSoftwareGames;
import business.entity.SekiroFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;

//Source : https://www.guru99.com/fr/java-swing-gui.html?utm_campaign=click&utm_medium=referral&utm_source=relatedarticles
public class GraphicUserInterface {
    private static Logger LOGGER = LogManager.getLogger(GraphicUserInterface.class);

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 600;

    private static final String DEFAULT_SAVE_PARENT_FOLDER = "APPDATA";
    private static final String APPLICATION_TITLE = "FromSoftware Deaths";

    private static FromSoftwareGames chosenGame;
    private static Path chosenGamePath;
    private static JLabel chosenGameLabel = new JLabel("Pas de fichier chargé");
    private static JFrame errorFrame = new JFrame("Erreur");


    public static void openGui() {
        initializeErrorPopup();

        //Creating the frame
        JFrame frame = new JFrame(APPLICATION_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        //Panel to select the game
        JPanel gameSelectionPanel = new JPanel(new GridLayout(0, 1));
        Border gameSelectionBorder = BorderFactory.createTitledBorder("Jeux");
        gameSelectionPanel.setBorder(gameSelectionBorder);

        //http://www.java2s.com/Code/Java/Swing-JFC/ASelectedButton.htm
        final ButtonGroup gameSelectionButtons = new ButtonGroup();
        for (FromSoftwareGames game : FromSoftwareGames.values()) {
            AbstractButton jButton = new JRadioButton(game.getFullName());
            jButton.addActionListener(actionListener -> chosenGame = game);
            gameSelectionPanel.add(jButton);
            gameSelectionButtons.add(jButton);
        }

        // Panel displaying character information
        JPanel informationPanel = new JPanel();
        Border informationBorder = BorderFactory.createTitledBorder("Informations sur la sauvegarde");
        final JLabel gameInformationLabel = new JLabel();
        gameInformationLabel.setVisible(true);
        informationPanel.setLayout(new BorderLayout());
        informationPanel.setBorder(informationBorder);
        informationPanel.add(gameInformationLabel, BorderLayout.NORTH);

        //Bottom panel to upload file
        JPanel uploadFilePanel = new JPanel();
        JButton uploadButton = new JButton("Choisir un fichier de sauvegarde");
        importFileListener(uploadButton, gameInformationLabel);
        uploadFilePanel.setLayout(new BorderLayout());
        uploadFilePanel.add(uploadButton, BorderLayout.WEST);
        uploadFilePanel.add(chosenGameLabel, BorderLayout.EAST);


        //Frame creation
        Container container = frame.getContentPane();
        container.add(gameSelectionPanel, BorderLayout.NORTH);
        container.add(informationPanel, BorderLayout.CENTER);
        container.add(uploadFilePanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static void importFileListener(JButton uploadButton, JLabel gameInformationLabel) {
        uploadButton.addActionListener(actionListener -> {
            JFileChooser fileChooser = new JFileChooser(System.getenv(DEFAULT_SAVE_PARENT_FOLDER));
            int jFileChooserOpenedState = fileChooser.showOpenDialog(null);
            if (jFileChooserOpenedState == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                chosenGamePath = Path.of(selectedFile.getAbsolutePath());
                try {
                    updateGameInfo(gameInformationLabel);
                } catch (NumberFormatException e) {
                    String errorMessage = "Erreur lors de la lecture du fichier, vérifiez que le jeu sélectionné correspond bien au fichier choisi";
                    displayError(errorMessage);
                    LOGGER.error(errorMessage, e);
                }
                chosenGameLabel.setText("Fichier chargé : " + chosenGamePath.getFileName().toString());
                chosenGameLabel.setVisible(true);
            }
        });
    }

    private static void updateGameInfo(JLabel gameInformationLabel) {
        FromSoftwareFile saveFile = null;
        switch (chosenGame) {
            case ELDEN_RING -> saveFile = new EldenRingFile(chosenGamePath);
            case SEKIRO -> saveFile = new SekiroFile(chosenGamePath);
        }
        if (null != saveFile) {
            gameInformationLabel.setText(saveFile.stringifyFirstSaveSlotInfo());
        } else {
            gameInformationLabel.setText("Solution pas encore développée pour : " + chosenGame.getFullName());
        }
    }

    private static void initializeErrorPopup() {
        errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        errorFrame.setSize(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 2);
        errorFrame.setVisible(false);
    }

    private static void displayError(String errorMessage) {
        JTextPane jTextPane = new JTextPane();
        jTextPane.setEditable(false);
        StyledDocument sDoc = (StyledDocument) jTextPane.getDocument();
        try {
            sDoc.insertString(0, errorMessage, jTextPane.getStyle("default"));
        } catch (BadLocationException e) {
            LOGGER.error("Invalid position given");
        }
        errorFrame.add(jTextPane);
        errorFrame.setVisible(true);
    }
}
