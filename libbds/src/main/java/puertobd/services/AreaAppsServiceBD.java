package puertobd.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import puertobd.entities.AreaApps;
import puertobd.services.interfaces.AreaAppsIface;
import puertobd.tools.Conectar;

public class AreaAppsServiceBD 
            implements AreaAppsIface {

    private final String TABLA = "public.area_apps";
    private Connection conn = null;
    private LinkedHashMap<String,Object> datos = new LinkedHashMap<>();
    private LinkedList<String> atributos;
    private LinkedList<String> lst_apps;

    public AreaAppsServiceBD() {

        conn = new Conectar().leerConexion();
        atributos = AreaApps.atributos();

    }

    public LinkedList<String> apps( String orden ) {

        String orderBy = orden.isEmpty() ? "" : String.format("order by %s", orden);
        String qry = String.format( "SELECT * from %s %s",TABLA, orderBy );

        try {

            ResultSet rs = leerDatos( qry );
            lst_apps = new LinkedList<>();
        
            while ( rs.next() ) { lst_apps.add( new AreaApps( rs ).areaApps2Str() ); }

        } catch (SQLException sqle) {
            System.out.println( sqle.getMessage() );
            System.out.println( "Error al recuperar la tabla " ); 
        }

        return lst_apps;
        
    }
    private ResultSet leerDatos ( String qry ) 
        throws SQLException {

        return conn.createStatement().executeQuery( qry );

    }

    @Override
    public Integer crearRegistro(AreaApps um) {
        
        return grabar(um);

    }

    @Override
    public Integer crearRegistro(HashMap<String, Object> datos) {
        
        AreaApps um = hmap2Object(datos);
        return grabar(um);

    }

    public HashMap<String,Object> actualizarRegistro(AreaApps a) {
        return actualizar(a);
    }

    public HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos) {
        AreaApps a = hmap2Object(datos);
        return actualizar(a);
    }


    private Integer grabar(AreaApps um) {

        String qry = "";
        Boolean con_id = Boolean.FALSE;
        int pkey = 0;
        if ((um.getId() != null) || (um.getId() > 0)) {
            qry = String.format("INSERT INTO %s (id, area_id, fecha, aplicacion, ruta, notas) VALUES (?,?,?,?,?,?) RETURNING id ", TABLA);
            con_id = Boolean.TRUE;
        } else {
            qry = String.format("INSERT INTO %s (area_id, fecha, aplicacion, ruta, notas) VALUES (?,?,?,?,?) RETURNING id ", TABLA);
        }

        try {
            String[] columnNames = new String[]{"id"};
            int ind = 1;
            PreparedStatement pstmt = conn.prepareStatement(qry, columnNames);
            if (con_id) {
                pstmt.setInt(ind++, um.getId());
            }

            pstmt.setInt(ind++, um.getArea());
            pstmt.setTimestamp(ind++, um.getFecha());
            pstmt.setString(ind++, um.getAplicacion());
            pstmt.setString(ind++, um.getRuta());
            pstmt.setString(ind++, um.getNotas());

            if (pstmt.executeUpdate() > 0) {
                //System.out.println(String.format("Registro grabado: %d", um.getId()));
                ResultSet gkeys = pstmt.getGeneratedKeys();
                if (gkeys.next()) {
                    pkey = gkeys.getInt(1);
                }
            } else {
                //System.out.println(String.format("Registro no grabado: %d", um.getId()));
            }

        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
            return 0;
        }
        return pkey;

    }

    private HashMap<String, Object> actualizar(AreaApps aum) {

        HashMap<String,Object> datos = null;
        String qry = "UPDATE public.area_apps " +
                        "set area_id = ?, " +
                        "fecha = ?, " +
                        "aplicacion = ?, " +
                        "ruta = ?, " +
                        "notas = ? " +
                        "WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, aum.getArea());
            pstmt.setTimestamp(2, aum.getFecha());
            pstmt.setString(3, aum.getAplicacion());
            pstmt.setString(4, aum.getRuta());
            pstmt.setString(5, aum.getNotas());
            pstmt.setInt(6, aum.getId());

            if (pstmt.executeUpdate() > 0) {

                datos = new HashMap<>();
                datos.put("id", aum.getId());
                datos.put("area_id", aum.getArea());
                datos.put("fecha", aum.getFecha());
                datos.put("aplicacion", aum.getAplicacion());
                datos.put("ruta", aum.getRuta());
                datos.put("notas", aum.getNotas());

            }
            
        } catch (SQLException e) {}

        return datos;

    }

    private AreaApps hmap2Object(HashMap<String,Object> datos) {
        
        AreaApps um = new AreaApps();
        um.setId((Integer)datos.get("id"));
        um.setArea((Integer)datos.get("area_id"));
        um.setFecha((Timestamp)datos.get("fecha"));
        um.setAplicacion((String)datos.get("aplicacion"));
        um.setRuta((String)datos.get("ruta"));
        um.setNotas((String)datos.get("notas"));                

        return um;        

    }

    private HashMap<String,Object> object2HMap(AreaApps um) {
        
        HashMap<String,Object> datos = new HashMap<>();
        
        datos.put("id", um.getId());
        datos.put("area_id", um.getArea());
        datos.put("fecha", um.getFecha());
        datos.put("aplicacion", um.getAplicacion());
        datos.put("ruta", um.getRuta());
        datos.put("notas", um.getNotas());

        return datos;

    }

    @Override
    public LinkedList<String> atributos() {
        
        return AreaApps.atributos();
        
    }

    @Override
    public LinkedHashMap<String, String> tipos() {
        return AreaApps.tipos();
    }

}
