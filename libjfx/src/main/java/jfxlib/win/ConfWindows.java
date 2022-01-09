package jfxlib.win; 

import jfxlib.win.abstracts.AbstractWindow;
import jfxlib.tools.Vars;

public class ConfWindows 
    extends AbstractWindow {

    public ConfWindows() { super(); }

    @Override
    public String iconFileName() { return ""; } //rb.getString(confFxml + ".icon"); 

    @Override
    public String fxmlFileName() { return ""; } //rb.getString(confFxml + ".fxml"); 

    @Override
    public String titleBundleKey() { return ""; } //rb.getString(confFxml + ".title");

    @Override
    public Boolean esModal() { return Vars.modal; }
    
}