package puertoapp.app.models;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import puertoapp.app.tools.Vars;
import puertobd.entities.Areas;
import puertobd.entities.ViewAreaApps;

public class AreaModel 
{

    private IntegerProperty idArea;
    private StringProperty  area;
    
    public AreaModel ()
    {
        idArea  = new SimpleIntegerProperty(this, "idArea", 0 );
        area    = new SimpleStringProperty(this, "area", "" );
    }

    public AreaModel ( Areas a)
    {
        idArea  = new SimpleIntegerProperty(this, "idArea", a.getId() );
        area    = new SimpleStringProperty(this, "area", a.getArea()  );
    }

    public IntegerProperty idAreaProperty() { return idArea; }
    public Integer getIdAreaInt() { return idArea.getValue(); }
    public void setIdArea( Integer i ) { idArea.setValue( i ); }
    public String getIdAreaStr() { return idArea.getValue().toString(); }

    public StringProperty areaProperty() { return area; }
    public void setArea( String s ) { area.setValue( s ); }

    public void mostrar() {
        
        System.out.println(String.format("%1$10s: %2$-5d", "IdArea#",    getIdAreaInt()));
        System.out.println(String.format("%1$10s: %2$s",   "Área",       area.get()));
    
    }

    @Override
    public String toString() 
    {
        return String.format( "%s", area.get() );
    }
}
