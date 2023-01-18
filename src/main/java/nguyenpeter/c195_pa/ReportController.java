package nguyenpeter.c195_pa;

import database.DBContacts;
import database.DBCountries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import database.DBAppointments;
import model.Appointments;
import model.Contacts;
import model.Countries;

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


    private ObservableList<Month> getMonths() {
        return FXCollections.observableArrayList(Month.values());
    }

    @FXML
    void onCloseButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void onRadioSelect(ActionEvent event) throws SQLException {
        if(rButtonContactSched.isSelected()) {
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
