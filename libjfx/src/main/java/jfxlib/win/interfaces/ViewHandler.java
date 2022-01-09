package jfxlib.win.interfaces; 

import java.io.IOException;

public interface ViewHandler {
    void launchWindow() throws IOException;   
    void closeWindow() throws IOException;
}