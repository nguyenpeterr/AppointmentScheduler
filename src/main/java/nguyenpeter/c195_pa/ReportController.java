package nguyenpeter.c195_pa;

import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Countries;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * ReportController manages the Report.fxml form. This handles the Reports window for all appointments
 */
public class ReportController implements Initializable {

    /**
     * Tables, labels, combo boxes, radio buttons declared here from the Report.fxml
     */
    @FXML
    private TableColumn<?, ?> apptIdCol;
    @FXML
    private Label comboLabel;
    @FXML
    private TableView<Appointments> contactScheduleTableView;
    @FXML
    private TableColumn<?, ?> createDateCol;
    @FXML
    private TableColumn<?, ?> createdByCol;
    @FXML
    private TableColumn<?, ?> custAddrCol;
    @FXML
    private TableColumn<?, ?> custIdCol;
    @FXML
    private TableColumn<?, ?> custNameCol;
    @FXML
    private TableColumn<?, ?> custPhoneCol;
    @FXML
    private TableColumn<?, ?> custPostalCol;
    @FXML
    private TableColumn<?, ?> custStateCol;
    @FXML
    private TableColumn<?, ?> custViewCustIdCol;
    @FXML
    private TableView<Customers> customersTableView;
    @FXML
    private TableColumn<?, ?> descCol;
    @FXML
    private TableColumn<?, ?> endTimeCol;
    @FXML
    private TableColumn<?, ?> lastUpdateCol;
    @FXML
    private RadioButton rButtonByCountry;
    @FXML
    private RadioButton rButtonByMonth;
    @FXML
    private RadioButton rButtonByType;
    @FXML
    private RadioButton rButtonContactSched;
    @FXML
    private ComboBox<Contacts> reportComboBox;
    @FXML
    private ComboBox<Countries> reportComboBoxCountry;
    @FXML
    private ComboBox<Month> reportComboMonth;
    @FXML
    private ComboBox<Appointments> reportComboType;
    @FXML
    private ToggleGroup reportTG;
    @FXML
    private TableColumn<?, ?> startTimeCol;
    @FXML
    private TableColumn<?, ?> titleCol;
    @FXML
    private TableColumn<?, ?> typeCol;
    @FXML
    private TableColumn<?, ?> updatedByCol;
    @FXML
    private Label totalCustLabel;
    @FXML
    private Label intTotalCustLabel;

    /**
     * Creates a list from Appointments to use for filtering
     */
    public static ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
    /**
     * Creates a list from Customers to use for filtering customers
     */
    public static ObservableList<Customers> customersList = FXCollections.observableArrayList();
    /**
     * Creates a list from Customers to use for filtering customers by appointment type
     */
    public static ObservableList<Customers> typeList = FXCollections.observableArrayList();
    /**
     * Used to assign the selectedContact based on user selection
     */
    public static Contacts selectedContact = null;
    /**
     * Used to assign the selectedType based on user selection
     */
    public static Appointments selectedType = null;
    /**
     * Assigns the selectedMonth from user selection for filtering
     */
    public static Month selectedMonth = null;
    /**
     * Assigns the selectedCountry from user selection for filtering
     */
    public static Countries selectedCountry = null;

    /**
     * Create a list of Months to populate the combo box to allow user to filter by specific month
     */
    private ObservableList<Month> getMonths() {
        return FXCollections.observableArrayList(Month.values());
    }

    /**
     * When the user selects the combo box in the Contact Schedule view, the user is able to select
     * the contact ID to filter the report.
     * Shows the count of customers on the bottom
     * @param event On combo box selection
     */
    @FXML
    void onReportComboBox(ActionEvent event) {
        selectedCountry = null;
        selectedMonth = null;
        selectedType = null;
        intTotalCustLabel.setText("--");
        selectedContact = (Contacts) reportComboBox.getSelectionModel().getSelectedItem();
        if(reportComboBox.getValue() == null) {
            tableViewSetup();
            intTotalCustLabel.setText(Integer.toString(appointmentsList.size()));
        } else {
            contactScheduleTableView.setItems(contactFilter());
            intTotalCustLabel.setText(Integer.toString(contactFilter().size()));
        }

    }

    /**
     * When the user selects the combo box in the 'View by Country' view, the user is able to select
     * the country to filter the report
     * Shows the count of customers on the bottom
     * @param event On combo box selection
     * @throws SQLException
     */
    @FXML
    void onReportComboBoxCountry(ActionEvent event) throws SQLException {
        intTotalCustLabel.setText("--");
        selectedContact = null;
        selectedMonth = null;
        selectedType = null;
        tableViewSetup();
        selectedCountry= (Countries) reportComboBoxCountry.getSelectionModel().getSelectedItem();
        if(reportComboBoxCountry.getValue() == null) {
            tableViewSetup();
        } else {
            typeList = DBCustomers.getCustomerByCountry(String.valueOf(selectedCountry));
            customersTableView.setItems(typeList);
            intTotalCustLabel.setText(Integer.toString(typeList.size()));
        }
    }

