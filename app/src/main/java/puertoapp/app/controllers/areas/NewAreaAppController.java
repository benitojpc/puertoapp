package puertoapp.app.controllers.areas;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import jfxlib.win.abstracts.AbstractController;
import puertoapp.app.models.AreaModel;
import puertoapp.app.models.VAreaAppsModel;
import puertoapp.app.services.AreaService;
import puertoapp.app.services.VAreaApps;
import puertoapp.app.tools.Const;
import puertoapp.app.tools.Vars;
import puertoapp.app.tools.msgs.Messages;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class NewAreaAppController 
                extends AbstractController 
                implements Initializable
{
    private HashMap<String,String> datos = null;
    
    @FXML private AnchorPane anchorPane;
    @FXML private BorderPane areaAppPane;
    @FXML private Button btnClose;
    @FXML private Button btnNewArea;
    @FXML private Button btnSave;
    @FXML private ComboBox<AreaModel> cbxArea;
    @FXML private Label lblApp;
    @FXML private Label lblArea;
    @FXML private Label lblRuta;
    @FXML private Label lblNotas;
    @FXML private Label lblTitle;
    @FXML private TextArea taNotas;
    @FXML private TextField tfApp;
    @FXML private TextField tfRuta;

    private Integer idArea;

    public NewAreaAppController()
    {
        super();
    }


    @Override 
    public void initialize(URL url, ResourceBundle rb) 
    {
        datos = new HashMap<>();

        lblTitle.setText( Messages.getTexto( "newareaapps.title" ) );
        lblArea.setText( Messages.getTexto( "newareaapps.area" ) );
        lblApp.setText( Messages.getTexto( "newareaapps.app" ) );
        lblRuta.setText( Messages.getTexto( "newareaapps.ruta" ) );
        lblNotas.setText( Messages.getTexto( "newareaapps.notas" ) );

        cbxArea.getItems().addAll( AreaService.comboAreas() );
        cbxArea.getSelectionModel().selectFirst();

        EventHandler<ActionEvent> cbevent = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        AreaModel am = cbxArea.getValue();
                        idArea = am.getIdAreaInt();
                    }
                };
 
        // Set on action
        cbxArea.setOnAction(cbevent);

        btnClose.setText( Messages.getTexto( "btn.close" ) );
        btnClose.setOnAction((event) -> {procesar("cerrar");});

        btnNewArea.setText( Messages.getTexto( "btn.newarea" ) );
        btnNewArea.setOnAction((event) -> {procesar("nueva");});

        btnSave.setText( Messages.getTexto( "btn.save" ) );
        btnSave.setOnAction((event) -> {procesar("grabar");});

    }

    private void procesar( String action ) 
    {
        switch (action.toLowerCase()) 
        {

            case "cerrar"   -> cerrarVista();
            case "grabar"   -> grabar();
            case "nueva"    -> nueva();

        }
        
    }

    private void cerrarVista() 
    {
        anchorPane.getScene().getWindow().hide();
    }

    private void grabar() 
    {
        cerrarVista();
    }

    private void nueva() 
    {

        Dialog dialog = new TextInputDialog( "" );
        dialog.setTitle( Messages.getTexto( "newarea.title" ) );
        dialog.setHeaderText( Messages.getTexto( "newarea.header" ) );
        
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) 
        {
            String resultado = result.get();
            if (!resultado.isEmpty())
            {
                AreaModel am = new AreaModel();
                am.setArea( resultado );
                idArea = AreaService.grabar( am );            
                am = AreaService.buscar( idArea );
                cbxArea.getItems().addAll( AreaService.comboAreas() );
                cbxArea.getSelectionModel().select( am );
            }

        }
        
    }

}
