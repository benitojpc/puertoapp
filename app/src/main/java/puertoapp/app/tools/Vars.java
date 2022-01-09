package puertoapp.app.tools;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import jfxlib.win.AppHandler;
import jfxlib.win.ModalWindow;
import puertoapp.app.controllers.areas.AreaAppsController;
import puertoapp.app.controllers.areas.NewAreaAppController;
import puertoapp.app.controllers.login.LoginController;
import puertoapp.app.controllers.menu.MenuController;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


public class Vars {

    private static HashMap<String, Object> datos;
    private static final String ZONE_ID = "Europe/Madrid";

    public static AppHandler apph;
    public static Stage stage;
    public static ResourceBundle bundle = null;
    //public static ResourceBundle bundle = ResourceBundle.getBundle("puerto3App.tools.messages.app_messages", Locale.getDefault());    
    
    public static void crearHandler(String frame) 
    {
        Vars.crearMapa(frame);
        Vars.apph = new AppHandler(Vars.datos);
    }

    public static HashMap<String,Object> crearFormulario( String frame, Stage stage ) 
    {
        Vars.datos = new HashMap<>();
        
        Path path = Paths.get("");
        // esta línea se activa cuando se compila con jlink
		String abspath = path.toAbsolutePath().toString() + "/app/build";
        // esta cuando utilizamos un jar o gradlew :app:run
        //String abspath = path.toAbsolutePath().toString() + "/src/main";

        switch (frame.toLowerCase())
        {
            case "newareaapp"   -> Vars.crearFormAreaApp( abspath, stage );
            default -> defaultOption();
        }

        return Vars.datos;

    } 

    private static void crearMapa(String frame) 
    {
        Vars.datos = new HashMap<>();
        
        Path path = Paths.get("");
        // esta línea se activa cuando se compila con jlink
		String abspath = path.toAbsolutePath().toString() + "/app/build";
        // esta cuando utilizamos un jar o gradlew :app:run
        //String abspath = path.toAbsolutePath().toString() + "/src/main";

        switch (frame.toLowerCase())
        {
            case "login" -> Vars.crearLogin( abspath );
            case "menu" -> Vars.crearMenu( abspath );
            case "areas" -> Vars.crearAreaApps( abspath );
            default -> defaultOption();
        }

    } 

    private static String defaultOption() 
    {
        return "";
    }

    private static void crearLogin( String abspath ) 
    {
        datos.put("fxml",           "login.fxml");
        datos.put("ruta",           String.format("%s/%s", abspath, "resources/fxmls/login/"));
        datos.put("site",           "main");
        datos.put("template",       "mainTemplate");
        datos.put("modal",          Boolean.TRUE);
        datos.put("ruta_template",  String.format("%s/%s", abspath, "resources/templates/"));
        datos.put("controller",     new LoginController());
        datos.put("stage",          Vars.stage);
        datos.put("bundle",         Vars.bundle);
    }

    private static void crearMenu( String abspath )
    {
        datos.put("fxml",           "menu.fxml");
        datos.put("ruta",           String.format("%s/%s", abspath, "resources/fxmls/menu/"));
        datos.put("site",           "main");
        datos.put("template",       "mainTemplate");
        datos.put("modal",          Boolean.TRUE);
        datos.put("ruta_template",  String.format("%s/%s", abspath, "resources/templates/"));
        datos.put("controller",     new MenuController());
        datos.put("stage",          Vars.stage);
        datos.put("bundle",         Vars.bundle);
    }

    private static void crearAreaApps( String abspath )
    {
        datos.put("fxml",           "areaapps.fxml");
        datos.put("ruta",           String.format("%s/%s", abspath, "resources/fxmls/areas/"));
        datos.put("site",           "main");
        datos.put("template",       "mainTemplate");
        datos.put("modal",          Boolean.TRUE);
        datos.put("ruta_template",  String.format("%s/%s", abspath, "resources/templates/"));
        datos.put("controller",     new AreaAppsController());
        datos.put("stage",          Vars.stage);
        datos.put("bundle",         Vars.bundle);
    }

    private static void crearFormAreaApp( String abspath, Stage stage ) 
    {
        datos.put("fxml",           "nuevo_areaapps.fxml");
        datos.put("ruta",           String.format("%s/%s", abspath, "resources/fxmls/areas/"));
        datos.put("site",           "main");
        datos.put("template",       "mainTemplate");
        datos.put("modal",          Boolean.TRUE);
        datos.put("ruta_template",  String.format("%s/%s", abspath, "resources/templates/"));
        datos.put("controller",     new NewAreaAppController());
        datos.put("stage",          Vars.stage);
    }
        
    public static Boolean mostrarDialogo(HashMap<String,String> datos, String tipo) {

        Alert alert = null;
        switch (tipo.toLowerCase()){
            case "confirmar":
                alert = new Alert(Alert.AlertType.CONFIRMATION);

                alert.setTitle(datos.get("title"));
                alert.setHeaderText(datos.get("header"));
                alert.setContentText(datos.get("context"));
    
                Optional<ButtonType> result = alert.showAndWait();
                return (result.get() == ButtonType.OK);
            
            case "aviso":
                alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle(datos.get("title"));
                alert.setHeaderText(datos.get("header"));
                alert.setContentText(datos.get("context"));
                alert.showAndWait();
                return true;
            
            case "informacion":
                alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle(datos.get("title"));
                alert.setHeaderText(datos.get("header"));
                alert.setContentText(datos.get("context"));
                alert.showAndWait();
                return true;
            
            default:
                return false;
            
        }

    }

    public static LocalDate fechaHoy() 
    {
        ZoneId zoneId = ZoneId.of( ZONE_ID );
        LocalDate ld = LocalDate.now(zoneId);
        return ld;
    }

    public static String fechaCompleta( LocalDate ld )
    {

        String formattedDate = ld.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        return formattedDate;

    }

    public static String fecha2Str( LocalDate ld, String fmt ) 
    {

        String formattedDate = ld.format(DateTimeFormatter.ofPattern( (fmt.isEmpty() ? "dd/MM/yyyy" : fmt ) ));
        return formattedDate;
        
    }

    public static LocalDate date2LocalDate( Date df ) {
        ZoneId zoneId = ZoneId.of( ZONE_ID );
        return df.toInstant()
                    .atZone( zoneId )
                    .toLocalDate();
    }

    public static LocalDate time2LocalDate( Timestamp ts )
    {
        ZoneId zoneId = ZoneId.of( ZONE_ID );
        return ts.toInstant()
                    .atZone( zoneId )
                    .toLocalDate();
    }

}
