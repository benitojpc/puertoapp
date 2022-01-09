module puertoapp {
    
    requires transitive java.sql;
    requires puertobd;

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires libjfx;

    opens puertoapp.app.controllers.login to javafx.fxml;
    opens puertoapp.app.controllers.menu to javafx.fxml;
    opens puertoapp.app.controllers.areas to javafx.fxml;
    
    opens puertoapp.app.models to javafx.base;
    
    exports puertoapp.app to javafx.graphics;

}
