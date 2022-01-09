package puertoapp.app.controllers.areas;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import jfxlib.win.ModalWindow;
import jfxlib.win.abstracts.AbstractController;
import puertoapp.app.models.VAreaAppsModel;
import puertoapp.app.services.VAreaApps;
import puertoapp.app.tools.Const;
import puertoapp.app.tools.Vars;
import puertoapp.app.tools.msgs.Messages;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AreaAppsController 
                extends AbstractController 
                implements Initializable
{
 
    private HashMap<String,String> datos = null;

    @FXML private AnchorPane AnchorPane;
    @FXML private Button btnClose;
    @FXML private Button btnUndo;
    @FXML private Button btnAccept;
    @FXML private Button btnNewAreaApp;

    @FXML private Label lblTitle;
    @FXML private Label lblArea;
    @FXML private Label lblId;
    @FXML private Label lblIdNum;
    @FXML private Label tabAreaTitle;

    @FXML private TextField tfArea;
    @FXML private TextField tfBuscar;

    @FXML private Tab tabView;
    @FXML private Tab tabApps;
    @FXML private Tab tabArea;
    @FXML private TabPane tabPane;

    @FXML private TableView<VAreaAppsModel> tblView;
    @FXML private TableColumn<VAreaAppsModel, Number>    idarea;
    @FXML private TableColumn<VAreaAppsModel, Number>    idapp;
    @FXML private TableColumn<VAreaAppsModel, String>    area;
    @FXML private TableColumn<VAreaAppsModel, String>    aplicacion;
    @FXML private TableColumn<VAreaAppsModel, String>    ruta;
    @FXML private TableColumn<VAreaAppsModel, String>    notas;
    @FXML private TableColumn<VAreaAppsModel, LocalDate> fecha;

    private ObservableList<VAreaAppsModel> obsList;
    protected FilteredList<VAreaAppsModel> filterList;
    protected SortedList<VAreaAppsModel>   sortedList;

    private static final String STYLE_CLASS = Const.TWELVE_FONT;
    private VAreaAppsModel vaam = null;
    
    public AreaAppsController()
    {
        super();
    }


    @Override 
    public void initialize(URL url, ResourceBundle rb) 
    {
        datos = new HashMap<>();

        btnClose.setText( Messages.getTexto( "btn.close" ) );
        btnClose.setOnAction((event) -> {procesar("cerrar");});

        btnAccept.setText( Messages.getTexto( "btn.accept" ) );
        btnAccept.setOnAction((event) -> {procesar("aceptar");});

        btnNewAreaApp.setText( Messages.getTexto( "btn.newareaapp" ) );
        btnNewAreaApp.setOnAction((event) -> {procesar("nwareaapp");});

        btnUndo.setText( Messages.getTexto( "btn.undo" ) );
        btnUndo.setOnAction((event) -> {procesar("undo");});

        tabView.setText( Messages.getTexto( "vareaapps.tabview" ) );
        tabApps.setText( Messages.getTexto( "vareaapps.tabarea" ) );
        tabArea.setText( Messages.getTexto( "vareaapps.tabapps" ) );

        lblTitle.setText( Messages.getTexto( "vareaapps.title" ) );
        tabAreaTitle.setText( Messages.getTexto( "vareaapps.tarea.title" ) );
        lblArea.setText( Messages.getTexto( "vareaapps.tarea.area" ) );
        lblId.setText( Messages.getTexto( "vareaapps.tarea.id" ) );

        tfBuscar.textProperty().addListener((ObservableValue<? extends Object> observable, Object o, Object n) -> {
            filterList.setPredicate((VAreaAppsModel p) -> {
                if (n != null) 
                {
                    String lowervalue = ((String) n).toLowerCase();
                    if (n instanceof String) 
                    {
                        if (p.getArea().toLowerCase().contains(lowervalue)) return true;
                        else if (p.getApp().toLowerCase().contains(lowervalue)) return true;
                        else if (p.getRuta().toLowerCase().contains(lowervalue)) return true;
                        else if (p.getNotas().toLowerCase().contains(lowervalue)) return true;
                        else if (p.getFechaStr().contains(lowervalue)) return true;
                        else return false;
                    } 
                    else return false;
                } 
                else  return false;
            });
        });

                
        // seleccionar un registro de la tabla.
        tblView.getSelectionModel().selectedItemProperty().addListener((observable, ov, nv) -> {
            if (nv != null) {
                vaam = (VAreaAppsModel) nv;
                lblIdNum.setText( vaam.getIdAppStr() );
                tfArea.setText( vaam.areaProperty().get() );
            }
        });

        crearTabla();

    }

    private void procesar( String action ) 
    {

        switch (action.toLowerCase()) 
        {

            case "cerrar"       -> cerrar();
            case "aceptar"      -> aceptar();
            case "undo"         -> deshacer();
            case "nwareaapp"    -> newAreaApp();

        }
    
    }

    private void newAreaApp() 
    {
        
        Stage currentStage = (Stage) btnNewAreaApp.getScene().getWindow();
        HashMap<String,Object> datosWin = Vars.crearFormulario("newareaapp", currentStage);
        new ModalWindow( datosWin );
        
    }

    private void cerrar() 
    {
        try 
        {
            Vars.crearHandler( "menu" );
            Vars.apph.launchWindow();
        } catch (IOException ioe)
        {
            String msg = String.format("[-] Error: %s", ioe.getMessage());
            System.out.println( msg );
        }
    }

    private void aceptar() 
    {}

    private void deshacer() 
    {}

    private void crearTabla() 
    {
        tblView.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );

        fecha       = new TableColumn<>( Messages.getTexto( "tblvapp.fecha"));
        fecha.setPrefWidth( columnWidth( "fecha" ));
        fecha.getStyleClass().add( STYLE_CLASS );
        fecha.setResizable( Boolean.FALSE );

        area        = new TableColumn<>( Messages.getTexto( "tblvapp.area"));
        area.setPrefWidth( columnWidth( "area" ));
        area.getStyleClass().add( STYLE_CLASS );
        area.setResizable( Boolean.FALSE );

        aplicacion  = new TableColumn<>( Messages.getTexto( "tblvapp.app"));
        aplicacion.setPrefWidth( columnWidth( "aplicacion" ));
        aplicacion.getStyleClass().add( STYLE_CLASS );
        aplicacion.setResizable( Boolean.FALSE );

        ruta        = new TableColumn<>( Messages.getTexto( "tblvapp.ruta")); 
        ruta.setPrefWidth( columnWidth( "ruta" ));
        ruta.getStyleClass().add( STYLE_CLASS );
        ruta.setResizable( Boolean.FALSE );


        tblView.getColumns().add( fecha );
        tblView.getColumns().add( area );
        tblView.getColumns().add( aplicacion );
        tblView.getColumns().add( ruta );

        obsList     = VAreaApps.datos();
        filterList  = new FilteredList<>( obsList, p -> true );

        fecha.setCellValueFactory( new PropertyValueFactory<>("fecha") );
        area.setCellValueFactory( new PropertyValueFactory<>("area") );
        aplicacion.setCellValueFactory( new PropertyValueFactory<>( "app" ) );
        ruta.setCellValueFactory( new PropertyValueFactory<>( "ruta" ) );

        cargarTabla();

    }

    private void cargarTabla() 
    {

        sortedList = new SortedList<>( filterList );
        sortedList.comparatorProperty().bind( tblView.comparatorProperty() );
        tblView.setItems( sortedList );

        if (vaam != null) 
        {
            tblView.getSelectionModel().select( vaam );
        }

    }

    private Double columnWidth(String c) {

        switch (c.toLowerCase()) {

            case "idarea"       : return 50.0;
            case "idapp"        : return 50.0;
            case "area"         : return 200.0;
            case "aplicacion"   : return 250.0;
            case "notas"        : return 250.0;
            case "fecha"        : return 150.0;
            case "ruta"         : return 380.0;
            default             : return 10.0;

        }

    }

}
