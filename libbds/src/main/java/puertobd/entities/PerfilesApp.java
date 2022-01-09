package puertobd.entities;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PerfilesApp {
    
    private Integer id;
    private Integer aplicacion;
    private String perfil;
    private String descripcion;
    private Boolean activo;

    public PerfilesApp() {
        
        id          = null;
        aplicacion  = null;
        perfil      = "";
        descripcion = "";
        activo      = false;
    
    }

    public PerfilesApp(Integer id, Integer aplicacion, String perfil, String descripcion, Boolean activo){
        
        this.id         = id;
        this.aplicacion = aplicacion;
        this.perfil     = perfil;
        this.descripcion= descripcion;
        this.activo     = activo;
    
    }

    public PerfilesApp( ResultSet rs ) 
                throws SQLException {

        id          = rs.getInt("id");
        aplicacion  = rs.getInt("aplicacion");
        perfil      = rs.getString("perfil");
        descripcion = rs.getString("descripcion");
        activo      = rs.getBoolean("activo");

    }

    public void setId(Integer i) { id = i;}
    public Integer getId() { return id; }

    public void setAplicacion(Integer i) { aplicacion = i;}
    public Integer getAplicacion() { return aplicacion; }

    public void setPerfil(String s) {perfil = s;}
    public String getPerfil() {return perfil;}
    
    public void setDescripcion(String s) {descripcion = s;}
    public String getDescripcion() {return descripcion;}
    
    public void setActivo(Boolean s) {activo = s;}
    public Boolean isActivo() {return activo;}
    public String esActivo(){ return activo ? "S" : "N";}

    public static LinkedList<String> atributos() {
        return new LinkedList<String>(Arrays.asList("id","aplicacion", "pefil", "descripcion", "activo"));
    }

    public static LinkedHashMap<String,String> tipos() {
        LinkedHashMap<String,String> tmp = new LinkedHashMap<>();
        tmp.put("id","int");
        tmp.put("aplicacion","int");
        tmp.put("perfil","str");
        tmp.put("descripcion","str");
        tmp.put("activo","bool");
        return tmp;
    }

    public String perfilesapp2Str(){
        return String.format("%s|%s|%s|%s|%s", 
                                this.id.toString(), 
                                this.aplicacion.toString(), 
                                this.perfil.toString(), 
                                this.descripcion, 
                                this.esActivo());
    }
 
}