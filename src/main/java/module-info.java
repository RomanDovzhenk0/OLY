module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires xmlrpc.client;
    requires xmlrpc.common;
    requires xmlrpc.server;

    opens com.server to xmlrpc.client, xmlrpc.common, xmlrpc.server;
    opens com.client.ad to javafx.fxml;
    opens com.client.authenticator.login to javafx.fxml;
    opens com.client.authenticator.register to javafx.fxml;
    opens com.client.browser to javafx.fxml;
    opens com.client.createAd to javafx.fxml;
    opens com.client.myProfile to javafx.fxml;
    opens com.serviceLib.services to xmlrpc.client, xmlrpc.common, xmlrpc.server;
    opens com.serviceLib.interfaces to xmlrpc.client, xmlrpc.common, xmlrpc.server;
    opens com.client to xmlrpc.client, xmlrpc.common, xmlrpc.server;

    exports com.client;
    exports com.serviceLib.services;
    exports com.serviceLib.interfaces;
    exports com.server;
}