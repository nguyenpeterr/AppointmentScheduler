package nguyenpeter.c195_pa;

import database.DBContacts;
import database.DBCountries;
import database.DBCustomers;
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
import database.DBAppointments;
import model.Appointments;
import model.Contacts;
import model.Countries;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    Stage stage;
    Parent scene;


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

    public static ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
    public static ObservableList<Customers> customersList = FXCollections.observableArrayList();

    private ObservableList<Month> getMonths() {
        return FXCollections.observableArrayList(Month.values());
    }

    @FXML
    void onReportComboBox(ActionEvent event) {

    }

    @FXML
    void onReportComboBoxCountry(ActionEvent event) {

    }

    @FXML
    void onReportComboMonth(ActionEvent event) {

    }

    @FXML
    void onReportComboType(ActionEvent event) {

    }

    @FXML
    void onCloseButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void onRadioSelect(ActionEvent event) throws SQLException {
        if(rButtonContactSched.isSelected()) {
            contactScheduleTableView.setVisible(true);
            customersTableView.setVisible(false);
            try {
                comboLabel.setText("Contact ID");
                reportComboBox.setItems(DBContacts.getAllContacts());
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
            try {
                comboLabel.setText("Type");
                reportComboType.setItems(DBAppointments.getAllAppointments());
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
            try {
                comboLabel.setText("Month");
                reportComboMonth.setItems(getMonths());
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
            try {
                comboLabel.setText("Country");
                reportComboBoxCountry.setItems(DBCountries.getAllCountries());
                reportComboBox.setVisible(false);
                reportComboMonth.setVisible(false);
                reportComboType.setVisible(false);
                reportComboBoxCountry.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void tableViewSetup() {
        try {
            appointmentsList = DBAppointments.getAllAppointments();
            customersList = DBCustomers.getAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        contactScheduleTableView.setItems(appointmentsList);
        if (rButtonContactSched.isSelected()) {
            contactScheduleTableView.setItems(contactFilter());
        } else {
            contactScheduleTableView.setItems(appointmentsList);
        }

    }

    private FilteredList<Appointments> contactFilter() {
        return new FilteredList<>(appointmentsList, p -> p.getContactId() == reportComboBox.getSelectionModel().getSelectedIndex() + 1);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        intTotalCustLabel.setText("--");
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
        tableViewSetup();
    }

}
