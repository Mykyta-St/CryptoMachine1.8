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

import java.nio.file.Path;
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

    private String text;
    private static Language language;
    private static FileManager fileManager;
    @FXML
    private ScrollPane scrollpane;
    private TextArea textarea;
    @FXML
    private Label chooseComment;
    @FXML
    private TextField shift;
    @FXML
    private void switchToMainScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("index.fxml")));
        encryptor.setEncryptedText(null);
        encryptor.setPlainText(null);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchToScene2EN(ActionEvent event) throws IOException {
        language = Language.ENGLISH;
        encryptor = new Encryptor(language);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EncryptDecryptEN.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToScene2UA(ActionEvent event) throws IOException {
        language = Language.UKRAINIAN;
        encryptor = new Encryptor(language);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EncryptDecryptUA.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToScene2RU(ActionEvent event) throws IOException {
        language = Language.RUSSIAN;
        encryptor = new Encryptor(language);
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
            Path inputFile = fileChooser.showOpenDialog(stage).toPath();
            fileManager = new FileManager();
            textarea = new TextArea();
            textarea.prefWidth(400);
            textarea.setWrapText(true);
            scrollpane.setContent(textarea);
            this.text = fileManager.getInputText(inputFile);
            if (text.length() == 0)
            {
                textarea.setText("file contains no text");
                this.text = null;
                throw new NullPointerException();
            } else
            {
                textarea.setText(text);
                this.chooseComment.setTextFill(Color.color(0, 1, 0));
                this.chooseComment.setText(LabelTextController.textWasTakenFromFileText(language));
            }

        } catch (NullPointerException e)
        {
            this.chooseComment.setTextFill(Color.color(1, 0, 0));
            this.chooseComment.setText(LabelTextController.fileNotChoosenText(language));
            e.printStackTrace();
        }
        System.out.println(encryptor.getPlainText());
        System.out.println(encryptor.getEncryptedText());
    }

    @FXML
    private void encrypttext() {
        try {
            encryptor.setPlainText(text);
            encryptor.encryptSezar(checkShift());
            this.text = encryptor.getEncryptedText();
            fileManager.createOutputFile(this.text, "encrypt", language);
            textarea.setText(this.text);
            encryptor.setPlainText(null);
            encryptor.setEncryptedText(null);
            this.chooseComment.setTextFill(Color.color(0, 1, 0));
            this.chooseComment.setText(LabelTextController.newFileCreatedText(language));
        } catch (NullPointerException e)
        {
            this.chooseComment.setTextFill(Color.color(1, 0, 0));
            this.chooseComment.setText(LabelTextController.needToChooseFileFirstText(language));
            e.printStackTrace();
        }
        System.out.println(encryptor.getPlainText());
        System.out.println(encryptor.getEncryptedText());
    }

    @FXML
    private void decrypttext() {
        try
        {
            encryptor.setEncryptedText(this.text);
            this.text = encryptor.decrypt();
            fileManager.createOutputFile(this.text, "decrypt", language);
            this.text = encryptor.getPlainText();
            textarea.setText(this.text);
            encryptor.setEncryptedText(null);
            this.chooseComment.setTextFill(Color.color(0, 1, 0));
            this.chooseComment.setText(LabelTextController.textWasDecryptedText(language));
        }
        catch (NullPointerException e)
        {
            this.chooseComment.setTextFill(Color.color(1, 0, 0));
            this.chooseComment.setText(LabelTextController.needToChooseFileFirstText(language));
            e.printStackTrace();
        }
    }

    private int checkShift()
    {
        if (shift.getText().equals("")) return 25;
        else return Integer.parseInt(shift.getText());
    }

}
