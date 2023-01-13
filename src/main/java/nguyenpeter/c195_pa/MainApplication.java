package nguyenpeter.c195_pa;

import database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.LanguageMain;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 540, 320);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}