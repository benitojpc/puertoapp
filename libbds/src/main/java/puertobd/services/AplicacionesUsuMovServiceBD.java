package puertobd.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import puertobd.entities.AplicacionesUsuMovs;
import puertobd.services.interfaces.AplicacionesUsuMovsIface;
import puertobd.tools.Conectar;

public class AplicacionesUsuMovServiceBD 
            implements AplicacionesUsuMovsIface {

    private final String TABLA = "public.aplicaciones_usuario_movs";
    private Connection conn = null;
    private LinkedHashMap<String,Object> datos = new LinkedHashMap<>();
    private LinkedList<String> atributos;

    public AplicacionesUsuMovServiceBD() {

        conn = new Conectar().leerConexion();
        atributos = AplicacionesUsuMovs.atributos();

    }

    @Override
    public Integer crearRegistro(AplicacionesUsuMovs um) {
        
        return grabar(um);

    }

    @Override
    public Integer crearRegistro(HashMap<String, Object> datos) {
        
        AplicacionesUsuMovs um = hmap2Object(datos);
        return grabar(um);

    }

    public HashMap<String,Object> actualizarRegistro(AplicacionesUsuMovs a) {
        return actualizar(a);
    }

    public HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos) {
        AplicacionesUsuMovs a = hmap2Object(datos);
        return actualizar(a);
    }


    private Integer grabar(AplicacionesUsuMovs um) {

        String qry = "";
        Boolean con_id = Boolean.FALSE;
        int pkey = 0;
        if ((um.getId() != null) || (um.getId() > 0)) {
            qry = String.format("INSERT INTO %s (id, userapp, fecha, tipo) VALUES (?,?,?,?) RETURNING id ", TABLA);
            con_id = Boolean.TRUE;
        } else {
            qry = String.format("INSERT INTO %s (userapp, fecha, tipo) VALUES (?,?,?) RETURNING id ", TABLA);
        }

        try {
            String[] columnNames = new String[]{"id"};
            int ind = 1;
            PreparedStatement pstmt = conn.prepareStatement(qry, columnNames);
            if (con_id) {
                pstmt.setInt(ind++, um.getId());
            }

            pstmt.setInt(ind++, um.getAppUsu());
            pstmt.setDate(ind++, um.getFecha());
            pstmt.setString(ind++, um.getTipo());

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

    private HashMap<String, Object> actualizar(AplicacionesUsuMovs aum) {

        HashMap<String,Object> datos = null;
        String qry = "UPDATE public.aplicaciones_usuario_movs " +
                        "set userapp = ?, " +
                        "fecha = ?, " +
                        "tipo = ? " +
                        "WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, aum.getAppUsu());
            pstmt.setDate(2, aum.getFecha());
            pstmt.setString(3, aum.getTipo());
            pstmt.setInt(5, aum.getId());

            if (pstmt.executeUpdate() > 0) {

                datos = new HashMap<>();
                datos.put("id", aum.getId());
                datos.put("userapp", aum.getAppUsu());
                datos.put("fecha", aum.getFecha());
                datos.put("tipo", aum.getTipo());

            }
            
        } catch (SQLException e) {}

        return datos;

    }

    private AplicacionesUsuMovs hmap2Object(HashMap<String,Object> datos) {
        
        AplicacionesUsuMovs um = new AplicacionesUsuMovs();
        um.setId((Integer)datos.get("id"));
        um.setAppUsu((Integer)datos.get("userapp"));
        um.setFecha((Date)datos.get("fecha"));
        um.setTipo((String)datos.get("tipo"));

        return um;        

    }

    private HashMap<String,Object> object2HMap(AplicacionesUsuMovs um) {
        
        HashMap<String,Object> datos = new HashMap<>();
        
        datos.put("id", um.getId());
        datos.put("userapp", um.getAppUsu());
        datos.put("fecha", um.getFecha());
        datos.put("tipo", um.getTipo());

        return datos;

    }

    @Override
    public LinkedList<String> atributos() {

        return AplicacionesUsuMovs.atributos();
        
    }

    @Override
    public LinkedHashMap<String, String> tipos() {
        return AplicacionesUsuMovs.tipos();
    }

}
