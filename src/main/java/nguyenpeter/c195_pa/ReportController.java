package nguyenpeter.c195_pa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    private TableColumn<?, ?> column1;

    @FXML
    private TableColumn<?, ?> column2;

    @FXML
    private TableColumn<?, ?> column3;

    @FXML
    private TableColumn<?, ?> column4;

    @FXML
    private TableColumn<?, ?> column5;

    @FXML
    private TableColumn<?, ?> column6;

    @FXML
    private TableColumn<?, ?> column7;

    @FXML
    private TableColumn<?, ?> column8;

    @FXML
    private TableColumn<?, ?> column9;

    @FXML
    private RadioButton rButtonByCountry;

    @FXML
    private RadioButton rButtonByMonth;

    @FXML
    private RadioButton rButtonByType;

    @FXML
    private RadioButton rButtonContactSched;

    @FXML
    private ComboBox<?> reportComboBox;

    @FXML
    private ToggleGroup reportTG;

    @FXML
    void onCloseButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(MainController.class.getResource("Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onRadioSelect(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
