package puertobd.entities;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuarios {

    private Integer id;
    private String carnet;
    private String dnie;
    private String ape1;
    private String ape2;
    private String nombre;
    private String login;
    private Boolean situacion;

    public Usuarios() {
        id          = null;
        carnet      = "";
        dnie        = "";
        ape1        = "";
        ape2        = "";
        nombre      = "";
        login       = "";
        situacion   = false;
    }

    public Usuarios(Integer id, String carnet, String dnie, 
                    String ape1, String ape2, String nombre, 
                    String login, Boolean situacion){
                        
        this.id         = id;
        this.carnet     = carnet;
        this.dnie       = dnie;
        this.ape1       = ape1;
        this.ape2       = ape2;
        this.nombre     = nombre;
        this.login      = login;
        this.situacion  = situacion;
    }

    public Usuarios( ResultSet rs ) 
                throws SQLException {

        id          = rs.getInt("id");
        carnet      = rs.getString("carnet");
        dnie        = rs.getString("dnie");
        ape1        = rs.getString("ape1");
        ape2        = rs.getString("ape2");
        nombre      = rs.getString("nomb");
        login       = rs.getString("login");
        situacion   = rs.getBoolean("situacion");

    }

    public void setId(Integer i) { id = i;}
    public Integer getId() { return id; }

    public void setCarnet(String s) {
        carnet = s;
    }

    public String getCarnet() {return carnet;}

    public void setDnie(String s) {dnie = s;}
    public String getDnie() {return dnie;}
    
    public void setApe1(String s) {ape1 = s;}
    public String getApe1() {return ape1;}
    
    public void setApe2(String s) {ape2 = s;}
    public String getApe2() {return ape2;}
    
    public void setNombre(String s) {nombre = s;}
    public String getNombre() {return nombre;}
    
    public void setlogin(String s) {login = s;}
    public String getlogin() {return login;}
    
    public void setSituacion(Boolean s) {situacion = s;}
    public Boolean isSituacion() {return situacion;}
    public String esSituacion(){ return situacion ? "S" : "N";}

    public String toString(){
        return nombre;
    }

    public static LinkedList<String> atributos() {
        return new LinkedList<String>(Arrays.asList("id","carnet","dnie","ape1","ape2",
                                                    "nombre","login","situacion"));
    }

    public static LinkedHashMap<String,String> tipos() {
        LinkedHashMap<String,String> tmp = new LinkedHashMap<>();
        tmp.put("id","int");
        tmp.put("carnet","str");
        tmp.put("dnie","str");
        tmp.put("ape1","str");
        tmp.put("ape2","str");
        tmp.put("nombre","str");
        tmp.put("login","str");
        tmp.put("situacion","bool");
        return tmp;
    }

    public String usuario2Str(){
        return String.format("%s|%s|%s|%s|%s|%s|%s|%s", 
                                this.id.toString(), 
                                this.carnet, 
                                this.dnie, 
                                this.ape1, 
                                this.ape2, 
                                this.nombre, 
                                this.login,
                                esSituacion());
    }
    
}