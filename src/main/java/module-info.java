module nguyenpeter.c195_pa {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens nguyenpeter.appointmentscheduler to javafx.fxml;
    exports nguyenpeter.appointmentscheduler;
    exports controller;
    exports model;
    opens controller to javafx.fxml;
    opens model to javafx.base, javafx.fxml;
    exports util;
    opens util to javafx.fxml;
}