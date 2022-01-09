module libjfx 
{
    
    exports jfxlib.win;
    exports jfxlib.win.abstracts;
    exports jfxlib.win.interfaces;

    requires java.logging;
    requires javafx.controls;   
    requires javafx.fxml;
    requires transitive javafx.graphics;

    uses jfxlib.win.AppHandler;
    uses jfxlib.win.ConfWindows;
    uses jfxlib.win.ModalWindow;
    uses jfxlib.win.abstracts.AbstractController;
    uses jfxlib.win.interfaces.ViewHandler;

}