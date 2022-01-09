package puertobd.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Grupos {

    private Integer id;
    private String grupo;
    private Boolean activo;

    public Grupos() {
        id      = null;
        grupo   = "";
        activo  = false;
    }

    public Grupos( ResultSet rs ) 
                throws SQLException {

        id      = rs.getInt("id");
        grupo   = rs.getString("grupo");
        activo  = rs.getBoolean("activo");

    }

    public Grupos(Integer id, String grupo, Boolean activo){
        this.id      = id;
        this.grupo  = grupo;
        this.activo  = activo;
    }

    public void setId(Integer i) { id = i;}
    public Integer getId() { return id; }

    public void setGrupo(String s) {grupo = s;}
    public String getGrupo() {return grupo;}
    
    public void setActivo(Boolean s) {activo = s;}
    public Boolean isActivo() {return activo;}
    public String esActivo(){ return activo ? "S" : "N";}

    public String toString(){
        return grupo;
    }

    public static LinkedList<String> atributos() {
        return new LinkedList<String>(Arrays.asList("id","grupo","activo"));
    }

    public static LinkedHashMap<String,String> tipos() {
        LinkedHashMap<String,String> tmp = new LinkedHashMap<>();
        tmp.put("id","int");
        tmp.put("grupo","str");
        tmp.put("activo","bool");
        return tmp;
    }

    public String grupo2Str(){
        return String.format("%s|%s|%s", 
                                this.id.toString(), 
                                this.grupo, 
                                esActivo());
    }
    
}