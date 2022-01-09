package puertobd.entities;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PerfilesUsu {
    
    private Integer id;
    private Integer usuario;
    private Integer perfil;
    private Boolean activo; 

    public PerfilesUsu() {

        this.id     = null;
        this.usuario= null;
        this.perfil = null;
        this.activo = false;

    }

    public PerfilesUsu(Integer id, Integer usuario, Integer perfil, Boolean activo) {

        this.id     = id;
        this.usuario= usuario;
        this.perfil = perfil;
        this.activo = activo;
        
    }

    public PerfilesUsu( ResultSet rs ) 
                throws SQLException {

        id      = rs.getInt("id");
        usuario = rs.getInt("usuario");
        perfil  = rs.getInt("perfil");
        activo  = rs.getBoolean("activo");

    }

    public void setId(Integer i) { id = i;}
    public Integer getId() { return id; }

    public void setUsuario(Integer i) { usuario = i;}
    public Integer getUsuario() { return usuario; }

    public void setPerfil(Integer i) {perfil = i;}
    public Integer getPerfil() {return perfil;}
     
    public void setActivo(Boolean s) {activo = s;}
    public Boolean isActivo() {return activo;}
    public String esActivo(){ return activo ? "S" : "N";}

    public static LinkedList<String> atributos() {
        return new LinkedList<String>(Arrays.asList("id","usuario", "perfil", "activo"));
    }

    public static LinkedHashMap<String,String> tipos() {
        LinkedHashMap<String,String> tmp = new LinkedHashMap<>();
        tmp.put("id","int");
        tmp.put("usuario","int");
        tmp.put("perfil","int");
        tmp.put("activo","bool");
        return tmp;
    }

    public String perfilesusu2Str(){
        return String.format("%s|%s|%s|%s", 
                                this.id.toString(), 
                                this.usuario.toString(), 
                                this.perfil.toString(), 
                                this.esActivo());
    }

}
