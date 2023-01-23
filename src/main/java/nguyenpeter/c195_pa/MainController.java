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
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Appointments;
import model.Customers;
import util.TimeZones;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
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
     * Rectangle is the alert window for showing appointments due within 15mins at login
     *
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
    @FXML
    private Group alertGroup;

    /**
     * appointmentsList shows the list of appointments from the appointments database
     * customersList shows the list of customers from Customers database
     * selectedAppointment variable stores the appointment the user selects on the tableview
     * selectedCustomer variable stores the customer the user selects on the customer tableview
     * viewAppointment set to true
     * monthSort sets the filter to view appointments by month
     * viewAll sets the filter to view all appointments
     * firstLoad set to true for appointment alert window to show at first load
     * Stage and scenes for AppointmentForm.fxml, CustomerForm.fxml, and Report.fxml
     */
    public static ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
    public static ObservableList<Customers> customersList = FXCollections.observableArrayList();
    public static Appointments selectedAppointment = null;
    public static Customers selectedCustomer = null;
    public static ZonedDateTime selectedDate = null;
    public boolean viewAppointments = true;
    public boolean monthSort = true;
    public boolean viewAll = true;
    private boolean firstLoad = true;
    Scene appointmentFormScene;
    Stage appointmentFormStage = new Stage();
    Scene reportScene;
    Stage reportStage = new Stage();
    Scene customerFormScene;
    Stage customerFormStage = new Stage();


    /**
     * Adds appointment or customer to database
     * @param event calls on Add button press
     * @throws IOException
     */
    @FXML
    void onAddButton(ActionEvent event) throws IOException{
        selectedCustomer = null;
        selectedAppointment = null;
        if(toggleAppointmentButton.isSelected()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentForm.fxml"));
            Parent root =loader.load();
            appointmentFormScene = new Scene(root);
            appointmentFormStage.setScene(appointmentFormScene);
            appointmentFormStage.setResizable(false);
            appointmentFormStage.showAndWait();
            tableViewSetup();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerForm.fxml"));
            Parent root =loader.load();
            customerFormScene = new Scene(root);
            customerFormStage.setScene(customerFormScene);
            customerFormStage.setResizable(false);
            customerFormStage.showAndWait();
            tableViewSetup();
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

    /**
     * Calls on setSelectedAppointment() method when user clicks on the table view
     * @param event Initializes when user clicks on the tableview window
     */
    @FXML
    void onAppointmentTableViewClicked(MouseEvent event) {
        setSelectedAppointment();
    }

    /**
     * Assigns selectedAppointment variable
     */
    public void setSelectedAppointment() {
        selectedCustomer = null;
        selectedAppointment = (Appointments) appointmentTableView.getSelectionModel().getSelectedItem();
        if(selectedDate != null) {
            selectedDate = selectedAppointment.getStartTimeZoned();
        }
    }

    /**
     * Assigns the selectedCustomer variable
     */
    public void setSelectedCustomer() {
        selectedAppointment = null;
        selectedDate = null;
        selectedCustomer = (Customers) customerTableView.getSelectionModel().getSelectedItem();
    }

    /**
     * Calls on setSelectedCustomer() method when user clicks on the table view
     * @param event Initializes when user clicks on the tableview window
     */
    @FXML
    void onCustomerTableViewClicked(MouseEvent event) {
        setSelectedCustomer();
    }

    /**
     * Alert is shown for the user.
     * @param message Message is taken in as the parameter to ask user to confirm
     * @return Result is returned if the user selects the 'Yes' button
     */
    public static boolean confirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        return alert.getResult() == ButtonType.YES;
    }

    /**
     * Delete button for the main page, to delete appointment or customer. Alert shown if user has not
     * selected an item to delete
     * @param event Initiated on button click
     * @throws SQLException throws an exception if there is a data access error
     */
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

    /**
     * Closes the alert window after user confirms the alert message
     * @param event Method is called on when user clicks on the confirm button
     */
    @FXML
    void onConfirmAlertButton(ActionEvent event) {
        hideAppointmentAlert();
    }

    /**
     * Connected to the log out button on the main form. Will close the main window and return
     * user to the login screen
     * @param event Initiated when user clicks on log out button
     * @throws IOException
     */
    @FXML
    void onLogOutButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(MainController.class.getResource("Login.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Attached to the month view radio button to filter results to current month
     * @param event Initiated on radio button select
     */
    @FXML
    void onMonthToggle(ActionEvent event) {
        viewToggle();
    }

    /**
     * Attached to the Reports button to open the Reports.fxml page
     * @param event Initiated on button click
     * @throws IOException
     */
    @FXML
    void onReportsButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
        Parent root =loader.load();
        reportScene = new Scene(root);
        reportStage.setScene(reportScene);
        reportStage.setResizable(false);
        reportStage.showAndWait();

    }

    /**
     * Opens the appointment or customer page with data from the selected appointment/customer for updating
     * @param event Initiated when user clicks on Update button
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onUpdateButton(ActionEvent event) throws IOException, SQLException {
        if (toggleAppointmentButton.isSelected()) {
            if (selectedAppointment != null || selectedCustomer != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("AppointmentForm.fxml"));
                Parent root =loader.load();
                appointmentFormScene = new Scene(root);

                AppointmentController AController = loader.getController();
                AController.sendAppointment(appointmentTableView.getSelectionModel().getSelectedItem());

                appointmentFormStage.setScene(appointmentFormScene);
                appointmentFormStage.setResizable(false);
                appointmentFormStage.showAndWait();
                tableViewSetup();

            }
        } else if (toggleCustomerButton.isSelected()) {
            if (selectedAppointment != null || selectedCustomer != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("CustomerForm.fxml"));
                Parent root =loader.load();
                customerFormScene = new Scene(root);

                CustomerController CController = loader.getController();
                CController.sendCustomer(customerTableView.getSelectionModel().getSelectedItem());

                customerFormStage.setScene(customerFormScene);
                customerFormStage.setResizable(false);
                customerFormStage.showAndWait();
                tableViewSetup();

            }
        }
    }

    /**
     * Attached to week view radio button. Will filter appointments by current week
     * @param event On radio button select
     */
    @FXML
    void onWeekToggle(ActionEvent event) {
        viewToggle();
    }

    /**
     * Attached to the View Appointments button to show appointments on the table view
     * @param event On button select
     */
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

    /**
     * Attached to the View Customers button to show customers in the table view
     * @param event On View Customers button click
     */
    @FXML
    void onCustomerToggle(ActionEvent event) {
        appointmentTableView.setVisible(false);
        appointmentTableView.setDisable(true);
        customerTableView.setVisible(true);
        customerTableView.setDisable(false);
    }


    /**
     * List of appointments according to the current week of the user's system
     * @return Returns the list of appointments within the current week
     */
    private FilteredList<Appointments> weekFilter() {
        return new FilteredList<>(appointmentsList, p -> p.getStartDateTimeLocal().get(ChronoField.ALIGNED_WEEK_OF_YEAR) ==
                ZonedDateTime.now(ZoneId.systemDefault()).get(ChronoField.ALIGNED_WEEK_OF_YEAR));
    }

    /**
     * List of appointments according the current month of the user's system
     * @return Returns the list of appointments within the current month
     */
//    private FilteredList<Appointments> monthFilter() {
//        return new FilteredList<>(appointmentsList, p -> p.getStart().getMonthValue() == ZonedDateTime.now(ZoneId.systemDefault()).getMonthValue());
//    }

    private FilteredList<Appointments> monthFilter() {
        return new FilteredList<>(appointmentsList, p -> p.getStartDateTimeLocal().getMonthValue() == ZonedDateTime.now(ZoneId.systemDefault()).getMonthValue());
    }


    /**
     * Checks to see what radio button the user selects to filter the view by month
     */
    public void viewToggle() {
        viewAll = false;
        monthSort = !monthSort;
        tableViewSetup();
    }

    /**
     * Used to show the appointment alert when application opens. Alerts the user if there is an
     * appointment scheduled within 15mins
     * @param bool Takes in a boolean value
     * @param id Takes in an appointment id
     * @param title Takes in the appointment title
     * @param start Takes in the ZonedDateTime of the start of the appointment
     */
    private void showAppointmentAlert(boolean bool, int id, String title, ZonedDateTime start) {
        if(bool) {
            String alert = "The appointment below is scheduled to start within 15 minutes: \n\n" +
                    "Appointment ID: " + id + "\n" + "Title: " + title + "\n" +
                    "Start: " + TimeZones.getLocalDate(start) + " @ " + TimeZones.getLocalTime(start);
            alertWindowText.setText(alert);
        } else {
            alertWindowText.setText("There are no appointments scheduled within 15 minutes.");
        }
        alertGroup.setVisible(true);
        alertGroup.setDisable(false);
    }

    /**
     * Used to hide the alert
     */
    private void hideAppointmentAlert() {
        alertGroup.setVisible(false);
        alertGroup.setDisable(true);
    }

    /**
     * Method attached to the appointment alert to alert user if there is an appointment
     * within 15mins.
     */
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
            if(a.getStartDateTimeLocal().equals(today)) {
                LocalTime aTime = a.getStartDateTimeLocal().toLocalTime();
                if(aTime.compareTo(now) <= fifteenMinutes && aTime.compareTo(now) > 0) {
                    showAppointmentAlert(true, a.getAppointmentId(), a.getTitle(), a.getStartTimeZoned());
                    showAlert = true;
                }
            }
        }
        if(!showAlert) {
            showAppointmentAlert(false, -1, "", ZonedDateTime.now());
        }
    }

    /**
     * Used to populate the table view with appointments/customers
     */
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

    /**
     * Sets the timezone based on user system and shows the current database time.
     * Appointment and Customer table views are set
     * Appointment alert window is shown
     * @param url
     * @param resourceBundle
     */
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
 * Adding Appt Start time is ok, adding end time does not stick (but updating sticks)
 * Check for overlap
 * Updating old appointments is 1hr off
 * Javadocs
 */
