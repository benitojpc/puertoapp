package jfxlib.win; 

import jfxlib.win.interfaces.ViewHandler; 
import jfxlib.win.interfaces.WindowFactory;
import jfxlib.win.abstracts.AbstractController;
import jfxlib.win.abstracts.AbstractWindow;

import java.io.IOException;
import java.util.HashMap;

import javafx.stage.Stage;

import jfxlib.tools.Vars;

public class AppHandler 
    implements ViewHandler, WindowFactory 
{

    /**
     * 
     * @param datos     : Dictionary array con los valores para poder configurar la vista
     *             fxml : fichero fxml
     *             ruta : ruta donde se encuentra el fichero
     *             site : etiqueta donde se colaca la vista en la plantilla
     *         template : plantilla para configurar la vista
     *            modal : si es una vista modal o no
     *AbstractController: controlador de la vista
     *            stage : javafx.stage
     *           bundle : ResourceBundle
     *                 
     *
     */
    public AppHandler(HashMap<String, Object> datos) 
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
        Vars.aw         = createWindow();
    }
    
    @Override
    public void launchWindow() throws IOException  { if (Vars.aw != null) buildAndShowScene(); }

    @Override
    public void closeWindow() throws IOException { if (Vars.aw != null) closeScene(); }

    /**
     * crea la vista del fichero sirviendose de los parámetros configurados
     * en el método launchWindow.
     * @return 
     */
    @Override
    public AbstractWindow createWindow() {
        if (Vars.ac != null) { return new ConfWindows(); }
        else { return null; }
    }
        
    /**
     * Constuye la vista (Scene) y la muestra
     */
    private void buildAndShowScene()
    {
        Vars.pstage.setTitle(Vars.aw.titleBundleKey());
        Vars.pstage.setResizable(Vars.aw.resizable());
        Vars.pstage.setScene(Vars.aw.root());
        Vars.pstage.show();
    }

    private void closeScene() { Vars.pstage.close(); }
    
}