    /**
     * When the user selects the combo box in the 'View by Month' view, the user is able to select
     * the Month to filter the report
     * Shows the count of customers on the bottom
     * @param event On combo box selection
     * @throws SQLException
     */
    @FXML
    void onReportComboMonth(ActionEvent event) throws SQLException {
        intTotalCustLabel.setText("--");
        selectedCountry = null;
        selectedContact = null;
        selectedType = null;
        tableViewSetup();
        selectedMonth = (Month) reportComboMonth.getSelectionModel().getSelectedItem();
        if(reportComboMonth.getValue() == null) {
            tableViewSetup();
        } else {
            typeList = DBCustomers.getCustomerByMonth(String.valueOf(selectedMonth));
            customersTableView.setItems(typeList);
            intTotalCustLabel.setText(Integer.toString(typeList.size()));
        }
    }

    /**
     * When the user selects the combo box in the 'View by Type' view, the user is able to select
     * the type to filter the report
     * Shows the count of customers on the bottom
     * @param event On combo box selection
     * @throws SQLException
     */
    @FXML
    void onReportComboType(ActionEvent event) throws SQLException {
        intTotalCustLabel.setText("--");
        selectedCountry = null;
        selectedMonth = null;
        selectedContact = null;
        tableViewSetup();
        selectedType = (Appointments) reportComboType.getSelectionModel().getSelectedItem();
        if(reportComboType.getValue() == null) {
            tableViewSetup();
        } else {
            typeList = DBCustomers.getCustomerByType(String.valueOf(selectedType));
            customersTableView.setItems(typeList);
            intTotalCustLabel.setText(Integer.toString(typeList.size()));
        }
    }


    /**
     * Closes the window
     * @param event On Close button click
     * @throws IOException
     */
    @FXML
    void onCloseButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Sets the table view and combo box label based on the selected radio button
     * @param event On radio button selection
     * @throws SQLException
     */
    @FXML
    void onRadioSelect(ActionEvent event) throws SQLException {
        if(rButtonContactSched.isSelected()) {
            contactScheduleTableView.setVisible(true);
            customersTableView.setVisible(false);
            tableViewSetup();
            try {
                comboLabel.setText("Contact ID");
                reportComboBox.setItems(DBContacts.getAllContacts());
                reportComboBox.getItems().add(0, null);
                reportComboBox.setValue(null);
                reportComboBox.setVisible(true);
                reportComboMonth.setVisible(false);
                reportComboType.setVisible(false);
                reportComboBoxCountry.setVisible(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (rButtonByType.isSelected()){
            contactScheduleTableView.setVisible(false);
            customersTableView.setVisible(true);
            tableViewSetup();
            try {
                comboLabel.setText("Type");
                reportComboType.setItems(DBAppointments.getAllAppointments());
                reportComboType.getItems().add(0, null);
                reportComboType.setValue(null);
                reportComboBox.setVisible(false);
                reportComboMonth.setVisible(false);
                reportComboType.setVisible(true);
                reportComboBoxCountry.setVisible(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (rButtonByMonth.isSelected()) {
            contactScheduleTableView.setVisible(false);
            customersTableView.setVisible(true);
            tableViewSetup();
            try {
                comboLabel.setText("Month");
                reportComboMonth.setItems(getMonths());
                reportComboMonth.getItems().add(0, null);
                reportComboMonth.setValue(null);
                reportComboBox.setVisible(false);
                reportComboMonth.setVisible(true);
                reportComboType.setVisible(false);
                reportComboBoxCountry.setVisible(false);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else if (rButtonByCountry.isSelected()) {
            contactScheduleTableView.setVisible(false);
            customersTableView.setVisible(true);
            tableViewSetup();
            try {
                comboLabel.setText("Country");
                reportComboBoxCountry.setItems(DBCountries.getAllCountries());
                reportComboBoxCountry.getItems().add(0, null);
                reportComboBoxCountry.setValue(null);
                reportComboBox.setVisible(false);
                reportComboMonth.setVisible(false);
                reportComboType.setVisible(false);
                reportComboBoxCountry.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Populates the table view
     */
    public void tableViewSetup() {
        try {
            appointmentsList = DBAppointments.getAllAppointments();
            customersList = DBCustomers.getAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        contactScheduleTableView.setItems(appointmentsList);
        customersTableView.setItems(customersList);
    }

    /**
     * Lambda to filter the table view based on the selected contact id from the combo box
     * Lambda is used here for simplifying the code for easier readability. Allows improvement of iterating
     * through the appointmentsList to filter by Contact ID
     * @return Returns a new filtered list
     */
    private FilteredList<Appointments> contactFilter() {
        return new FilteredList<>(appointmentsList, p -> p.getContactId() == reportComboBox.getSelectionModel().getSelectedIndex());
    }


    /**
     * When the Reports window is initialized, combo box label is set to blank.
     * Table view is populated with all appointments
     * @param url URL
     * @param resourceBundle ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        intTotalCustLabel.setText("--");
        comboLabel.setText("");
        contactScheduleTableView.setVisible(true);
        customersTableView.setVisible(false);
        tableViewSetup();

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        custViewCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custAddrCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        custPostalCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        updatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        custStateCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

    }

}
