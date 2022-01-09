package puertoapp.app.controllers.login;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import jfxlib.win.abstracts.AbstractController;
//import puertoapp.app.services.VAreaApps;
import puertoapp.app.tools.Vars;
import puertoapp.app.tools.msgs.Messages;

public class LoginController 
            extends AbstractController 
            implements Initializable 
{

    ResourceBundle rb = null; //Vars.bundle;

    @FXML private Label lblTitle;
    @FXML private Label lblUser;
    @FXML private Label lblPass;

    @FXML private AnchorPane anchorLogin;
    @FXML private Button btCancel;
    @FXML private Button btValidate;
    
    @FXML private PasswordField passwd;
    @FXML private TextField username;

    private HashMap<String,String> datos = new HashMap<>();

    public LoginController() { super(); }

    @Override 
    public void initialize(URL url, ResourceBundle rb) 
    {
        
        lblTitle.setText( Messages.getTexto( "login.title" ) );
        lblUser.setText( Messages.getTexto( "login.user" ) );
        lblPass.setText( Messages.getTexto( "login.pass" ) );

        btValidate.setText( Messages.getTexto( "btn.validate" ) );
        btCancel.setText( Messages.getTexto( "btn.cancel" ) );

        username.setFocusTraversable(true);

        btCancel.setOnAction((event) -> {procesar("exit");});
        btValidate.setOnAction((event) -> {procesar("validate");});

        username.requestFocus();

//        checkPuertoBD();

    }

    private void procesar( String action ) 
    {

        switch (action.toLowerCase()) 
        {

            case "exit" -> cerrarAplicacion();
            case "validate" -> validarUsuario();

        }

    }

    private void validarUsuario() 
    {
        String uname = username.getText();
        String upass = passwd.getText();

        if ("administrador".equalsIgnoreCase(uname) && "canoa1".equals(upass))
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
            
        } else  
        {
            errorValidacion();
        }
    }

    private void errorValidacion()
    {

        datos.put( "title", Messages.getTexto( "evalidate.title" ) );
        datos.put( "header", Messages.getTexto( "evalidate.header" ) );
        datos.put( "context", Messages.getTexto( "evalidate.context" ) );

        if (Vars.mostrarDialogo(datos, "confirmar")) {
            username.requestFocus();
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
/*
    private void checkPuertoBD() 
    {
        
        System.out.println( "\n\nVista Aplicaciones-Areas" );
        VAreaApps.verDatos();
        
    }
*/

}