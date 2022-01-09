package puertobd.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import puertobd.tools.Conectar;
import puertobd.entities.Puestos;

public class PuestoServiceBD 
        implements puertobd.services.interfaces.PuestoIface {
    
    private final String TABLA = "public.Puestos";
    private Connection conn = null;
    private LinkedHashMap<String,Object> datos = new LinkedHashMap<>();
    private LinkedList<String> atributos;

    public PuestoServiceBD() {

        conn = new Conectar().leerConexion();
        atributos = Puestos.atributos();

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
    
    public LinkedList<String> puestos( String orden ) {

        String orderBy = orden.isEmpty() ? "" : String.format("order by %s", orden);
        String qry = String.format( "SELECT * from %s %s",TABLA, orderBy );
        LinkedList<String> items = new LinkedList<>();

        try {

            ResultSet rs = leerDatos( qry );
            while ( rs.next() ) { items.add( new Puestos( rs ).puesto2Str() ); }

        } catch (SQLException sqle) {System.out.println( "Error al recuperar la tabla " ); }

        return items;
        
    }

    private ResultSet leerDatos ( String qry ) 
                throws SQLException {
        
        return conn.createStatement().executeQuery( qry );

    }

    // borrar registro no elimina el registro, sólo marca activo como falso.
    public Integer borrarRegistro(Integer id) {
        
        int rows = 0;
        String qry = "UPDATE public.Puestos " +
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

    public Integer crearRegistro(Puestos a) {
        return grabar(a);
    }

    public Integer crearRegistro(HashMap<String,Object> datos) {
        Puestos a = hmap2Object(datos);
        return grabar(a);
    }

    public HashMap<String,Object> actualizarRegistro(Puestos a) {
        return actualizar(a);
    }

    public HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos) {
        Puestos a = hmap2Object(datos);
        return actualizar(a);
    }


    private Integer grabar(Puestos a) {
        
        String qry = "";
        Boolean con_id = Boolean.FALSE;
        int pkey = 0;
        if ((a.getId() != null) || (a.getId() > 0)) {
            qry = "INSERT INTO public.Puestos (id,puesto,activo) VALUES (?,?,?) RETURNING id ";
            con_id = Boolean.TRUE;
        } else {
            qry = "INSERT INTO public.Puestos (puesto,activo) VALUES (?,?) RETURNING id ";
        }

        try {
            String[] columnNames = new String[]{"id"};

            int ind = 1;
            PreparedStatement pstmt = conn.prepareStatement(qry, columnNames);
            if (con_id) {
                pstmt.setInt(ind++, a.getId());
            }

            pstmt.setString(ind++, a.getPuesto());
            pstmt.setBoolean(ind++, a.isActivo());

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

    private HashMap<String, Object> actualizar(Puestos a) {

        HashMap<String,Object> datos = new HashMap<>();
        String qry = "UPDATE public.Puestos " +
                        "set puesto = ?, " +
                        "activo = ? " +
                        "WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setString(1, a.getPuesto());
            pstmt.setBoolean(4, a.isActivo());
            pstmt.setInt(5, a.getId());

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                datos.put("id", a.getId());
                datos.put("puesto", a.getPuesto());
                datos.put("activo", a.isActivo());
            } else {
                Puestos ap = buscar(a.getId());
                if (ap != null) {
                    datos.put("id", ap.getId());
                    datos.put("puesto", ap.getPuesto());
                    datos.put("activo", ap.isActivo());
                }
            }   

        } catch (SQLException e) {}

        return datos;

    }

    private Puestos buscar(Integer id) {

        Puestos app = null;
        String qry = String.format("SELECT * FROM %s WHERE id = %d", TABLA, id);

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(qry);

            while (rs.next()) {
                app = new Puestos(rs.getInt("id"),
                                        rs.getString("puesto"), 
                                        rs.getBoolean("activo"));
            }

        } catch (SQLException e) {}

        return app;

    }

    private Puestos hmap2Object(HashMap<String,Object> datos) {
        
        Puestos a = new Puestos();
        a.setId((Integer)datos.get("id"));
        a.setPuesto((String)datos.get("puesto"));
        a.setActivo((Boolean)datos.get("activo"));
        return a;        

    }

    private HashMap<String,Object> object2HMap(Puestos a) {
        
        HashMap<String,Object> datos = new HashMap<>();

        datos.put("id", a.getId());
        datos.put("puesto", a.getPuesto());
        datos.put("activo", a.isActivo());        
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
                qry = String.format("SELECT * FROM %s WHERE lower(puesto) = %c", TABLA, ((String)o).toLowerCase());
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(qry);

            Puestos puesto = null;
            while (rs.next()) {

                puesto = new Puestos(rs.getInt("id"),
                                    rs.getString("puesto"), 
                                    rs.getBoolean("activo"));

            }

            return puesto.puesto2Str();

        } catch (SQLException e) {

            String msg = String.format("Error recuperando %s", TABLA);
            System.out.println(String.format("%s\nError: %s", msg, e.getMessage()));

        }
        
        return null;

    }

    @Override
    public LinkedList<String> atributos() {
        
        return Puestos.atributos();
        
    }

    @Override
    public LinkedHashMap<String, String> tipos() {
        return Puestos.tipos();
    }

}
