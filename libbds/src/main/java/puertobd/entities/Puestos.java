package puertobd.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Puestos {

    private Integer id;
    private String puesto;
    private Boolean activo;

    public Puestos() {
        id      = null;
        puesto   = "";
        activo  = false;
    }

    public Puestos(Integer id, String puesto, Boolean activo){
        this.id      = id;
        this.puesto  = puesto;
        this.activo  = activo;
    }

    public Puestos( ResultSet rs ) 
        throws SQLException {

        id      = rs.getInt("id"); 
        puesto  = rs.getString("puesto");
        activo  = rs.getBoolean("activo");

    }

    public void setId(Integer i) { id = i;}
    public Integer getId() { return id; }

    public void setPuesto(String s) {puesto = s;}
    public String getPuesto() {return puesto;}
    
    public void setActivo(Boolean s) {activo = s;}
    public Boolean isActivo() {return activo;}
    public String esActivo(){ return activo ? "S" : "N";}

    public String toString(){
        return puesto;
    }

    public static LinkedList<String> atributos() {
        return new LinkedList<String>(Arrays.asList("id","puesto","activo"));
    }

    public static LinkedHashMap<String,String> tipos() {
        LinkedHashMap<String,String> tmp = new LinkedHashMap<>();
        tmp.put("id","int");
        tmp.put("puesto","str");
        tmp.put("activo","bool");
        return tmp;
    }

    public String puesto2Str(){
        return String.format("%s|%s|%s", 
                                this.id.toString(), 
                                this.puesto, 
                                esActivo());
    }
    
}