package gui;

import controller.FileReaderController;
import domain.character.FromSoftwareCharacter;
import domain.file.FileInformation;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class ParameterSelectionGUI {

    private JPanel parameterSelectionPanel;
    private JPanel outputFileSelectionPanel;
    private JPanel inputFileSelectionPanel;
    private JButton fileOutputSelectionButton;
    private JButton uploadButton;
    private JButton startButton;
    private JLabel outputFileLabel;
    private JLabel chosenGameLabel;

    private Path chosenGamePath;

    enum ProgramStatus {RUNNING, PAUSED, STOPPED}

    private ProgramStatus deathCounterStatus;

    FileInformation charFileInformation;
    ScheduledFuture future;
    PausableSwingWorker worker;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private static final String DEFAULT_SAVE_PARENT_FOLDER = "APPDATA";

    public ParameterSelectionGUI(FileInformation myFileInformation) {
        deathCounterStatus = ProgramStatus.STOPPED;
        charFileInformation = myFileInformation;
        worker = new PausableSwingWorker();
        outputFileLabel = new JLabel("Pas de fichier de sortie défini");
        chosenGameLabel = new JLabel("Pas de fichier chargé");

        initComponents();
    }

    public void initComponents() {
        parameterSelectionPanel = new JPanel();
        parameterSelectionPanel.setLayout(new GridLayout(3, 0));
        outputFileSelectionPanel = new JPanel();
        outputFileSelectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        outputFileSelectionPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        inputFileSelectionPanel = new JPanel();
        inputFileSelectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        inputFileSelectionPanel.setBorder(BorderFactory.createTitledBorder("Input"));

        Border uploadFile = BorderFactory.createTitledBorder("Paramètres");

        fileOutputSelectionButton = new JButton("Choisir le fichier de sortie");
        fileOutputSelectionButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(System.getenv(DEFAULT_SAVE_PARENT_FOLDER));
            if (fileChooser.showOpenDialog(fileOutputSelectionButton) == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                System.out.println("Save as file : " + fileToSave.getAbsolutePath());
                outputFileLabel.setText(fileToSave.getAbsolutePath());
            } else {
                startButton.setVisible(false);
            }
        });

        outputFileSelectionPanel.add(fileOutputSelectionButton);
        outputFileSelectionPanel.add(outputFileLabel);


        uploadButton = new JButton("Choisir un fichier de sauvegarde");
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(System.getenv(DEFAULT_SAVE_PARENT_FOLDER));
            if (fileChooser.showOpenDialog(uploadButton) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                chosenGamePath = Path.of(selectedFile.getAbsolutePath());
                charFileInformation.setFilePath(chosenGamePath);
                chosenGameLabel.setText("Fichier chargé : " + chosenGamePath.getFileName().toString());
                startButton.setVisible(true);
            } else {
                startButton.setVisible(false);
            }
        });

        inputFileSelectionPanel.add(uploadButton);
        inputFileSelectionPanel.add(chosenGameLabel);


        startButton = new JButton("Démarrage du programme");
        startButton.addActionListener(actionListener -> onPressStartButton());
        startButton.setVisible(false);

        chosenGameLabel.setHorizontalAlignment(JLabel.CENTER);

        parameterSelectionPanel.setBorder(uploadFile);
        parameterSelectionPanel.add(outputFileSelectionPanel);
        parameterSelectionPanel.add(inputFileSelectionPanel);
        parameterSelectionPanel.add(startButton);
    }

    public JPanel getParameterSelectionPanel() {
        return this.parameterSelectionPanel;
    }

    private void updateGameInfo() {
        FileReaderController fileReaderController = new FileReaderController(charFileInformation.getChosenGame(), chosenGamePath);
        try {
            FromSoftwareCharacter fromSoftwareCharacter = fileReaderController.get(0);
            charFileInformation.setStringifiedData("Number of death : " + fromSoftwareCharacter.getDeathCount());
            System.out.println(charFileInformation.getStringifiedData());
        } catch (NullPointerException exception) {
            charFileInformation.setStringifiedData("Solution pas encore développée pour : " + charFileInformation.getChosenGame().getFullName());
        }
    }

    private void onPressStartButton() {
        switch (deathCounterStatus) {
            case STOPPED -> {
                deathCounterStatus = ProgramStatus.RUNNING;
                startButton.setText("Pause du programme !");
                worker.execute();
            }
            case RUNNING -> {
                deathCounterStatus = ProgramStatus.PAUSED;
                startButton.setText("Relance du programme !");
                worker.pause();
            }
            case PAUSED -> {
                deathCounterStatus = ProgramStatus.RUNNING;
                startButton.setText("Pause du programme !");
                worker.resume();
            }
        }
    }

    public void handleDeathCounter() {
        final Runnable updateGameInfoRunnable = new Runnable() {
            public void run() {
                updateGameInfo();
            }
        };
        future = executorService.scheduleAtFixedRate(updateGameInfoRunnable, 0, 2, TimeUnit.SECONDS);
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