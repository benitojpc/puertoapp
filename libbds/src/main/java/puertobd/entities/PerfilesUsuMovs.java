package puertobd.entities;

import java.sql.Date;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import java.sql.ResultSet;
import java.sql.SQLException;

import puertobd.tools.Utils;

public class PerfilesUsuMovs {
    
    private Integer id;
    private Integer perfusu;
    private Date fecha;
    private String tipo;

    public PerfilesUsuMovs() {

        id      = null;
        perfusu  = null;
        fecha   = null;
        tipo    = null;

    }

    public PerfilesUsuMovs(Integer id, Integer perfusu, Date fecha, String tipo) {

        this.id     = id;
        this.perfusu = perfusu;
        this.fecha  = fecha;
        this.tipo   = tipo;

    }

    public PerfilesUsuMovs( ResultSet rs ) 
                throws SQLException {

        id      = rs.getInt("id");
        perfusu = rs.getInt("perfusu");
        fecha   = rs.getDate("fecha");
        tipo    = rs.getString("tipo");

    }

    public void setId(Integer i) { id = i; }
    public Integer getId() { return id; }

    public void setPerfusu(Integer i) { perfusu = i; }
    public Integer getPerfusu() { return perfusu; }

    public void setFecha(Date f) { fecha = f; }
    public Date getFecha() { return fecha; }
    public String getFechaStr() { return Utils.date2Str(fecha); }

    public void setTipo(String s) { tipo = s; }
    public String getTipo() { return tipo; }

    public static LinkedList<String> atributos() {
        return new LinkedList<String>(Arrays.asList("id","perfusu", "fecha", "tipo"));
    }

    public static LinkedHashMap<String,String> tipos() {
        LinkedHashMap<String,String> tmp = new LinkedHashMap<>();
        tmp.put("id","int");
        tmp.put("perfusu","int");
        tmp.put("fecha","date");
        tmp.put("tipo","str");
        return tmp;
    }

    public String perfilesusumovs2Str(){
        return String.format("%s|%s|%s|%s", 
                                this.id.toString(), 
                                this.perfusu.toString(), 
                                this.getFechaStr(),
                                this.tipo);
    }

}
