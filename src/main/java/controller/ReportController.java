package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    @FXML
    private TextField addressField;
    @FXML
    private Label addressLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<?> countryComboBox;
    @FXML
    private Label countryLabel;
    @FXML
    private TextField customerIdField;
    @FXML
    private Label customerIdLabel;
    @FXML
    private ComboBox<?> divisionComboBox;
    @FXML
    private TextField nameField;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField phoneField;
    @FXML
    private Label phoneLabel;
    @FXML
    private TextField postalCodeField;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Label stateLabel;

    @FXML
    void onCancelButton(ActionEvent event) {

    }

    @FXML
    void onCountryComboBox(ActionEvent event) {

    }

    @FXML
    void onSaveButton(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
