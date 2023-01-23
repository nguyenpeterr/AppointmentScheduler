package nguyenpeter.c195_pa;

import database.DBAppointments;
import database.DBContacts;
import database.DBCustomers;
import database.DBUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;
import util.TimeZones;
import util.Verify;

import javax.xml.validation.Validator;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * AppointmentController class controls the AppointmentForm.fxml. This controller handles all texts, database data for adding,
 * deleting, or updating.
 */
public class AppointmentController implements Initializable {

//    Stage stage;
//    Parent scene;
    /**
     * Labels, text fields, combo boxes and spinners are declared here from the AppointmentForm.fxml
     */
    @FXML
    private TextField appointmentIdField;
    @FXML
    private Label appointmentIdLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<Contacts> contactComboBox;
    @FXML
    private Label contactLabel;
    @FXML
    private TextField customerIdField;
    @FXML
    private ComboBox<Customers> customerIdComboBox;
    @FXML
    private ComboBox<Users> userIdComboBox;
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
    private ComboBox<String> endCombo;
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
    private ComboBox<String> startCombo;
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

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
    private final String delimiter = ": ";

//    /**
//     * List to handle time in hours for the spinner. Minutes are incremented every 30 minutes and hours are incremented
//     * accordingly
//     * @return returns hours
//     */
//    private ObservableList<LocalTime> genHours() {
//        ObservableList<LocalTime> hours = FXCollections.observableArrayList();
//        for(int i=0; i <= 23; i++) {
//            for(int m=0; m <= 1; m++) {
//                hours.add(TimeZones.setLocalTime(i, m*30));
//            }
//        } return hours;
//    }
//    public ObservableList<LocalTime> hours = genHours();
//    public SpinnerValueFactory<LocalTime> startTImeVF = new SpinnerValueFactory.ListSpinnerValueFactory<>(hours);
//    public SpinnerValueFactory<LocalTime> endTimeVF = new SpinnerValueFactory.ListSpinnerValueFactory<>(hours);

    private ObservableList<String> getAppointmentTimes() {
        ObservableList<String> appointmentTimes = FXCollections.observableArrayList();
        LocalTime midnight = LocalTime.of(0,0);
        appointmentTimes.add(midnight.format(timeFormatter));

        int mins = 15;
        LocalTime appointmentTime = midnight.plusMinutes(mins);
        while(!appointmentTime.equals(midnight)) {
            appointmentTimes.add(appointmentTime.format(timeFormatter));
            appointmentTime = appointmentTime.plusMinutes(mins);
        }
        return appointmentTimes;
    }

