package jfxlib.tools; 

import java.io.IOException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class PageFXML 
{
    
    private AnchorPane pane;
    
    public PageFXML()
    {
        init();
    }
    
    public AnchorPane getPane()
    { 
        return this.pane; 
    }
    
    void init()
    {
        String fxml = Vars.fruta + Vars.fxml;        
        
        File f = new File(fxml);
        
        try {
            FXMLLoader loader = new FXMLLoader(f.toURI().toURL());
            loader.setController(Vars.ac);
            pane = (AnchorPane)loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PageFXML.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println( String.format("[-] Error: %s",ex.getMessage() ));
        }
    }

}