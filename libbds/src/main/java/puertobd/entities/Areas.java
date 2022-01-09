package puertobd.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Areas {

    private Integer id;
    private String area;

    public Areas() {
        id      = null;
        area  = "";
    }

    public Areas( ResultSet rs ) 
                throws SQLException {

        id      = rs.getInt("id");
        area  = rs.getString("area");
    }

    public Areas(Integer id, String area){
        this.id      = id;
        this.area  = area;
    }

    public void setId(Integer i) { id = i;}
    public Integer getId() { return id; }

    public void setArea(String s) {area = s;}
    public String getArea() {return area;}
        
    @Override
    public String toString(){
        return String.format( "%s", this.area );
    }

    public static LinkedList<String> atributos() {
        return new LinkedList<String>(Arrays.asList("id","area"));
    }

    public static LinkedHashMap<String,String> tipos() {
        LinkedHashMap<String,String> tmp = new LinkedHashMap<>();
        tmp.put("id","int");
        tmp.put("area","str");
        return tmp;
    }

    public String app2Str(){
        return String.format("%s|%s", this.id.toString(), this.area);
    }
   
}