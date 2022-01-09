package puertobd.entities;

import java.sql.Date;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import java.sql.ResultSet;
import java.sql.SQLException;

import puertobd.tools.Utils;

public class AplicacionesUsu {
    
    private Integer id;
    private Integer usuario;
    private Integer aplicacion;
    private String login;
    private Boolean activa;

    public AplicacionesUsu() {

        id          = null;
        usuario     = null;
        aplicacion  = null;
        login       = null;
        activa      = null;

    }

    public AplicacionesUsu(Integer id, Integer usuario, Integer aplicacion, String login, Boolean activa) {

        this.id         = id;
        this.usuario    = usuario;
        this.aplicacion = aplicacion;
        this.login      = login;
        this.activa     = activa;

    }

    public AplicacionesUsu( ResultSet rs ) 
                throws SQLException {

        id         = rs.getInt("id");
        usuario    = rs.getInt("usuario");
        aplicacion = rs.getInt("aplicacion");
        login      = rs.getString("login");
        activa     = rs.getBoolean("activa");

    }

    public void setId(Integer i) { id = i; }
    public Integer getId() { return id; }

    public void setUsuario(Integer i) { usuario = i; }
    public Integer getUsuario() { return usuario; }

    public void setAplicacion(Integer i) { aplicacion = i; }
    public Integer getAplicacion() { return aplicacion; }

    public void setLogin(String s) { login = s; }
    public String getLogin() { return login; }

    public void setActiva(Boolean b) { activa = b; }
    public Boolean getActiva() { return activa; }
    public String getActivaStr() { return Utils.bool2Str(activa); }

    public static LinkedList<String> atributos() {
        return new LinkedList<String>(Arrays.asList("id","usuario","aplicacion","login","activa"));
    }

    public static LinkedHashMap<String,String> tipos() {
        LinkedHashMap<String,String> tmp = new LinkedHashMap<>();
        tmp.put("id","int");
        tmp.put("usuario","int");
        tmp.put("aplicacion","int");
        tmp.put("login","str");
        tmp.put("activa","bool");
        return tmp;
    }

    public String appusu2Str(){
        return String.format("%s|%s|%s|%s", 
                                this.id.toString(), 
                                this.usuario.toString(), 
                                this.aplicacion.toString(), 
                                this.getActivaStr());
    }
 
}
