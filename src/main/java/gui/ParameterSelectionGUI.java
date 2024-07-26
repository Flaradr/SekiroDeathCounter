package gui;

import controller.FileReaderController;
import domain.character.FromSoftwareCharacter;
import domain.file.FileInformation;
import util.FileWriterWrapper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class ParameterSelectionGUI {

    private final static int PERIOD_BETWEEN_READING_IN_SECONDS = 5;
    private final static String INPUT_FILE_NOT_LOADED = "Pas de fichier chargé";
    private final static String OUTPUT_FILE_NOT_DEFINED = "Pas de fichier de sortie défini";
    private static final String DEFAULT_SAVE_PARENT_FOLDER = "APPDATA";
    public static final String OUTPUT_FILE_BORDER_TITLE = "Informations sur le fichier en sortie";
    public static final String INPUT_FILE_BORDER_TITLE = "Fichier de sauvegarde";
    public static final String PARAMETERS = "Paramètres";
    public static final String CHOOSE_OUTPUT_FILE = "Choisir le fichier de sortie";
    public static final String CHOOSE_SAVE_FILE = "Choisir un fichier de sauvegarde";
    public static final String LOADED_FILE_COLON = "Fichier chargé : ";
    public static final String START_PROGRAM = "Démarrage du programme";
    public static final String PAUSE_PROGRAM = "Pause du programme";
    public static final String RESUME_PROGRAM = "Relance du programme";

    private JPanel parameterSelectionPanel;


    private JPanel outputFileSelectionPanel;
    private JButton fileOutputSelectionButton;
    private JLabel outputFileLabel;


    private JPanel inputFileSelectionPanel;
    private JButton uploadButton;
    private JLabel chosenGameLabel;


    private JPanel startPanel;
    private JButton startButton;
    private JSpinner spinner;
    private JButton resetDeathCounterToZeroButton;
    private int numberOfDeath;


    private Path chosenGamePath;
    private Path deathCounterFilePath;

    enum ProgramStatus {RUNNING, PAUSED, STOPPED}

    private ProgramStatus deathCounterStatus;

    FileInformation charFileInformation;
    ScheduledFuture future;
    PausableSwingWorker worker;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();


    public ParameterSelectionGUI(FileInformation myFileInformation) {
        deathCounterStatus = ProgramStatus.STOPPED;
        charFileInformation = myFileInformation;
        worker = new PausableSwingWorker();
        outputFileLabel = new JLabel(OUTPUT_FILE_NOT_DEFINED);
        chosenGameLabel = new JLabel(INPUT_FILE_NOT_LOADED);
        numberOfDeath = 0;
        spinner = new JSpinner();
        spinner.setPreferredSize(new Dimension(100, 20));
        initComponents();
    }

    public void initComponents() {
        parameterSelectionPanel = new JPanel();
        parameterSelectionPanel.setLayout(new GridLayout(3, 0));

        outputFileSelectionPanel = new JPanel();
        outputFileSelectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        outputFileSelectionPanel.setBorder(BorderFactory.createTitledBorder(OUTPUT_FILE_BORDER_TITLE));

        inputFileSelectionPanel = new JPanel();
        inputFileSelectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        inputFileSelectionPanel.setBorder(BorderFactory.createTitledBorder(INPUT_FILE_BORDER_TITLE));

        startPanel = new JPanel();
        startPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        Border uploadFile = BorderFactory.createTitledBorder(PARAMETERS);

        fileOutputSelectionButton = new JButton(CHOOSE_OUTPUT_FILE);
        fileOutputSelectionButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(System.getenv(DEFAULT_SAVE_PARENT_FOLDER));
            if (fileChooser.showOpenDialog(fileOutputSelectionButton) == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                if (isASaveFile(fileToSave.getAbsolutePath())) {
                    displayError("Vous avez sélectionner un fichier de sauvegarde comme fichier où sera sauvegarder le compteur de mort !");
                } else {
                    deathCounterFilePath = Path.of(fileToSave.getAbsolutePath());
                    outputFileLabel.setText(deathCounterFilePath.toString());
                }

            }
        });

        outputFileSelectionPanel.add(fileOutputSelectionButton);
        outputFileSelectionPanel.add(outputFileLabel);


        uploadButton = new JButton(CHOOSE_SAVE_FILE);
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(System.getenv(DEFAULT_SAVE_PARENT_FOLDER));
            if (fileChooser.showOpenDialog(uploadButton) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                chosenGamePath = Path.of(selectedFile.getAbsolutePath());
                charFileInformation.setFilePath(chosenGamePath);
                chosenGameLabel.setText(LOADED_FILE_COLON + chosenGamePath.getFileName().toString());
                startButton.setEnabled(true);
                resetDeathCounterToZeroButton.setEnabled(true);
            } else {
                startButton.setEnabled(false);
                resetDeathCounterToZeroButton.setEnabled(false);
            }
        });

        inputFileSelectionPanel.add(uploadButton);
        inputFileSelectionPanel.add(chosenGameLabel);

        startButton = new JButton(START_PROGRAM);
        startButton.addActionListener(actionListener -> onPressStartButton());
        startButton.setEnabled(false);

        resetDeathCounterToZeroButton = new JButton("Décaler nombre de mort à 0");
        resetDeathCounterToZeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDeathCounterToZero();
            }
        });
        spinner.setToolTipText("Permet d'ajouter ou retirer des morts");
        resetDeathCounterToZeroButton.setEnabled(false);
        startPanel.add(resetDeathCounterToZeroButton);
        startPanel.add(new JLabel("Modifier nombre de mort"));
        startPanel.add(spinner);
        startPanel.add(startButton);

        chosenGameLabel.setHorizontalAlignment(JLabel.CENTER);

        parameterSelectionPanel.setBorder(uploadFile);
        parameterSelectionPanel.add(outputFileSelectionPanel);
        parameterSelectionPanel.add(inputFileSelectionPanel);
        parameterSelectionPanel.add(startPanel);
    }

    public JPanel getParameterSelectionPanel() {
        return this.parameterSelectionPanel;
    }


    private void setDeathCounterToZero() {
        FileReaderController fileReaderController = new FileReaderController(charFileInformation.getChosenGame(), chosenGamePath);
        try {
            FromSoftwareCharacter fromSoftwareCharacter = fileReaderController.get(0);
            numberOfDeath = fromSoftwareCharacter.getDeathCount();
            SpinnerModel model = new SpinnerNumberModel(numberOfDeath, Integer.MIN_VALUE + numberOfDeath, numberOfDeath, 1);
            spinner.setModel(model);
        } catch (NullPointerException exception) {
            charFileInformation.setStringifiedData("Solution pas encore développée pour : " + charFileInformation.getChosenGame().getFullName());
        }
    }

    private void updateGameInfo() {
        FileReaderController fileReaderController = new FileReaderController(charFileInformation.getChosenGame(), chosenGamePath);
        try {
            FromSoftwareCharacter fromSoftwareCharacter = fileReaderController.get(0);
            charFileInformation.setStringifiedData("Nombre de mort : " + fromSoftwareCharacter.getDeathCount());
            numberOfDeath = fromSoftwareCharacter.getDeathCount();
            SpinnerModel model = new SpinnerNumberModel((int) spinner.getValue(), Integer.MIN_VALUE + numberOfDeath, numberOfDeath, 1);
            spinner.setModel(model);
            FileWriterWrapper.writeIntInFile(deathCounterFilePath, fromSoftwareCharacter.getDeathCount());
        } catch (NullPointerException exception) {
            charFileInformation.setStringifiedData("Solution pas encore développée pour : " + charFileInformation.getChosenGame().getFullName());
        }
    }


    public boolean isASaveFile(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                .map(s -> (s.equals("sl2")))
                .orElse(false);
    }

    private void onPressStartButton() {
        if (null == charFileInformation.getChosenGame()) {
            startButton.setEnabled(false);
            chosenGamePath = null;
            chosenGameLabel.setText(INPUT_FILE_NOT_LOADED);
            displayError("Le jeu n'a pas été sélectionné");
            return;
        }

        switch (deathCounterStatus) {
            case STOPPED -> {
                deathCounterStatus = ProgramStatus.RUNNING;
                startButton.setText(PAUSE_PROGRAM);
                worker.execute();
            }
            case RUNNING -> {
                deathCounterStatus = ProgramStatus.PAUSED;
                startButton.setText(RESUME_PROGRAM);
                worker.pause();
            }
            case PAUSED -> {
                deathCounterStatus = ProgramStatus.RUNNING;
                startButton.setText(PAUSE_PROGRAM);
                worker.resume();
            }
        }
    }

    private static void displayError(String errorMessage) {
        ErrorDialog foo = new ErrorDialog(errorMessage);
        foo.setVisible(true);
    }

    public void handleDeathCounter() {
        final Runnable updateGameInfoRunnable = new Runnable() {
            public void run() {
                updateGameInfo();
            }
        };
        future = executorService.scheduleAtFixedRate(updateGameInfoRunnable, 0, PERIOD_BETWEEN_READING_IN_SECONDS, TimeUnit.SECONDS);
    }


    public class PausableSwingWorker<K, V> extends SwingWorker<K, V> {

        private volatile boolean isPaused;

        public final void pause() {
            if (!isPaused()) {
                isPaused = true;
                future.cancel(true);
            }
        }

        public final void resume() {
            if (isPaused()) {
                isPaused = false;
                handleDeathCounter();
            }
        }

        public final boolean isPaused() {
            return isPaused;
        }

        @Override
        protected K doInBackground() {
            handleDeathCounter();
            return null;
        }
    }
}