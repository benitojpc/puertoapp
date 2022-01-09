package puertobd.entities;

import java.sql.Date;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import java.sql.ResultSet;
import java.sql.SQLException;

import puertobd.tools.Utils;

public class PuestosUsu {
    
    private Integer id;
    private Integer usuario;
    private Integer puesto;
    private Date fechaa;
    private Date fechab;

    public PuestosUsu() {

        id      = null;
        usuario = null;
        puesto  = null;
        fechaa  = null;
        fechab  = null;

    }

    public PuestosUsu(Integer id, Integer usuario, Integer puesto, Date fa, Date fb) {

        this.id      = id;
        this.usuario = usuario;
        this.puesto   = puesto;
        this.fechaa  = fa;
        this.fechab  = fb;

    }

    public PuestosUsu( ResultSet rs ) 
                throws SQLException {

        id      = rs.getInt("id");
        usuario = rs.getInt("usuario");
        puesto  = rs.getInt("puesto");
        fechaa  = rs.getDate("fechaa");
        fechab  = rs.getDate("fechab");

    }

    public void setId(Integer i) { id = i; }
    public Integer getId() { return id; }

    public void setUsuario(Integer i) { usuario = i; }
    public Integer getUsuario() { return usuario; }

    public void setPuesto(Integer i) { puesto = i; }
    public Integer getPuesto() { return puesto; }

    public void setFechaA(Date f) { fechaa = f; }
    public Date getFechaA() { return fechaa; }
    public String getFechaAStr() { return Utils.date2Str(fechaa); }

    public void setFechaB(Date f) { fechaa = f; }
    public Date getFechaB() { return fechaa; }
    public String getFechaBStr() { return Utils.date2Str(fechab); }

    public static LinkedList<String> atributos() {
        return new LinkedList<String>(Arrays.asList("id","usuario", "puesto", "fechaa", "fechab"));
    }

    public static LinkedHashMap<String,String> tipos() {
        LinkedHashMap<String,String> tmp = new LinkedHashMap<>();
        tmp.put("id","int");
        tmp.put("usuario","int");
        tmp.put("puesto","int");
        tmp.put("fechaa","date");
        tmp.put("fechab","date");
        return tmp;
    }

    public String puestosusu2Str(){
        return String.format("%s|%s|%s|%s|%s", 
                                this.id.toString(), 
                                this.usuario.toString(), 
                                this.puesto.toString(), 
                                this.getFechaAStr(), 
                                this.getFechaBStr());
    }

}
