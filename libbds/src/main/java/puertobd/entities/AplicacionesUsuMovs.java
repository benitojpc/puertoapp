package puertobd.entities;

import java.sql.Date;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import java.sql.ResultSet;
import java.sql.SQLException;

import puertobd.tools.Utils;

public class AplicacionesUsuMovs {
    
    private Integer id;
    private Integer appusu;
    private Date fecha;
    private String tipo;

    public AplicacionesUsuMovs() {

        id      = null;
        appusu  = null;
        fecha   = null;
        tipo    = null;

    }

    public AplicacionesUsuMovs(Integer id, Integer appusu, Date fecha, String tipo) {

        this.id     = id;
        this.appusu = appusu;
        this.fecha  = fecha;
        this.tipo   = tipo;

    }

    public AplicacionesUsuMovs( ResultSet rs ) 
                throws SQLException {

        id      = rs.getInt("id");
        appusu  = rs.getInt("appusu");
        fecha   = rs.getDate("fecha");
        tipo    = rs.getString("tipo");
        
    }

    public void setId(Integer i) { id = i; }
    public Integer getId() { return id; }

    public void setAppUsu(Integer i) { appusu = i; }
    public Integer getAppUsu() { return appusu; }

    public void setFecha(Date f) { fecha = f; }
    public Date getFecha() { return fecha; }
    public String getFechaStr() { return Utils.date2Str(fecha); }

    public void setTipo(String s) { tipo = s; }
    public String getTipo() { return tipo; }

    public static LinkedList<String> atributos() {
        return new LinkedList<String>(Arrays.asList("id","appusu","fecha","tipo"));
    }

    public static LinkedHashMap<String,String> tipos() {
        LinkedHashMap<String,String> tmp = new LinkedHashMap<>();
        tmp.put("id","int");
        tmp.put("appusu","int");
        tmp.put("fecha","date");
        tmp.put("tipo","str");
        return tmp;
    }

    public String appusumov2Str(){
        return String.format("%s|%s|%s|%s", 
                                this.id.toString(), 
                                this.appusu.toString(), 
                                this.getFechaStr(), 
                                this.tipo);
    }
 
}
