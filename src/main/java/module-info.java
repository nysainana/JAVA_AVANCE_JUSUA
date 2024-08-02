module com.java.avance.javaavancem12024 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.kordamp.ikonli.coreui;
    requires java.desktop;
    requires com.jfoenix;
    requires javafx.swing;

    opens com.java.avance.javaavancem12024.ui to javafx.fxml;
    exports com.java.avance.javaavancem12024;
}