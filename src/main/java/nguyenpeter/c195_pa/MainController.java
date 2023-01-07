package nguyenpeter.c195_pa;

import database.DBAppointments;
import database.DBCustomers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;
import util.TimeZones;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Peter Nguyen
 */

/**
 * MainController class is referenced to the main user interface of Main.fxml. This is the window that users will interact with
 * after logging in.
 *
 */

public class MainController implements Initializable {
    /**
     * Sets the stage and scene of the GUI page.
     */
    Stage stage;
    Parent scene;

    /**
     * Table columns are labeled representing data from the database.
     * Radio buttons are for users to select for viewing certain data
     * Buttons are dedicated for specific tasks (adding, updating, deleting data, etc..)
     */
    @FXML
    private Button addButton;
    @FXML
    private TableColumn<?, ?> addressCol;
    @FXML
    private TableColumn<?, ?> appointmentIdCol_a;
    @FXML
    private Rectangle appointmentRectangle;
    @FXML
    private TableView<Appointments> appointmentTableView;
    @FXML
    private TableColumn<?, ?> contactCol_a;
    @FXML
    private TableColumn<?, ?> custCreateDateCol;
    @FXML
    private TableColumn<?, ?> custCreatedByCol;
    @FXML
    private TableColumn<?, ?> customerIdCol;
    @FXML
    private TableColumn<?, ?> customerIdCol_a;
    @FXML
    private TableColumn<?, ?> customerNameCol;
    @FXML
    private TableView<Customers> customerTableView;
    @FXML
    private Button deleteButton;
    @FXML
    private TableColumn<?, ?> descriptionCol_a;
    @FXML
    private Button dismissRectangleButton;
    @FXML
    private TableColumn<?, ?> custDivisionIdCol;
    @FXML
    private TableColumn<?, ?> endCol_a;
    @FXML
    private TableColumn<?, ?> custLastUpdateCol;
    @FXML
    private TableColumn<?, ?> custLastUpdatedByCol;
    @FXML
    private TableColumn<?, ?> locationCol_a;
    @FXML
    private Button logOutButton;
    @FXML
    private ToggleGroup apptcustTG;
    @FXML
    private TableColumn<?, ?> phoneCol;
    @FXML
    private TableColumn<?, ?> postalCodeCol;
    @FXML
    private RadioButton radioAllAppt;
    @FXML
    private RadioButton radioApptByMonth;
    @FXML
    private RadioButton radioApptByWeek;
    @FXML
    private ToggleButton toggleAppointmentButton;
    @FXML
    private ToggleButton toggleCustomerButton;
    @FXML
    private Label rectangleLabel;
    @FXML
    private Button reportsButton;
    @FXML
    private Label serverTimeZoneLabel;
    @FXML
    private Label serverTimeZone;
    @FXML
    private TableColumn<?, ?> startCol_a;
    @FXML
    private Label yourTimeZoneLabel;
    @FXML
    private Label timeZone;
    @FXML
    private TableColumn<?, ?> titleCol_a;
    @FXML
    private TableColumn<?, ?> typeCol_a;
    @FXML
    private Button updateButton;
    @FXML
    private TableColumn<?, ?> userIdCol_a;
    @FXML
    private ToggleGroup viewTG;

    public static ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
    public static ObservableList<Customers> customersList = FXCollections.observableArrayList();

    @FXML
    void onAddButton(ActionEvent event) throws IOException{
        if(toggleAppointmentButton.isSelected()) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(MainController.class.getResource("AppointmentForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(MainController.class.getResource("CustomerForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * User selection to view all appointments
     * @param event when View All radio button is selected
     */
    @FXML
    void onViewAllRadio(ActionEvent event) {

    }

    @FXML
    void onAppointmentTableViewClicked(MouseEvent event) {

    }

    @FXML
    void onCustomerTableViewClicked(MouseEvent event) {

    }

    @FXML
    void onDeleteButton(ActionEvent event) {

    }

    @FXML
    void onDismissRectangleButton(ActionEvent event) {

    }

    @FXML
    void onLogOutButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(MainController.class.getResource("Login.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onMonthToggle(ActionEvent event) {

    }

    @FXML
    void onReportsButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(MainController.class.getResource("Report.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Reports");
        stage.show();
    }

    @FXML
    void onUpdateButton(ActionEvent event) throws IOException{
        if(toggleAppointmentButton.isSelected()) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(MainController.class.getResource("AppointmentForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Appointment Form");
            stage.show();
        }
//        else {
//            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
//            scene = FXMLLoader.load(MainController.class.getResource("CustomerForm.fxml"));
//            stage.setScene(new Scene(scene));
//            stage.show();
//        }
        if (toggleCustomerButton.isSelected()) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(MainController.class.getResource("CustomerForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Customer Form");
            stage.show();
        }
    }



    @FXML
    void onWeekToggle(ActionEvent event) {

    }

    @FXML
    void onAppointmentToggle(ActionEvent event) {
        appointmentTableView.setVisible(true);
        appointmentTableView.setDisable(false);
        customerTableView.setVisible(false);
        customerTableView.setDisable(true);

        try {
            appointmentsList = DBAppointments.getAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        appointmentTableView.setItems(appointmentsList);
    }

    @FXML
    void onCustomerToggle(ActionEvent event) {
        appointmentTableView.setVisible(false);
        appointmentTableView.setDisable(true);
        customerTableView.setVisible(true);
        customerTableView.setDisable(false);
    }

    public void tableViewSetup() {
        appointmentTableView.setVisible(true);
        customerTableView.setVisible(false);
        try {
            appointmentsList = DBAppointments.getAllAppointments();
            customersList = DBCustomers.getAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        appointmentTableView.setItems(appointmentsList);
        customerTableView.setItems(customersList);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeZone.setText(TimeZones.getLocalTime() + " - " + TimeZones.getLocalTimeZone());
        serverTimeZone.setText(TimeZones.getUTCTime() + " - " + TimeZones.getUTCZone());

        tableViewSetup();

        appointmentIdCol_a.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol_a.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol_a.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol_a.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol_a.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol_a.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol_a.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdCol_a.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol_a.setCellValueFactory(new PropertyValueFactory<>("userId"));
        contactCol_a.setCellValueFactory(new PropertyValueFactory<>("contactId"));

        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        custCreateDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        custCreatedByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        custLastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        custLastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        custDivisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

    }
}