    /**
     * Attached to the cancel button on the GUI. Window will close and user will be returned to the main appointment window.
     * @param event On Cancel button click
     * @throws IOException
     */
    @FXML
    void onCancelButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainController.selectedAppointment = null;
        stage.close();
    }

    /**
     * Attached to the start combobox
     * @param event On mouse click
     */

    @FXML
    void onStartCombo(MouseEvent event) {
    }

    /**
     * Attached to the end combobox
     * @param event On mouse click
     */
    @FXML
    void onEndCombo(MouseEvent event) {
    }

    /**
     * Attached to the save button to either update an existing appointment or create a new appointment based on
     * whether there is a selected appointment
     * @param event On Save button click
     * @throws IOException
     */
    @FXML
    void onSaveButton(ActionEvent event) throws IOException {
        if(validInput()) {
            if (MainController.selectedAppointment != null) {
                DBAppointments.updateAppointment(createAppointment());
                System.out.println("Update Success");
            } else {
                addAppointment();
                System.out.println("Add Success");
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            MainController.selectedAppointment = null;
            MainController.selectedCustomer = null;
            stage.close();
        }
    }

    /**
     * Method to add appointment into the database
     */
    public void addAppointment() {
        DBAppointments.addAppointment(createAppointment());
    }

    /**
     * Grabs input from the text field and creates a new appointment
     * @return Returns a new appointment
     */
    public Appointments createAppointment() {
        int appointmentId = Integer.parseInt(appointmentIdField.getText());
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        LocalDateTime start = datePickerValue(0).toLocalDateTime();
        LocalDateTime end = datePickerValue(1).toLocalDateTime();
        int customerId = customerIdComboBox.getSelectionModel().getSelectedIndex() + 1;
        int userId = userIdComboBox.getSelectionModel().getSelectedIndex() + 1;
        int contactId = contactComboBox.getSelectionModel().getSelectedIndex() + 1;
        ZonedDateTime createDate = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("UTC"));
        String createdBy = "admin";
        Timestamp lastUpdate = TimeZones.timestampUTC();
        String lastUpdatedBy = "admin";
        return new Appointments(appointmentId, title, description, location, type, start, end,
                createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
    }


    /**
     * Takes the data from the selected appointment and fills the AppointmentForm.fxml accordingly. Allows the user
     * to update the current data.
     * @param appointment Takes the selected appointment as the parameter
     * @throws SQLException
     */
    public void sendAppointment(Appointments appointment) throws SQLException {
        appointmentIdField.setText(String.valueOf(appointment.getAppointmentId()));
        titleField.setText(String.valueOf(appointment.getTitle()));
        descriptionField.setText(String.valueOf(appointment.getDescription()));
        locationField.setText(String.valueOf(appointment.getLocation()));
        typeField.setText(String.valueOf(appointment.getType()));
        LocalDate startDate = MainController.selectedAppointment.getStartDateTimeLocal().toLocalDate();
        LocalTime startTime = MainController.selectedAppointment.getStartDateTimeLocal().toLocalTime();
        startDatePicker.setValue(startDate);
        startCombo.setValue(startTime.format(timeFormatter).toString());
        LocalDate endDate = MainController.selectedAppointment.getEndTimeLocal().toLocalDate();
        LocalTime endTime = MainController.selectedAppointment.getEndTimeLocal().toLocalTime();
        endDatePicker.setValue(endDate);
        endCombo.setValue(endTime.format(timeFormatter).toString());
        contactComboBox.getSelectionModel().selectFirst();
        contactComboBox.getSelectionModel().select(MainController.selectedAppointment.getContactId() - 1);
        userIdComboBox.getSelectionModel().selectFirst();
        userIdComboBox.getSelectionModel().select(MainController.selectedAppointment.getCustomerId() - 1);
        customerIdComboBox.getSelectionModel().selectFirst();
        customerIdComboBox.getSelectionModel().select(MainController.selectedAppointment.getCustomerId() - 1);

    }


    /**
     * Method to verify if the input from the user is valid
     * @return Returns true if all inputs are valid, returns false otherwise
     */
    public boolean validInput() {
        boolean titleInput = Verify.isVarCharFifty("Title", titleField.getText());
        boolean descriptionInput = Verify.isVarCharFifty("Description", descriptionField.getText());
        boolean locationInput = Verify.isVarCharFifty("Location", locationField.getText());
        boolean typeInput = Verify.isVarCharFifty("Type", typeField.getText());
        boolean userIdInput = false;
        LocalDateTime start = datePickerValue(0).toLocalDateTime();
        LocalDateTime end = datePickerValue(1).toLocalDateTime();
        if(userIdComboBox.getValue() != null) {
            userIdInput = true;
        }
        boolean userId = true;
        boolean customerIdInput = false;
        if(customerIdComboBox.getValue() != null) {
            customerIdInput = true;
        }
       boolean customerId = true;
        boolean datesInput = Verify.validTime(datePickerValue(0), datePickerValue(1),
                TimeZones.EST(start.toLocalTime()), TimeZones.EST(end.toLocalTime()));
        boolean dateAvailable = false;
        if (customerId) {
            dateAvailable = Verify.isAvailableDate(datePickerValue(0), datePickerValue(1),
                    Integer.parseInt(appointmentIdField.getText()));
        }
        boolean[] inputs = {titleInput, descriptionInput, locationInput, typeInput, userIdInput, userId,
                customerIdInput, customerId, datesInput, dateAvailable};
        for(boolean b : inputs) {
            if(!b) {
                return false;
            }
        }
        return true;
    }

    /**
     * Dissects the date from the date picker and splits it into date and time
     * @param datePicker Takes the date from the date picker as a parameter
     * @return Returns the dissected date and time
     */
    private ZonedDateTime datePickerValue(int datePicker){
        LocalDate date;
        LocalTime time;
        if(datePicker == 0) {
            date = startDatePicker.getValue();
            String timeString = startCombo.getValue();
            time = LocalTime.parse(timeString, timeFormatter);
        } else {
            date = endDatePicker.getValue();
            String timeString = endCombo.getValue();
            time = LocalTime.parse(timeString, timeFormatter);
        }
        return TimeZones.combinedDateTime(date, time);
    }

    /**
     * When the appointment form is initialized, field values are populated
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIdField.setText(String.valueOf(DBAppointments.generateAppointmentId()));
        startCombo.getItems().addAll(getAppointmentTimes());
        endCombo.getItems().addAll(startCombo.getItems());
        try {
            contactComboBox.setItems(DBContacts.getAllContacts());
            customerIdComboBox.setItems(DBCustomers.getAllCustomers());
            userIdComboBox.setItems(DBUsers.getAllUsers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}