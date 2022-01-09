package jfxlib.win.abstracts;

import java.io.IOException;
import java.io.File;
import jfxlib.tools.PageFXML;
import jfxlib.tools.Vars;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;



public abstract class AbstractWindow 
{
    protected AbstractWindow() {}
    
    public Scene root() {
        
        try {
        
            String path_template = Vars.ruta_templ + Vars.template + ".fxml"; 

            File f = new File(path_template);
            PageFXML pfxml = new PageFXML();

            AnchorPane main = pfxml.getPane();
            FXMLLoader loader;
            loader = new FXMLLoader(f.toURI().toURL());
        
            AnchorPane atemplate = loader.load();
            
            // busca el lugar en el que incluir el fichero fxml en el template
            Pane pane = (Pane)atemplate.lookup(Vars.site); 
            pane.getChildren().setAll(main.getChildren());
            return new Scene(atemplate);
    
        } catch (IOException e) {
            System.out.println( String.format("[-] Error: %s", e.getMessage()));
            e.printStackTrace();
            return null;
        }
    
    }
    
    public String iconFilePath() { return ""; }
    public boolean resizable() { return false; }
    public abstract String iconFileName();
    public abstract String fxmlFileName();
    public abstract String titleBundleKey();
    public abstract Boolean esModal();    
    
}