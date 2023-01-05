package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField appointmentIdField;
    @FXML
    private Label appointmentIdLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<?> contactComboBox;
    @FXML
    private Label contactLabel;
    @FXML
    private TextField customerIdField;
    @FXML
    private Label customerIdLabel;
    @FXML
    private TextField descriptionField;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label endDateLabel;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Label endTimeLabel;
    @FXML
    private Spinner<?> endTimeSpinner;
    @FXML
    private TextField locationField;
    @FXML
    private Label locationLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Label startDateLabel;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private Label startTimeLabel;
    @FXML
    private Spinner<?> startTimeSpinner;
    @FXML
    private TextField titleField;
    @FXML
    private Label titleLabel;
    @FXML
    private TextField typeField;
    @FXML
    private Label typeLabel;
    @FXML
    private TextField userIdField;
    @FXML
    private Label userIdLabel;

    @FXML
    void onCancelButton(ActionEvent event) {

    }

    @FXML
    void onEndSpinnerClick(MouseEvent event) {

    }

    @FXML
    void onSaveButton(ActionEvent event) {

    }

    @FXML
    void onStartSpinnerClick(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}