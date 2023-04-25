package com.example.cryptomachine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import java.util.Objects;

public class MainSceneController
{
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    private static Encryptor encryptor;
    private static Language language;
    private static FileManager fileManager;
    @FXML
    private ScrollPane scrollpane;
    private TextArea textarea;
    @FXML
    private Button chooseFile, enCrypt, deCrypt, mainScene;

    @FXML
    private Label choose, chooseComment;
    @FXML
    private void switchToMainScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("index.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchToScene2EN(ActionEvent event) throws IOException {
        language = Language.ENGLISH;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EncryptDecryptEN.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToScene2UA(ActionEvent event) throws IOException {
        language = Language.UKRAINIAN;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EncryptDecryptUA.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToScene2RU(ActionEvent event) throws IOException {
        language = Language.RUSSIAN;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EncryptDecryptRU.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void chooseInputFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                //new FileChooser.ExtensionFilter("Text Files", "*.txt", "*.doc", ".docx"));
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        try
        {
            File inputfile = fileChooser.showOpenDialog(stage);
            //String fileName = inputfile.getName();
            fileManager = new FileManager();
            encryptor = new Encryptor(language);
            textarea = new TextArea();
            textarea.prefWidth(400);
            textarea.setWrapText(true);
            scrollpane.setContent(textarea);
            encryptor.setPlainText(fileManager.getInputText(inputfile));
            //System.out.println("==============\n" + encryptor.getPlainText());
            textarea.setText(encryptor.getPlainText());
            this.chooseComment.setTextFill(Color.color(0, 1, 0));
            if (language == Language.ENGLISH) {
                this.chooseComment.setText("Text was taken from input file");
            } else if (language == Language.UKRAINIAN) {
                this.chooseComment.setText("Текст прочитано із наданого файла");
            } else if (language == Language.RUSSIAN) {
                this.chooseComment.setText("Текст прочитан из выбранного файла");
            }
        } catch (NullPointerException e)
        {
            this.chooseComment.setTextFill(Color.color(1, 0, 0));
            if (language == Language.ENGLISH) {
                this.chooseComment.setText("you need to choose a file! Right now!");
            } else if (language == Language.UKRAINIAN) {
                this.chooseComment.setText("ви маєте вибрати файл! Негайно!");
            } else if (language == Language.RUSSIAN) {
                this.chooseComment.setText("нужно выбрать хВайл! Прям щас!");
            }
            e.printStackTrace();
        }
    }

    @FXML
    private void encrypttext() {
        try {
            encryptor.encryptSezar();
            fileManager.createOutputFile(encryptor.getEncryptedText(), "encrypt", language);
            textarea.setText(encryptor.getEncryptedText());
            this.chooseComment.setTextFill(Color.color(0, 1, 0));;
            if (language == Language.ENGLISH) {
                this.chooseComment.setText("New file with encrypted text was created");
            } else if (language == Language.UKRAINIAN) {
                this.chooseComment.setText("Створено новий файл з зашифрованим текстом");
            } else if (language == Language.RUSSIAN) {
                this.chooseComment.setText("Создан новый файл с зашЫфрованным текстом");
            }
        } catch (NullPointerException e)
        {
            this.chooseComment.setTextFill(Color.color(1, 0, 0));
            if (language == Language.ENGLISH) {
                this.chooseComment.setText("First you need to choose a file!");
            } else if (language == Language.UKRAINIAN) {
                this.chooseComment.setText("Спочатку ви маєте вибрати файл!");
            } else if (language == Language.RUSSIAN) {
                this.chooseComment.setText("Необходимо сперва выбрать файл!");
            }
            e.printStackTrace();
        }
    }

    @FXML
    private void decrypttext() {
        try
        {
            if (encryptor.getEncryptedText()==null) {
                encryptor.setEncryptedText(encryptor.getPlainText());
                encryptor.setPlainText("");
            }
                fileManager.createOutputFile(encryptor.decryptWithBrutForce(encryptor.getEncryptedText()), "decrypt", language);
                textarea.setText(encryptor.getPlainText());
            this.chooseComment.setTextFill(Color.color(0, 1, 0));;
            if (language == Language.ENGLISH) {
                this.chooseComment.setText("Look decryption results below");
            } else if (language == Language.UKRAINIAN) {
                this.chooseComment.setText("Знизу результати дешиврування");
            } else if (language == Language.RUSSIAN) {
                this.chooseComment.setText("Результати расшЫфровки внизу");
            }
        }
        catch (NullPointerException e)
        {
            this.chooseComment.setTextFill(Color.color(1, 0, 0));
            if (language == Language.ENGLISH) {
                this.chooseComment.setText("First you need to choose a file!");
            } else if (language == Language.UKRAINIAN) {
                this.chooseComment.setText("Спочатку ви маєте вибрати файл!");
            } else if (language == Language.RUSSIAN) {
                this.chooseComment.setText("Необходимо сперва выбрать файл!");
            }
            e.printStackTrace();
        }
    }


}
