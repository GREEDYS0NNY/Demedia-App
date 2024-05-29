module com.de.project_demedia_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.jfoenix;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires javafx.media;
    requires org.jetbrains.annotations;
    requires org.apache.logging.log4j.core;


    opens com.de.project_demedia_app to javafx.fxml;
    exports com.de.project_demedia_app;
    exports com.de.project_demedia_app.Controllers.Windows;
    opens com.de.project_demedia_app.Controllers.Windows to javafx.fxml;
    exports com.de.project_demedia_app.Controllers.MainWindowPanels;
    opens com.de.project_demedia_app.Controllers.MainWindowPanels to javafx.fxml;
    exports com.de.project_demedia_app.Controllers.Sections;
    opens com.de.project_demedia_app.Controllers.Sections to javafx.fxml;
    exports com.de.project_demedia_app.Controllers.MediaPages;
    exports com.de.project_demedia_app.Models;
    opens com.de.project_demedia_app.Models to javafx.fxml;
    exports com.de.project_demedia_app.Views;
    opens com.de.project_demedia_app.Views to javafx.fxml;
}