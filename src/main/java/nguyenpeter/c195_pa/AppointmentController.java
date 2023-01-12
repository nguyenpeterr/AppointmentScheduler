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
    private Spinner<LocalTime> endTimeSpinner;
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
    private Spinner<LocalTime> startTimeSpinner;
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
    private ObservableList<LocalTime> genHours() {
        ObservableList<LocalTime> hours = FXCollections.observableArrayList();
        for(int i=0; i <= 23; i++) {
            for(int m=0; m <= 1; m++) {
                hours.add(TimeZones.setLocalTime(i, m*30));
            }
        } return hours;
    }
    public ObservableList<LocalTime> hours = genHours();
    public SpinnerValueFactory<LocalTime> startTImeVF = new SpinnerValueFactory.ListSpinnerValueFactory<>(hours);
    public SpinnerValueFactory<LocalTime> endTimeVF = new SpinnerValueFactory.ListSpinnerValueFactory<>(hours);


    @FXML
    void onCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(MainController.class.getResource("Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Appointment Management System");
        stage.show();
    }

    @FXML
    void onEndSpinnerClick(MouseEvent event) {

    }

    @FXML
    void onSaveButton(ActionEvent event) throws IOException {
        if(validInput()) {
            if(MainController.selectedAppointment != null) {
                DBAppointments.updateAppointment(createAppointment());
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(MainController.class.getResource("Main.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Appointment Management System");
                stage.show();
            }
        } else {
                addAppointment();
            }
    }

    public void addAppointment() {
        DBAppointments.addAppointment(createAppointment());
    }

    public Appointments createAppointment() {
        int appointmentId = Integer.parseInt(appointmentIdField.getText());
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        ZonedDateTime start = datePickerValue(0);
        ZonedDateTime end = datePickerValue(1);
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

    @FXML
    void onStartSpinnerClick(MouseEvent event) {

    }


    public void sendAppointment(Appointments appointment) throws SQLException {
        appointmentIdField.setText(String.valueOf(appointment.getAppointmentId()));
        titleField.setText(String.valueOf(appointment.getTitle()));
        descriptionField.setText(String.valueOf(appointment.getDescription()));
        locationField.setText(String.valueOf(appointment.getLocation()));
        typeField.setText(String.valueOf(appointment.getType()));
        LocalDate startDate = TimeZones.toLocal(MainController.selectedAppointment.getStart()).toLocalDate();
        LocalTime startTime = TimeZones.toLocal(MainController.selectedAppointment.getStart()).toLocalTime();
        startDatePicker.setValue(startDate);
        startTImeVF.setValue(startTime);
        LocalDate endDate = TimeZones.toLocal(MainController.selectedAppointment.getEnd()).toLocalDate();
        LocalTime endTime = TimeZones.toLocal(MainController.selectedAppointment.getEnd()).toLocalTime();
        endDatePicker.setValue(endDate);
        endTimeVF.setValue(endTime);
        contactComboBox.getSelectionModel().selectFirst();
        contactComboBox.getSelectionModel().select(MainController.selectedAppointment.getContactId() - 1);
        userIdComboBox.getSelectionModel().selectFirst();
        userIdComboBox.getSelectionModel().select(MainController.selectedAppointment.getCustomerId() - 1);
        customerIdComboBox.getSelectionModel().selectFirst();
        customerIdComboBox.getSelectionModel().select(MainController.selectedAppointment.getCustomerId() - 1);

    }


    public boolean validInput() {
        boolean titleInput = Verify.isVarCharFifty("Title", titleField.getText());
        boolean descriptionInput = Verify.isVarCharFifty("Description", descriptionField.getText());
        boolean locationInput = Verify.isVarCharFifty("Location", locationField.getText());
        boolean typeInput = Verify.isVarCharFifty("Type", typeField.getText());
        boolean userIdInput = Verify.validInt(String.valueOf(userIdComboBox.getValue()));
        boolean userId = false;
        if (userIdInput) {
            userId = Verify.validUserId(Integer.parseInt(String.valueOf(userIdComboBox.getValue())));
        }
        boolean customerIdInput = Verify.validInt(String.valueOf(customerIdComboBox.getValue()));
        boolean customerId = false;
        if (customerIdInput) {
            customerId = Verify.validCustomer(Integer.parseInt(String.valueOf(customerIdComboBox.getValue())));
        }
        boolean datesInput = Verify.validTime(datePickerValue(0), datePickerValue(1),
                TimeZones.EST(startTimeSpinner.getValue()), TimeZones.EST(endTimeSpinner.getValue()));
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

    private ZonedDateTime datePickerValue(int datePicker){
        LocalDate date;
        LocalTime time;
        if(datePicker == 0) {
            date = startDatePicker.getValue();
            time = startTimeSpinner.getValue();
        } else {
            date = endDatePicker.getValue();
            time = endTimeSpinner.getValue();
        }
        return TimeZones.combinedDateTime(date, time);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIdField.setText(String.valueOf(DBAppointments.generateAppointmentId()));
        startTimeSpinner.setValueFactory(startTImeVF);
        endTimeSpinner.setValueFactory(endTimeVF);
        try {
            contactComboBox.setItems(DBContacts.getAllContacts());
            customerIdComboBox.setItems(DBCustomers.getAllCustomers());
            userIdComboBox.setItems(DBUsers.getAllUsers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}