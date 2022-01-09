package puertoapp.app.controllers.menu;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import jfxlib.win.abstracts.AbstractController;
import puertoapp.app.tools.Vars;
import puertoapp.app.tools.msgs.Messages;

public class MenuController  
                extends AbstractController 
                implements Initializable {

    ResourceBundle rb = null; //Vars.bundle;

    @FXML private Menu mEntities;
    @FXML private AnchorPane menuPane;
    @FXML private MenuItem miAreas;
    @FXML private MenuItem miClose;

    private HashMap<String,String> datos = new HashMap<>();

    public MenuController() { super(); }

    @Override 
    public void initialize(URL url, ResourceBundle rb) 
    {
        mEntities.setText( Messages.getTexto( "menu.entities" ) );
        miAreas.setText( Messages.getTexto( "menui.areas" ) );
        miClose.setText( Messages.getTexto( "menui.close" ) );

        miAreas.setOnAction( (event) -> {procesar( "areas" );} );
        miClose.setOnAction( (event) -> {procesar( "exit" );} );
    }

    private void procesar( String action ) 
    {

        switch (action.toLowerCase()) 
        {

            case "areas" -> areaApps();
            case "exit"  -> cerrarAplicacion();

        }

    }

    private void areaApps() 
    {
        try 
        {
            Vars.crearHandler( "areas" );
            Vars.apph.launchWindow();
        } catch (IOException ioe)
        {
            String msg = String.format("[-] Error: %s", ioe.getMessage());
            System.out.println( msg );
        }

    }

    private void cerrarAplicacion() 
    {

        datos.put( "title", Messages.getTexto( "msg.exit.title" ) );
        datos.put( "header", Messages.getTexto( "msg.exit.header" ) );
        datos.put( "context", Messages.getTexto( "msg.exit.context" ) );

        if (Vars.mostrarDialogo(datos, "confirmar")) {
            Platform.exit();
        }

    }

}