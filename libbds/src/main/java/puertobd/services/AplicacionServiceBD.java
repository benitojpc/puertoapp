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
import puertobd.entities.Aplicaciones;

public class AplicacionServiceBD 
        implements puertobd.services.interfaces.AplicacionIface {
    
    private final String TABLA = "aplicaciones a";
    private Connection conn = null;
    private LinkedHashMap<String,Object> datos = new LinkedHashMap<>();
    private LinkedList<String> aplicaciones;
    private LinkedList<String> atributos;
    protected Aplicaciones a = null;

    public AplicacionServiceBD() {

        conn = new Conectar().leerConexion();
        atributos = Aplicaciones.atributos();

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

    public LinkedList<String> aplicaciones( String orden ) {

        String orderBy = orden.isEmpty() ? "" : String.format("order by %s", orden);
        String qry = String.format( "SELECT * from %s %s",TABLA, orderBy );

        try {

            ResultSet rs = leerDatos( qry );
            aplicaciones = new LinkedList<>();
        
            while ( rs.next() ) { aplicaciones.add( new Aplicaciones( rs ).app2Str() ); }

        } catch (SQLException sqle) {System.out.println( "Error al recuperar la tabla " ); }

        return aplicaciones;
        
    }

    private ResultSet leerDatos ( String qry ) 
                throws SQLException {
        
        return conn.createStatement().executeQuery( qry );

    }

    // borrar registro no elimina el registro, sólo marca activo como falso.
    public Integer borrarRegistro(Integer id) {
        
        int rows = 0;
        String qry = "UPDATE public.aplicaciones " +
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

    public Integer crearRegistro(Aplicaciones a) {
        return grabar(a);
    }

    public Integer crearRegistro(HashMap<String,Object> datos) {
        Aplicaciones a = hmap2Object(datos);
        return grabar(a);
    }

    public HashMap<String,Object> actualizarRegistro(Aplicaciones a) {
        return actualizar(a);
    }

    public HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos) {
        Aplicaciones a = hmap2Object(datos);
        return actualizar(a);
    }

    public void cargarDatos(List<Object> datos){

        List<Aplicaciones> apps = new ArrayList<>();
        
        datos.forEach( (Object o) -> {
            
            a = new Aplicaciones();
            HashMap<String,Object> app = (HashMap<String,Object>)o;

            app.forEach((key, value) -> {

                switch (key.toLowerCase()){
                    
                    case "id": 
                        a.setId((Integer)value);
                        break;
                    
                    case "nombre": 
                        a.setNombre((String)value);
                        break;
                    
                    case "servidor": 
                        a.setServidor((String)value);
                        break;
                    
                    case "notas": 
                        a.setNotas((String)value);
                        break;

                    default: 
                        break;

                }

            });

            a.setActiva(Boolean.TRUE);
            apps.add(a);

        });

        for (Aplicaciones ap : apps){

            crearRegistro(ap);
            
        }

    }

    private Integer grabar(Aplicaciones a) {
        
        String qry = "";
        Boolean con_id = Boolean.FALSE;
        int pkey = 0;
        if ((a.getId() != null) || (a.getId() > 0)) {
            qry = "INSERT INTO public.aplicaciones (id,nombre,servidor,notas,activo) VALUES (?,?,?,?,?) RETURNING id ";
            con_id = Boolean.TRUE;
        } else {
            qry = "INSERT INTO public.aplicaciones (nombre,servidor,notas,activo) VALUES (?,?,?,?) RETURNING id ";
        }

        try {
            String[] columnNames = new String[]{"id"};

            int ind = 1;
            PreparedStatement pstmt = conn.prepareStatement(qry, columnNames);
            if (con_id) {
                pstmt.setInt(ind++, a.getId());
            }
            pstmt.setString(ind++, a.getNombre());
            pstmt.setString(ind++, a.getServidor().isEmpty() ? "" : a.getServidor());
            pstmt.setString(ind++, a.getNotas().isEmpty() ? "" : a.getNotas());
            pstmt.setBoolean(ind++, a.isActiva());

            if (pstmt.executeUpdate() > 0) {
                ResultSet gkeys = pstmt.getGeneratedKeys();
                if (gkeys.next()) {
                    pkey = gkeys.getInt(1);
                }
            }

        } catch (SQLException e) {
            return 0;
        }
        return pkey;

    }

    private HashMap<String, Object> actualizar(Aplicaciones a) {

        HashMap<String,Object> datos = new HashMap<>();
        String qry = "UPDATE public.aplicaciones " +
                        "set nombre = ?, " +
                        "servidor = ?, " +
                        "notas = ?, " +
                        "activo = ? " +
                        "WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setString(1, a.getNombre());
            pstmt.setString(2, a.getServidor().isEmpty() ? "" : a.getServidor());
            pstmt.setString(3, a.getNotas().isEmpty() ? "" : a.getNotas());
            pstmt.setBoolean(4, a.isActiva());
            pstmt.setInt(5, a.getId());

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                datos.put("id", a.getId());
                datos.put("nombre", a.getNombre());
                datos.put("servidor", a.getServidor());
                datos.put("notas", a.getNotas());
                datos.put("activa", a.isActiva());
            } else {
                Aplicaciones ap = buscar(a.getId());
                if (ap != null) {
                    datos.put("id", ap.getId());
                    datos.put("nombre", ap.getNombre());
                    datos.put("servidor", ap.getServidor());
                    datos.put("notas", ap.getNotas());
                    datos.put("activa", ap.isActiva());
                }
            }   

        } catch (SQLException e) {}

        return datos;

    }

    private Aplicaciones buscar(Integer id) {

        Aplicaciones app = null;
        String qry = String.format("SELECT * FROM %s WHERE id = %d", TABLA, id);

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(qry);

            while (rs.next()) {
                app = new Aplicaciones(rs.getInt("id"),
                                        rs.getString("nombre"),
                                        rs.getString("servidor"), 
                                        rs.getString("notas"), 
                                        rs.getBoolean("activo"));
            }

        } catch (SQLException e) {}

        return app;

    }

    private Aplicaciones hmap2Object(HashMap<String,Object> datos) {
        
        Aplicaciones a = new Aplicaciones();
        a.setId((Integer)datos.get("id"));
        a.setNombre((String)datos.get("nombre"));
        a.setServidor((String)datos.get("servidor"));
        a.setNotas((String)datos.get("notas"));
        a.setActiva((Boolean)datos.get("activa"));
        return a;        

    }

    private HashMap<String,Object> object2HMap(Aplicaciones a) {
        
        HashMap<String,Object> datos = new HashMap<>();

        datos.put("id", a.getId());
        datos.put("nombre", a.getNombre());
        datos.put("servidor", a.getServidor());
        datos.put("notas", a.getNotas());
        datos.put("activa", a.isActiva());        
        return datos;

    }

    @Override
    public String buscar(Object o) {
        
        String qry = "";
        try {

            if (o instanceof Integer) {
                // buscar por Id
                qry = String.format("SELECT * FROM %s WHERE id = %d", TABLA, (Integer)o);
            } else if (o instanceof String) {
                qry = String.format("SELECT * FROM %s WHERE lower(nombre) = %c", TABLA, ((String)o).toLowerCase());
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(qry);

            Aplicaciones app = null;
            while (rs.next()) {

                app = new Aplicaciones(rs.getInt("id"),
                                    rs.getString("nombre"), 
                                    rs.getString("servidor"),
                                    rs.getString("notas"),
                                    rs.getBoolean("activo"));

            }

            return app.app2Str();

        } catch (SQLException e) {

            String msg = String.format("Error recuperando %s", TABLA);
            System.out.println(String.format("%s\nError: %s", msg, e.getMessage()));

        }
        
        return null;

    }

    @Override
    public LinkedHashMap<String, String> tipos() {
        
        return Aplicaciones.tipos();
        
    }

}
