package puertoapp.app.models;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import puertoapp.app.tools.Vars;
import puertobd.entities.ViewAreaApps;

public class VAreaAppsModel 
{

    private IntegerProperty idArea;
    private IntegerProperty idApp;
    private StringProperty  area;
    private StringProperty  app;
    private StringProperty  ruta;
    private StringProperty  notas;
    private ObjectProperty<LocalDate>  fecha ; //clientDate = new SimpleObjectProperty<>(this, "clientDate");

    private String fmtf = "dd/MM/yyyy";

    public VAreaAppsModel ()
    {
        idArea  = new SimpleIntegerProperty(this, "idArea", 0 );
        idApp   = new SimpleIntegerProperty(this, "idApp", 0 );
        area    = new SimpleStringProperty(this, "area", "" );
        app     = new SimpleStringProperty(this, "app", "" );    
        ruta    = new SimpleStringProperty(this, "ruta", "" );
        notas   = new SimpleStringProperty(this, "notas", "" );
        fecha   = new SimpleObjectProperty<>(this, "fecha", Vars.fechaHoy() );
    }

    public VAreaAppsModel ( ViewAreaApps vaa)
    {
        idArea  = new SimpleIntegerProperty(this, "idArea", vaa.getIdArea() );
        idApp   = new SimpleIntegerProperty(this, "idApp", vaa.getIdApp()  );
        area    = new SimpleStringProperty(this, "area", vaa.getArea()  );
        app     = new SimpleStringProperty(this, "app", vaa.getAplicacion() );    
        ruta    = new SimpleStringProperty(this, "ruta", vaa.getRuta()  );
        notas   = new SimpleStringProperty(this, "notas", vaa.getNotas()  );
        fecha   = new SimpleObjectProperty<>(this, "fecha", Vars.time2LocalDate( vaa.getFecha() ) );
    }

    public IntegerProperty idAreaProperty() { return idArea; }
    public Integer getIdAreaInt() { return idArea.getValue(); }
    public void setIdArea( Integer i ) { idArea.setValue( i ); }
    public String getIdAreaStr() { return idArea.getValue().toString(); }

    public IntegerProperty idAppProperty() { return idApp; }
    public Integer getIdAppInt() { return idApp.getValue(); }
    public void setIdApp( Integer i ) { idApp.setValue( i ); }
    public String getIdAppStr() { return idApp.getValue().toString(); }

    public StringProperty areaProperty() { return area; }
    public void setArea( String s ) { area.setValue( s ); }
    public String getArea() { return area.getValue(); }

    public StringProperty appProperty() { return app; }
    public void setApp( String s ) { app.setValue( s ); }
    public String getApp() { return app.getValue(); }

    public StringProperty rutaProperty() { return ruta; }
    public void setRuta( String s ) { ruta.setValue( s ); }
    public String getRuta() { return ruta.getValue(); }

    public StringProperty notasProperty() { return notas; }
    public void setNotas( String s ) { notas.setValue( s ); }
    public String getNotas() { return notas.getValue(); }

    public ObjectProperty<LocalDate> fechaProperty() { return fecha; }
    public final LocalDate getFecha() { return fecha.get(); }
    public final void setFecha(LocalDate date) { new SimpleObjectProperty<>(date); }
    public String getFechaStr() 
    {
        return Vars.fecha2Str( fecha.get(), fmtf );
    }
    
    public void mostrar() {
        
        System.out.println(String.format("%1$10s: %2$-5d", "IdArea#",    getIdAreaInt()));
        System.out.println(String.format("%1$10s: %2$-5d", "IdApp#",     getIdAppInt()));
        System.out.println(String.format("%1$10s: %2$s",   "Área",       area.get()));
        System.out.println(String.format("%1$10s: %2$s",   "Áplicación", app.get()));
        System.out.println(String.format("%1$10s: %2$s",   "Ruta",       ruta.get()));
        System.out.println(String.format("%1$10s: %2$s",   "Notas",      notas.get()));
        System.out.println(String.format("%1$10s: %2$s",   "Fecha",      Vars.fechaCompleta( getFecha() )));

    }
}
