package nguyenpeter.c195_pa;

import database.DBCountries;
import database.DBCustomers;
import database.DBFirstLevelDivisions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Customers;
import util.TimeZones;
import util.Verify;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Hashtable;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    private TextField addressField;
    @FXML
    private Label addressLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<String> countryComboBox;
    @FXML
    private Label countryLabel;
    @FXML
    private TextField customerIdField;
    @FXML
    private Label customerIdLabel;
    @FXML
    private ComboBox<String> divisionComboBox;
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

    public Hashtable<Integer, String> divisionNameHash;
    public Hashtable<String, Integer> divisionIdHash;

    @FXML
    void onCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(MainController.class.getResource("Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Appointment Management System");
        stage.show();
    }

    @FXML
    void onCountryComboBox(ActionEvent event) {
        if(countryComboBox.getSelectionModel().getSelectedIndex() == 0) {
            divisionComboBox.setItems(DBFirstLevelDivisions.getUSDivision());
        } else if (countryComboBox.getSelectionModel().getSelectedIndex() == 1) {
            divisionComboBox.setItems(DBFirstLevelDivisions.getUKDivision());
        } else if (countryComboBox.getSelectionModel().getSelectedIndex() == 2) {
            divisionComboBox.setItems(DBFirstLevelDivisions.getCADivision());
        }
        divisionComboBox.getSelectionModel().selectFirst();
    }

    public boolean verifyInput() {
        boolean nameInput = Verify.validName(nameField.getText());
        boolean addressInput = Verify.validAddress(addressField.getText());
        boolean postalInput = Verify.validPostal(postalCodeField.getText());
        boolean phoneInput = Verify.validPhone(phoneField.getText());
        boolean[] input = {nameInput, addressInput, postalInput, phoneInput};
        for (boolean b : input) {
            if (!b) {
                return false;
            }
        }
        return true;
    }
    @FXML
    void onSaveButton(ActionEvent event) throws IOException {
        if(verifyInput()) {
            if (MainController.selectedCustomer != null) {
                DBCustomers.updateCustomer(createCustomer());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Update Successful!");
                alert.showAndWait();
            } else {
                addCustomer();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Add Successful!");
                alert.showAndWait();
            }
            MainController.selectedAppointment = null;
            MainController.selectedCustomer = null;
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(MainController.class.getResource("Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Appointment Management System");
            stage.show();
        }
    }

    public Customers createCustomer() {
        int customerId = Integer.parseInt(customerIdField.getText());
        String customerName = nameField.getText();
        String customerAddress = addressField.getText();
        String customerPostalCode = postalCodeField.getText();
        String customerPhoneNumber = phoneField.getText();
        ZonedDateTime createDate = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("UTC"));
        String createdBy = "admin";
        Timestamp lastUpdate = TimeZones.timestampUTC();
        String lastUpdatedBy = "admin";
        int divisionId = divisionIdFromName(divisionComboBox.getSelectionModel().getSelectedItem());
        return new Customers(customerId,customerName,customerAddress, customerPostalCode, customerPhoneNumber,
                createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
    }

    public void addCustomer() {
            DBCustomers.addCustomer(createCustomer());
    }

    public void sendCustomer(Customers customer) throws SQLException {
        customerIdField.setText(String.valueOf(customer.getCustomerId()));
        nameField.setText(String.valueOf(customer.getCustomerName()));
        addressField.setText(String.valueOf(customer.getCustomerAddress()));
        postalCodeField.setText(String.valueOf(customer.getCustomerPostalCode()));
        phoneField.setText(String.valueOf(customer.getCustomerPhoneNumber()));
        setCBDivisionId(customer.getDivisionId());

    }

    public void setCBDivisionId(int divisionId) {
        if (divisionId >= 1 && divisionId <=54) {
            countryComboBox.getSelectionModel().select("U.S");
            divisionComboBox.setItems(DBFirstLevelDivisions.getUSDivision());
            divisionComboBox.getSelectionModel().select(divisionNameFromId(divisionId));
        } else if (divisionId >= 60 && divisionId <= 72) {
            countryComboBox.getSelectionModel().select("Canada");
            divisionComboBox.setItems(DBFirstLevelDivisions.getCADivision());
            divisionComboBox.getSelectionModel().select(divisionNameFromId(divisionId));
        } else if (divisionId >= 101 && divisionId <= 104) {
            countryComboBox.getSelectionModel().select("UK");
            divisionComboBox.setItems(DBFirstLevelDivisions.getUKDivision());
            divisionComboBox.getSelectionModel().select(divisionNameFromId(divisionId));
        }
    }

    public String divisionNameFromId(int divisionId) {
        return divisionNameHash.get(divisionId);
    }

    public int divisionIdFromName(String division) {
        return divisionIdHash.get(division);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        divisionNameHash = DBFirstLevelDivisions.hashAllDivisions();
        divisionIdHash = DBFirstLevelDivisions.hashAllDivisionIds();
        try {
            countryComboBox.setItems(DBCountries.getAllCountryNames());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        countryComboBox.getSelectionModel().selectFirst();
        divisionComboBox.setItems(DBFirstLevelDivisions.getUSDivision());
        divisionComboBox.getSelectionModel().selectFirst();
        customerIdField.setText(String.valueOf(DBCustomers.generateCustomerId()));
    }
}
