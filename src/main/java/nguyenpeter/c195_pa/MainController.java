package nguyenpeter.c195_pa;

import database.DBAppointments;
import database.DBCustomers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;
import util.TimeZones;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ResourceBundle;
import java.util.logging.Filter;

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
    private Rectangle appointmentAlertWindow;
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
    private Button confirmAlertButton;
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
    private Label alertWindowText;
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
    public static Appointments selectedAppointment = null;
    public static Customers selectedCustomer = null;
    public static ZonedDateTime selectedDate = null;
    public boolean viewAppointments = true;
    public boolean monthSort = true;
    public boolean viewAll = true;


    @FXML
    void onAddButton(ActionEvent event) throws IOException{
        selectedCustomer = null;
        selectedAppointment = null;
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
        viewAll = true;
        tableViewSetup();
    }

    @FXML
    void onAppointmentTableViewClicked(MouseEvent event) {
        setSelectedAppointment();
    }

    public void setSelectedAppointment() {
        selectedCustomer = null;
        selectedAppointment = (Appointments) appointmentTableView.getSelectionModel().getSelectedItem();
        if(selectedDate != null) {
            selectedDate = selectedAppointment.getStart();
        }
    }

    public void setSelectedCustomer() {
        selectedAppointment = null;
        selectedDate = null;
        selectedCustomer = (Customers) customerTableView.getSelectionModel().getSelectedItem();
    }

    @FXML
    void onCustomerTableViewClicked(MouseEvent event) {
        setSelectedCustomer();
    }

    public static boolean confirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        return alert.getResult() == ButtonType.YES;
    }

    @FXML
    void onDeleteButton(ActionEvent event) throws SQLException {
        if (selectedAppointment != null || selectedCustomer != null) {
            if (viewAppointments && selectedAppointment != null) {
                if(confirm("Are you sure you'd like to delete Appointment ID: " + selectedAppointment.getAppointmentId() + "?")) {
                    DBAppointments.removeAppointment(selectedAppointment.getAppointmentId());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Appointment ID: " + selectedAppointment.getAppointmentId() + "\n" +
                            selectedAppointment.getType() + " has been deleted.");
                    alert.show();
                }
            } else {
                if(selectedCustomer != null) {
                    if(confirm("Are you sure you'd like to delete " + selectedCustomer.getCustomerName() + "?")) {
                        DBAppointments.removeCustomerAppts(DBAppointments.getCustomerAppts(selectedCustomer.getCustomerId()),
                                selectedCustomer.getCustomerId());
                        DBCustomers.removeCustomer(selectedCustomer.getCustomerId());
                    }
                }
            }
            tableViewSetup();
            selectedCustomer = null;
            selectedAppointment = null;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please choose something to delete");
            alert.show();
        }
    }

    @FXML
    void onConfirmAlertButton(ActionEvent event) {
        hideAppointmentAlert();
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
        viewToggle();
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
    void onUpdateButton(ActionEvent event) throws IOException, SQLException {
        if (toggleAppointmentButton.isSelected()) {
            if (selectedAppointment != null || selectedCustomer != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("AppointmentForm.fxml"));
                loader.load();

                AppointmentController AController = loader.getController();
                AController.sendAppointment(appointmentTableView.getSelectionModel().getSelectedItem());

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } else if (toggleCustomerButton.isSelected()) {
            if (selectedAppointment != null || selectedCustomer != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("CustomerForm.fxml"));
                loader.load();

                CustomerController CController = loader.getController();
                CController.sendCustomer(customerTableView.getSelectionModel().getSelectedItem());

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }


    @FXML
    void onWeekToggle(ActionEvent event) {
        viewToggle();
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
        try {
            appointmentsList = DBAppointments.getAllAppointments();
            customersList = DBCustomers.getAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerTableView.setItems(customersList);
        if (monthSort) {
            appointmentTableView.setItems(monthFilter());
        } else {
            appointmentTableView.setItems(weekFilter());
        }
        if (viewAll) {
            appointmentTableView.setItems(appointmentsList);
        }
    }
    private FilteredList<Appointments> weekFilter() {
        return new FilteredList<>(appointmentsList, p -> p.getStart().get(ChronoField.ALIGNED_WEEK_OF_YEAR) ==
                ZonedDateTime.now(ZoneId.systemDefault()).get(ChronoField.ALIGNED_WEEK_OF_YEAR));
    }

    private FilteredList<Appointments> monthFilter() {
        return new FilteredList<>(appointmentsList, p -> p.getStart().getMonthValue() == ZonedDateTime.now(ZoneId.systemDefault()).getMonthValue());
    }
    public void viewToggle() {
        viewAll = false;
        if(monthSort) {
            monthSort = false;
        } else {
            monthSort = true;
        }
        tableViewSetup();
    }

    private void showAppointmentAlert(boolean imminent, int id, String title, ZonedDateTime start) {
        appointmentAlertWindow.setFill(Paint.valueOf("#828282"));
        if(imminent) {
            String alert = "The appointment below is scheduled to start within 15 minutes: \n\n" +
                    "Appointment ID: " + id + "\n" + "Title: " + title + "\n" +
                    "Start: " + TimeZones.getLocalDate(start) + " @ " + TimeZones.getLocalTime(start);
            alertWindowText.setText(alert);
        } else {
            alertWindowText.setText("There are no appointments scheduled within 15 minutes.");
        }
        alertWindowText.setVisible(true);
        alertWindowText.setDisable(false);
        appointmentAlertWindow.setVisible(true);
        appointmentAlertWindow.setDisable(false);
        confirmAlertButton.setVisible(true);
        confirmAlertButton.setDisable(false);
    }

    private void hideAppointmentAlert() {
        alertWindowText.setVisible(false);
        alertWindowText.setDisable(true);
        appointmentAlertWindow.setVisible(false);
        appointmentAlertWindow.setDisable(true);
        confirmAlertButton.setVisible(false);
        confirmAlertButton.setDisable(true);
    }

    private void confirmAppointments() {
        boolean showAlert = false;
        int fifteenMinutes = 900000;
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        try {
            appointments = DBAppointments.getAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LocalTime now = ZonedDateTime.now(ZoneId.systemDefault()).toLocalTime();
        LocalDate today = ZonedDateTime.now(ZoneId.systemDefault()).toLocalDate();
        for (Appointments a : appointments) {
            if(a.getStart().toLocalDate().equals(today)) {
                LocalTime aTime = a.getStart().toLocalTime();
                if(aTime.compareTo(now) <= fifteenMinutes && aTime.compareTo(now) > 0) {
                    showAppointmentAlert(true, a.getAppointmentId(), a.getTitle(), a.getStart());
                    showAlert = true;
                }
            }
        }
        if(!showAlert) {
            showAppointmentAlert(false, -1, "", ZonedDateTime.now());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeZone.setText(TimeZones.getLocalTime() + " - " + TimeZones.getLocalTimeZone());
        serverTimeZone.setText(TimeZones.getUTCTime() + " - " + TimeZones.getUTCZone());
        appointmentTableView.setVisible(true);
        appointmentTableView.setDisable(false);
        customerTableView.setVisible(false);
        customerTableView.setDisable(true);
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

        confirmAppointments();
    }
}

/**
 * Fix timezone display
 * 15min alert window (DONE) - Need to call the function
 * Reports page
 * Javadocs
 */
