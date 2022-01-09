package puertobd.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Aplicaciones {

    private Integer id;
    private String nombre;
    private String servidor;
    private String notas;
    private Boolean activa;

    public Aplicaciones() {
        id      = null;
        nombre  = "";
        servidor= "";
        notas   = "";
        activa  = false;
    }

    public Aplicaciones( ResultSet rs ) 
                throws SQLException {

        id      = rs.getInt("id");
        nombre  = rs.getString("nombre");
        servidor= rs.getString("servidor");
        notas   = rs.getString("notas");
        activa  = rs.getBoolean("activo");

    }

    public Aplicaciones(Integer id, String nombre, String servidor, String notas, Boolean activa){
        this.id      = id;
        this.nombre  = nombre;
        this.servidor= servidor;
        this.notas   = notas;
        this.activa  = activa;
    }

    public void setId(Integer i) { id = i;}
    public Integer getId() { return id; }

    public void setNombre(String s) {nombre = s;}
    public String getNombre() {return nombre;}
    
    public void setServidor(String s) {servidor = s;}
    public String getServidor() {return servidor;}
    
    public void setNotas(String s) {notas = s;}
    public String getNotas() {return notas;}

    public void setActiva(Boolean s) {activa = s;}
    public Boolean isActiva() {return activa;}
    public String esActiva(){ return activa ? "S" : "N";}

    public String toString(){
        return nombre;
    }

    public static LinkedList<String> atributos() {
        return new LinkedList<String>(Arrays.asList("id","nombre","servidor","notas","activa"));
    }

    public static LinkedHashMap<String,String> tipos() {
        LinkedHashMap<String,String> tmp = new LinkedHashMap<>();
        tmp.put("id","int");
        tmp.put("nombre","str");
        tmp.put("servidor","str");
        tmp.put("notas","str");
        tmp.put("activa","bool");
        return tmp;
    }

    public String app2Str(){
        return String.format("%s|%s|%s|%s|%s", 
                                this.id.toString(), 
                                this.nombre, 
                                this.servidor,
                                this.notas,
                                esActiva());
    }
   
}