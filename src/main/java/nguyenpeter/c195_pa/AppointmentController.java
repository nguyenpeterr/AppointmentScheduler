package nguyenpeter.c195_pa;

import com.dlsc.formsfx.model.validators.StringLengthValidator;
import javafx.beans.Observable;
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
import javafx.util.StringConverter;
import model.Appointments;
import util.TimeZones;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
    private ComboBox<?> contactComboBox;
    @FXML
    private Label contactLabel;
    @FXML
    private TextField customerIdField;
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
    private Spinner<?> endTimeSpinner;
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
    private Spinner<?> startTimeSpinner;
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
    ObservableList<LocalTime> hours = genHours();
    SpinnerValueFactory startTImeVF = new SpinnerValueFactory.ListSpinnerValueFactory<>(hours);
    SpinnerValueFactory endTimeVF = new SpinnerValueFactory.ListSpinnerValueFactory<>(hours);

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
    void onSaveButton(ActionEvent event) {

    }

    @FXML
    void onStartSpinnerClick(MouseEvent event) {

    }

    public void sendAppointment(Appointments appointment) {
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
        endTimeVF.setValue(endDate);

//        contactComboBox.getSelectionModel().selectFirst();
//        contactComboBox.getSelectionModel().select(appointment.getContactId() - 1);
        userIdField.setText(String.valueOf(appointment.getUserId()));
        customerIdField.setText(String.valueOf(appointment.getCustomerId()));
//        this.appointmentId = appointmentId;
//        this.title = title;
//        this.description = description;
//        this.location = location;
//        this.type = type;
//        this.start = start;
//        this.end = end;
//        this.createDate = createDate;
//        this.createdBy = createdBy;
//        this.lastUpdate = lastUpdate;
//        this.lastUpdatedBy = lastUpdatedBy;
//        this.customerId = customerId;
//        this.userId = userId;
//        this.contactId = contactId;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}