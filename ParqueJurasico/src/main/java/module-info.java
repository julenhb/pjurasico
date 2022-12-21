module com.example.parquejurasico {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.persistence;
    requires javafx.graphics;
    requires java.logging;
    requires net.bytebuddy;
    requires java.sql;
    requires java.naming;
    requires mysql.connector.java;
    requires org.hibernate.orm.core;


    opens clases to org.hibernate.orm.core;
    exports com.example.parquejurasico;
    opens utilidades to org.hibernate.orm.core;
    opens com.example.parquejurasico to javafx.fxml, org.hibernate.orm.core;
}