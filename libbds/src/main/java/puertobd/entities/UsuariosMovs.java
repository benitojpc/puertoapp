package puertobd.entities;

import java.sql.Date;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import java.sql.ResultSet;
import java.sql.SQLException;

import puertobd.tools.Utils;

public class UsuariosMovs {

    private Integer id;
    private Integer usuario;
    private String tipo;
    private Date fecha;

    public UsuariosMovs() {

        id      = null;
        usuario = null;
        tipo    = "";
        fecha   = null;

    }

    public UsuariosMovs(Integer id, Integer usuario, String tipo, Date fecha) {

        this.id      = id;
        this.usuario = usuario;
        this.tipo    = tipo;
        this.fecha   = fecha;
        
    }
   
    public UsuariosMovs( ResultSet rs ) 
                throws SQLException {

        id      = rs.getInt("id");
        usuario = rs.getInt("usuario");
        tipo    = rs.getString("tipo");
        fecha   = rs.getDate("fecha");

    }

    public void setId(Integer i) { id = i; }
    public Integer getId() { return id; }

    public void setUsuario(Integer i) { usuario = i; }
    public Integer getUsuario() { return usuario; }

    public void setTipo(String s) { tipo = s; }
    public String getTipo() { return tipo; }

    public void setFecha(Date f) { fecha = f; }
    public Date getFecha() { return fecha; }
    public String getFechaStr() { return Utils.date2Str(fecha); }

    public static LinkedList<String> atributos() {
        return new LinkedList<String>(Arrays.asList("id","usuario", "tipo", "fecha"));
    }

    public static LinkedHashMap<String,String> tipos() {
        LinkedHashMap<String,String> tmp = new LinkedHashMap<>();
        tmp.put("id","int");
        tmp.put("usuario","int");
        tmp.put("tipo", "str");
        tmp.put("fecha","date");
        return tmp;
    }

    public String usuariosmovs2Str(){
        return String.format("%s|%s|%s|%s", 
                                this.id.toString(), 
                                this.usuario.toString(), 
                                this.tipo, 
                                this.getFechaStr());
    }

}
