package nguyenpeter.c195_pa;

import database.DBContacts;
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
import javafx.stage.Stage;
import database.DBAppointments;
import model.Appointments;
import model.Contacts;
import util.TimeZones;

import java.time.Month;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public abstract class ReportController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    private TableColumn<?, ?> apptIdCol;
    @FXML
    private Label comboLabel;
    @FXML
    private TableView<?> contactScheduleTableView;
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
    private TableView<?> customersTableView;
    @FXML
    private TableColumn<?, ?> descCol;
    @FXML
    private TableColumn<?, ?> endDateCol;
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
    private ComboBox<?> reportComboBox;
    @FXML
    private ToggleGroup reportTG;
    @FXML
    private TableColumn<?, ?> startDateCol;
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


    @FXML
    void onCloseButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void onRadioSelect(ActionEvent event) {
        if(rButtonContactSched.isSelected()) {
            comboLabel.setText("Contact ID");
            contactScheduleTableView.setVisible(true);
            customersTableView.setVisible(false);
            totalCustLabel.setText("");
            intTotalCustLabel.setText("");
        }
        if(rButtonByType.isSelected() || rButtonByMonth.isSelected() || rButtonByCountry.isSelected()) {
            comboLabel.setText("Customer ID");
            contactScheduleTableView.setVisible(false);
            customersTableView.setVisible(true);
            totalCustLabel.setText("Total Customers:");
            intTotalCustLabel.setText("--");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboLabel.setText("");
        totalCustLabel.setText("");
        intTotalCustLabel.setText("");
    }

//    public static String contactSchedule() throws SQLException {
//        int appt = 1;
//        StringBuilder schedule = new StringBuilder("");
//        ObservableList<Appointments> contactAppointments;
//        ObservableList<Contacts> contacts = FXCollections.observableArrayList();
//        try {
//            contacts = DBContacts.getAllContacts();
//        }
//        catch(SQLException sqlE) {}
//        for (Contacts c : contacts) {
//            appt = 1;
//            contactAppointments = DBAppointments.getContactAppts(c.getContactId());
//            schedule.append("Contact: ").append(c.getContactName()).append("\n");
//            for (Appointments a : contactAppointments) {
//                schedule.append("|Appointment ").append(appt).append("|\nAppointment_ID: ")
//                        .append(a.getAppointmentId()).append("\nTitle: ").append(a.getTitle()).append("\nType: ")
//                        .append(a.getType()).append("\nDescription: ").append(a.getDescription()).append("\nStart: ")
//                        .append(TimeZones.reportEST(a.getStart())).append("\nEnd: ")
//                        .append(TimeZones.reportEST(a.getEnd())).append("\nCustomer ID: ")
//                        .append(a.getCustomerId()).append("\n\n");
//                appt +=1;
//            }
//        }
//        return schedule.toString();
//    }

}
