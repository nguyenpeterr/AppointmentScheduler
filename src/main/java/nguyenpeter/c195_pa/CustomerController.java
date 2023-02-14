package nguyenpeter.c195_pa;

import database.DBCountries;
import database.DBCustomers;
import database.DBFirstLevelDivisions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

/**
 * CustomerController class controls the CustomerForm.fxml. This controller handles all customers and database data for adding,
 * deleting, or updating.
 */
public class CustomerController implements Initializable {

    /**
     * Labels, text fields, combo boxes and buttons are declared here from the CustomerForm.fxml
     */
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

    /**
     * Hash table to store division names
     */
    public Hashtable<Integer, String> divisionNameHash;

    /**
     * Hash table to store division IDs
     */
    public Hashtable<String, Integer> divisionIdHash;

    /**
     * Does not save any input or updated data. Closes window
     * @param event On Cancel button click
     * @throws IOException
     */
    @FXML
    void onCancelButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainController.selectedCustomer = null;
        stage.close();
    }

    /**
     * Attached to the Country combo box for user to select country of the customer
     * @param event On mouse click
     */
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

    /**
     * Verifies the inputs of the user.
     * Checks for valid name entry
     * Checks for valid address entry
     * Checks for valid postal code entry
     * Checks for valid phone number entry
     *
     * @return Returns true if all are valid, else false
     */
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

    /**
     * Saves or updates a new or existing customer to the application and database
     * @param event On Save button click
     * @throws IOException
     */
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
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

        }
    }

    /**
     * Creates a new customer with given input
     * @return Returns a new customer object
     */
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

    /**
     * Adds a mew customer into the MySQL database
     */
    public void addCustomer() {
            DBCustomers.addCustomer(createCustomer());
    }

    /**
     * Grabs the selected custmer from the main appointment window and populates the text fields with the customer's information
     * @param customer Takes in the selected customer as the parameter
     * @throws SQLException
     */
    public void sendCustomer(Customers customer) throws SQLException {
        customerIdField.setText(String.valueOf(customer.getCustomerId()));
        nameField.setText(String.valueOf(customer.getCustomerName()));
        addressField.setText(String.valueOf(customer.getCustomerAddress()));
        postalCodeField.setText(String.valueOf(customer.getCustomerPostalCode()));
        phoneField.setText(String.valueOf(customer.getCustomerPhoneNumber()));
        setCBDivisionId(customer.getDivisionId());

    }

    /**
     * Lists the country in the division combobox based on the user's division ID
     * @param divisionId Takes the customer's division ID as the parameter
     */
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

    /**
     * Grabs the division name based on the division ID
     * @param divisionId Takes the division ID as the parameter
     * @return Division names from ID
     */
    public String divisionNameFromId(int divisionId) {
        return divisionNameHash.get(divisionId);
    }

    /**
     * Grabs the division id based on the division name
     * @param division Takes the division name as the parameter
     * @return Division IDs from Name
     */
    public int divisionIdFromName(String division) {
        return divisionIdHash.get(division);
    }


    /**
     * When the window is loaded, division name/id are set.
     * If there was a customer that was selected on the main window, the text fields are populated with that info
     * @param url URL
     * @param resourceBundle ResourceBundle
     */
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
