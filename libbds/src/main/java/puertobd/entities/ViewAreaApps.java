package puertobd.entities;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import java.sql.ResultSet;
import java.sql.SQLException;

import puertobd.tools.Utils;

public class ViewAreaApps {
    
    private Integer     idArea;
    private Integer     idApp;
    private String      area;
    private Timestamp   fecha;
    private String      aplicacion;
    private String      ruta;
    private String      notas;

    public ViewAreaApps() {

        idArea      = null;
        idApp       = null;
        area        = null;
        fecha       = null;
        aplicacion  = "";
        ruta        = "";
        notas       = "";

    }

    public ViewAreaApps(Integer idArea, Integer idApp, String area, Timestamp fecha, String aplicacion, String ruta, String notas) {

        this.idArea     = idArea;
        this.idApp      = idApp;
        this.area       = area;
        this.fecha      = fecha;
        this.aplicacion = aplicacion;
        this.ruta       = ruta;
        this.notas      = notas;

    }

    public ViewAreaApps( ResultSet rs ) 
                throws SQLException {

        idArea      = rs.getInt("areaid");
        idApp       = rs.getInt("appid");
        area        = rs.getString("area");
        fecha       = rs.getTimestamp("fecha");
        aplicacion  = rs.getString("aplicacion");
        ruta        = rs.getString("ruta");
        notas       = rs.getString("notas");

    }

    public void setIdArea(Integer i) { idArea = i; }
    public Integer getIdArea() { return idArea; }

    public void setIdApp(Integer i) { idApp = i; }
    public Integer getIdApp() { return idApp; }

    public void setArea(String i) { area = i; }
    public String getArea() { return area; }

    public void setFecha(Timestamp f) { fecha = f; }
    public Timestamp getFecha() { return fecha; }
    public String getFechaStr() { return Utils.tstamp2Str(fecha); }

    public void setAplicacion(String s) { aplicacion = s; }
    public String getAplicacion() { return aplicacion; }

    public void setRuta(String s) { ruta = s; }
    public String getRuta() { return ruta; }

    public void setNotas(String s) { notas = s; }
    public String getNotas() { return notas; }

    public static LinkedList<String> atributos() {
        return new LinkedList<String>(Arrays.asList("areaid","appid","area", "fecha", "aplicacion", "ruta", "notas"));
    }

    public static LinkedHashMap<String,String> tipos() {
        LinkedHashMap<String,String> tmp = new LinkedHashMap<>();
        tmp.put("areaid","int");
        tmp.put("appid","int");
        tmp.put("area","str");
        tmp.put("fecha","Timestamp");
        tmp.put("aplicacion","str");
        tmp.put("ruta","str");
        tmp.put("notas","str");
        return tmp;
    }

    public String ViewAreaApps2Str(){
        return String.format("%s|%s|%s|%s|%s|%s|%s", 
                                this.idArea.toString(), 
                                this.idApp.toString(), 
                                this.area, 
                                this.getFechaStr(),
                                this.aplicacion,
                                this.ruta,
                                this.notas);
    }

}
