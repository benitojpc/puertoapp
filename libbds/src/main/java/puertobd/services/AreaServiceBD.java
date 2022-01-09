package puertobd.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import puertobd.tools.Conectar;
import puertobd.entities.Areas;

public class AreaServiceBD 
        implements puertobd.services.interfaces.AreaIface {
    
    private final String TABLA = "Areas a";
    private Connection conn = null;
    private LinkedHashMap<String,Object> datos = new LinkedHashMap<>();
    private LinkedList<String> areas;
    private LinkedList<String> atributos;
    protected Areas a = null;

    public AreaServiceBD() {

        conn = new Conectar().leerConexion();
        atributos = Areas.atributos();

    }

    public Areas encontrar( Integer id ) 
    {
        return buscar( id );
    }

    public Boolean existe(Integer id) {
        return (buscar(id) != null);
    }

    public Boolean existe(String s) {
        
        var qry = String.format("SELECT * FROM %s WHERE %s", TABLA, s);
        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(qry);

            return rs.next();

        } catch (SQLException e) {}
        return false;
        
    }

    public LinkedList<String> areas( String orden ) {

        String orderBy = orden.isEmpty() ? "" : String.format("order by %s", orden);
        String qry = String.format( "SELECT * from %s %s",TABLA, orderBy );

        try {

            ResultSet rs = leerDatos( qry );
            areas = new LinkedList<>();
        
            while ( rs.next() ) { areas.add( new Areas( rs ).app2Str() ); }

        } catch (SQLException sqle) {System.out.println( "Error al recuperar la tabla " ); }

        return areas;
        
    }

    public LinkedList<Areas> datos() 
    {
        String orderBy = "";
        String qry = String.format( "SELECT * from %s %s",TABLA, orderBy );
        LinkedList<Areas> lst_areas = new LinkedList<>();

        try {

            ResultSet rs = leerDatos( qry );
            while ( rs.next() ) { lst_areas.add( new Areas( rs ) ); }

        } catch (SQLException sqle) {System.out.println( "Error al recuperar la tabla " ); }

        return lst_areas;
    }

    private ResultSet leerDatos ( String qry ) 
                throws SQLException {
        
        return conn.createStatement().executeQuery( qry );

    }

    // borrar registro no elimina el registro, sólo marca activo como falso.
    public Integer borrarRegistro(Integer id) {
        
        int rows = 0;
        String qry = "UPDATE public.areas " +
                        "set activo = ? " +
                        "WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setBoolean(1, false);
            pstmt.setInt(2, id);

            rows = pstmt.executeUpdate();


        } catch (SQLException e) {}

        return rows;

    }

    public Integer crearRegistro(Areas a) {
        return grabar(a);
    }

    public Integer crearRegistro(HashMap<String,Object> datos) {
        Areas ar = hmap2Object(datos);
        return grabar(ar);
    }

    public HashMap<String,Object> actualizarRegistro(Areas a) {
        return actualizar(a);
    }

    public HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos) {
        Areas ar = hmap2Object(datos);
        return actualizar(ar);
    }

    public void cargarDatos(List<Object> datos){

        List<Areas> apps = new ArrayList<>();
        
        datos.forEach( (Object o) -> {
            
            a = new Areas();
            HashMap<String,Object> app = (HashMap<String,Object>)o;

            app.forEach((key, value) -> {

                switch (key.toLowerCase()){
                    
                    case "id": 
                        a.setId((Integer)value);
                        break;
                    
                    case "area": 
                        a.setArea((String)value);
                        break;
                    
                    default: 
                        break;

                }

            });

            apps.add(a);

        });

        for (Areas ap : apps){

            crearRegistro(ap);
            
        }

    }

    private Integer grabar(Areas a) {
        
        String qry = "";
        Boolean con_id = Boolean.FALSE;
        
        int pkey = 0;
        
        if ( a.getId() == null ) 
        {
            qry = "INSERT INTO public.areas (area) VALUES (?) RETURNING id ";
        } else 
        {
            qry = "INSERT INTO public.areas (id,area) VALUES (?,?) RETURNING id ";
            con_id = Boolean.TRUE;
        }

        try {

            int ind = 1;

            PreparedStatement pstmt = conn.prepareStatement(qry);
            if (con_id) {
                pstmt.setInt(ind++, a.getId());
            }
            pstmt.setString(ind++, a.getArea());

            pstmt.execute();
            ResultSet last_updated = pstmt.getResultSet();
            last_updated.next();
            pkey = last_updated.getInt(1);

        } catch (SQLException e) {
            System.out.println( String.format( "[-] Error: %s", e.getMessage() ));
            return 0;
        }
        return pkey;

    }

    private HashMap<String, Object> actualizar(Areas a) {

        HashMap<String,Object> hm_datos = new HashMap<>();
        String qry = "UPDATE public.areas " +
                        "set area = ? " +
                        "WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setString(1, a.getArea());
            pstmt.setInt(2, a.getId());

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                hm_datos.put("id", a.getId());
                hm_datos.put("area", a.getArea());
            } else {
                Areas ap = buscar(a.getId());
                if (ap != null) {
                    hm_datos.put("id", ap.getId());
                    hm_datos.put("area", ap.getArea());
                }
            }   

        } catch (SQLException e) {}

        return hm_datos;

    }

    private Areas buscar(Integer id) {

        Areas app = null;
        String qry = String.format("SELECT * FROM %s WHERE id = %d", TABLA, id);

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(qry);

            while (rs.next()) {
                app = new Areas(rs.getInt("id"),
                                        rs.getString("area"));
            }

        } catch (SQLException e) {}

        return app;

    }

    private Areas hmap2Object(HashMap<String,Object> datos) {
        
        Areas a = new Areas();
        if ( datos.get("id") != null )
        {
            a.setId( (Integer)datos.get("id") );
        }
        a.setArea((String)datos.get("area"));
        return a;        

    }

    private HashMap<String,Object> object2HMap(Areas a) {
        
        HashMap<String,Object> hm_datos = new HashMap<>();

        hm_datos.put("id", a.getId());
        hm_datos.put("area", a.getArea());
        return hm_datos;

    }

    @Override
    public String buscar(Object o) {
        
        String qry = "";
        try {

            if (o instanceof Integer) {
                // buscar por Id
                qry = String.format("SELECT * FROM %s WHERE id = %d", TABLA, (Integer)o);
            } else if (o instanceof String) {
                qry = String.format("SELECT * FROM %s WHERE lower(area) = %c", TABLA, ((String)o).toLowerCase());
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(qry);

            Areas app = null;
            while (rs.next()) {

                app = new Areas(rs.getInt("id"),
                                    rs.getString("area"));

            }

            return app.app2Str();

        } catch (SQLException e) {

            String msg = String.format("Error recuperando %s", TABLA);
            System.out.println(String.format("%s%nError: %s", msg, e.getMessage()));

        }
        
        return null;

    }

    @Override
    public LinkedHashMap<String,String>  tipos() {
        
        return Areas.tipos();
        
    }

}
