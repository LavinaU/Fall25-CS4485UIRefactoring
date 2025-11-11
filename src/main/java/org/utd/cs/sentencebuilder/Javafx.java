/**
 * JavaFX.java
 * CS4485 - Fall 2025 - Sentence Builder Project
 *
 * Author: Kalani Kawaguchi
 * Date: October 6, 2025
 *
 * Description:
 * Simple UI with some placeholders.
 * Upload file button allows users to upload .txt files to be saved to the
 * data folder. Uploaded file will then be imported to the DB
 */
package org.utd.cs.sentencebuilder;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.transform.Source;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

public class Javafx extends Application {

    private static DatabaseManager db; //Kevin Tran: Shared DatabaseManager instance
    public static void setDatabaseManager(DatabaseManager databaseManager) {
        db = databaseManager;
    }

    private static final int MAX_WORDS = 1;
    private static final FileChooser fileChooser = new FileChooser();
    //private static DatabaseManager db = new DatabaseManager();
    private static Scene homeScene;
    private static Scene historyScene;
    private static ObservableMap<String, SourceFile> importedFiles = FXCollections.observableHashMap();

    private Scene mainScene;
    private Scene historyScene;

    private Scene buildMainScene(Stage stage) {

    }

    private Scene buildHistoryScene(Stage stage) {

    }

    @Override
    public void start(Stage stage) {
        mainScene = buildMainScene(stage);
        historyScene = buildHistoryScene(stage);

        stage.setScene(mainScene);
        stage.setTitle("Sentence Builder");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    public static void selectFile(Stage stage){
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        fileChooser.setTitle("Upload a .txt file");
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Path dest = Path.of("data/clean", file.getName());

            try {
                Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File saved");

                //Kevin Tran
                //Uses the shared DatabaseManager instance to import the file
                boolean wordsOnly = false;
                new ImporterCli(db).run(Path.of("data/clean"), wordsOnly);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No file");
        }
    }


}