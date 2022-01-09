package puertobd.entities;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import java.sql.ResultSet;
import java.sql.SQLException;

import puertobd.tools.Utils;

public class AreaApps {
    
    private Integer     id;
    private Integer     area;
    private Timestamp   fecha;
    private String      aplicacion;
    private String      ruta;
    private String      notas;

    public AreaApps() {

        id          = null;
        area        = null;
        fecha       = null;
        aplicacion  = "";
        ruta        = "";
        notas       = "";

    }

    public AreaApps(Integer id, Integer area, Timestamp fecha, String aplicacion, String ruta, String notas) {

        this.id         = id;
        this.area       = area;
        this.fecha      = fecha;
        this.aplicacion = aplicacion;
        this.ruta       = ruta;
        this.notas      = notas;

    }

    public AreaApps( ResultSet rs ) 
                throws SQLException {

        id          = rs.getInt("id");
        area        = rs.getInt("area_id");
        fecha       = rs.getTimestamp("fecha");
        aplicacion  = rs.getString("aplicacion");
        ruta        = rs.getString("ruta");
        notas       = rs.getString("notas");

    }

    public void setId(Integer i) { id = i; }
    public Integer getId() { return id; }

    public void setArea(Integer i) { area = i; }
    public Integer getArea() { return area; }

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
        return new LinkedList<String>(Arrays.asList("id","area_id", "fecha", "aplicacion", "ruta", "notas"));
    }

    public static LinkedHashMap<String,String> tipos() {
        LinkedHashMap<String,String> tmp = new LinkedHashMap<>();
        tmp.put("id","int");
        tmp.put("area_id","int");
        tmp.put("fecha","Timestamp");
        tmp.put("aplicacion","str");
        tmp.put("ruta","str");
        tmp.put("notas","str");
        return tmp;
    }

    public String areaApps2Str(){
        return String.format("%s|%s|%s|%s|%s|%s", 
                                this.id.toString(), 
                                this.area.toString(), 
                                this.getFechaStr(),
                                this.aplicacion,
                                this.ruta,
                                this.notas);
    }

}
