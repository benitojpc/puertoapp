package puertobd.entities;

import java.sql.Date;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import java.sql.ResultSet;
import java.sql.SQLException;

import puertobd.tools.Utils;

public class GruposUsu {
    
    private Integer id;
    private Integer usuario;
    private Integer grupo;
    private Date fechaa;
    private Date fechab;

    public GruposUsu() {

        id      = null;
        usuario = null;
        grupo   = null;
        fechaa  = null;
        fechab  = null;

    }

    public GruposUsu(Integer id, Integer usuario, Integer grupo, Date fa, Date fb) {

        this.id      = id;
        this.usuario = usuario;
        this.grupo   = grupo;
        this.fechaa  = fa;
        this.fechab  = fb;

    }

    public GruposUsu( ResultSet rs ) 
                throws SQLException {

        id      = rs.getInt("id");
        usuario = rs.getInt("usuario");
        grupo   = rs.getInt("grupo");
        fechaa  = rs.getDate("fechaa");
        fechab  = rs.getDate("fechab");

    }

    public void setId(Integer i) { id = i; }
    public Integer getId() { return id; }

    public void setUsuario(Integer i) { usuario = i; }
    public Integer getUsuario() { return usuario; }

    public void setGrupo(Integer i) { grupo = i; }
    public Integer getGrupo() { return grupo; }

    public void setFechaA(Date f) { fechaa = f; }
    public Date getFechaA() { return fechaa; }
    public String getFechaAStr() { return Utils.date2Str(fechaa); }

    public void setFechaB(Date f) { fechaa = f; }
    public Date getFechaB() { return fechaa; }
    public String getFechaBStr() { return Utils.date2Str(fechab); }

    public static LinkedList<String> atributos() {
        return new LinkedList<String>(Arrays.asList("id","usuario", "grupo", "fechaa", "fechab"));
    }

    public static LinkedHashMap<String,String> tipos() {
        LinkedHashMap<String,String> tmp = new LinkedHashMap<>();
        tmp.put("id","int");
        tmp.put("usuario","int");
        tmp.put("grupo","int");
        tmp.put("fechaa","date");
        tmp.put("fechab","date");
        return tmp;
    }

    public String gruposusu2Str(){
        return String.format("%s|%s|%s|%s|%s", 
                                this.id.toString(), 
                                this.usuario.toString(), 
                                this.grupo.toString(), 
                                this.getFechaAStr(), 
                                this.getFechaBStr());
    }
 
}
