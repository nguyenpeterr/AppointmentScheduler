package nguyenpeter.c195_pa;

import database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.LanguageMain;

import java.io.IOException;
import java.util.Locale;

/**
 * MainApplication is the main class that loads the application
 */
public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage.setScene(new Scene(root, 540, 320));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Opens the JDBC connection to the MySQL database
     * Launches the application
     * Closes the connection when exited
     * @param args
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}