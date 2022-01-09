package jfxlib.win;

import jfxlib.tools.Vars;
import jfxlib.win.abstracts.AbstractController;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Modality;

import java.io.IOException;
import java.io.File;

import jfxlib.tools.PageFXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class ModalWindow 
{
    public ModalWindow(HashMap<String, Object> datos) 
    {
        Vars.fxml       = (String)datos.get("fxml");
        Vars.fruta      = (String)datos.get("ruta");
        Vars.site       = "#" + (String)datos.get("site");
        Vars.template   = (String)datos.get("template");
        Vars.modal      = (Boolean)datos.get("modal");
        Vars.ruta_templ = (String)datos.get("ruta_template");
        Vars.ac         = (AbstractController)datos.get("controller");
        Vars.pstage     = (Stage)datos.get("stage");
        Vars.rb         = null;
        Vars.aw         = null;
        verVentana();
    }

    private void verVentana() 
    {
    
        try 
        {

            String ruta_file = Vars.fruta + Vars.fxml; 
            File f = new File( ruta_file );

            FXMLLoader loader = new FXMLLoader( f.toURI().toURL() );
            loader.setController( Vars.ac );

            AnchorPane atemplate = loader.load();
            Scene nscene = new Scene(atemplate);
            Stage stage = new Stage();
            stage.setScene(nscene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            stage.close();

        } catch(IOException ioe){ System.out.println( ioe.getMessage() ); }

    }
    
}