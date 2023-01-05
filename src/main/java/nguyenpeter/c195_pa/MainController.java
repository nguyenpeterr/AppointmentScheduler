package nguyenpeter.c195_pa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<?, ?> addressCol_c;

    @FXML
    private TableColumn<?, ?> appointmentIdCol_a;

    @FXML
    private Rectangle appointmentRectangle;

    @FXML
    private TableView<?> appointmentTableView;

    @FXML
    private TableColumn<?, ?> contactCol_a;

    @FXML
    private TableColumn<?, ?> createDateCol_c;

    @FXML
    private TableColumn<?, ?> createdByCol_c;

    @FXML
    private TableColumn<?, ?> customerIdCol_a;

    @FXML
    private TableColumn<?, ?> customerIdCol_c;

    @FXML
    private TableColumn<?, ?> customerNameCol_c;

    @FXML
    private TableView<?> customerTableView;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<?, ?> descriptionCol_a;

    @FXML
    private Button dismissRectangleButton;

    @FXML
    private TableColumn<?, ?> divisionIdCol_c;

    @FXML
    private TableColumn<?, ?> endCol_a;

    @FXML
    private TableColumn<?, ?> lastUpdateCol_c;

    @FXML
    private TableColumn<?, ?> lastUpdatedByCol_c;

    @FXML
    private TableColumn<?, ?> locationCol_a;

    @FXML
    private Button logOutButton;

    @FXML
    private ToggleGroup apptcustTG;

    @FXML
    private TableColumn<?, ?> phoneCol_c;

    @FXML
    private TableColumn<?, ?> postalCodeCol_c;

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

    @FXML
    void onAllAppointmentsRadio(ActionEvent event) {

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
    void onReportsButton(ActionEvent event) {

    }

    @FXML
    void onUpdateButton(ActionEvent event) {

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
    }

    @FXML
    void onCustomerToggle(ActionEvent event) {
        appointmentTableView.setVisible(false);
        appointmentTableView.setDisable(true);
        customerTableView.setVisible(true);
        customerTableView.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleAppointmentButton.isSelected();
        timeZone.setText(String.valueOf(ZoneId.systemDefault()));
    }
}