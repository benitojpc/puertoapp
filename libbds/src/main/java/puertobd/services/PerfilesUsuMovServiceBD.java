package puertobd.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import puertobd.entities.PerfilesUsuMovs;
import puertobd.services.interfaces.PerfilesUsuMovsIface;
import puertobd.tools.Conectar;

public class PerfilesUsuMovServiceBD 
            implements PerfilesUsuMovsIface {

    private final String TABLA = "public.perfiles_usuario_movs";
    private Connection conn = null;
    private LinkedHashMap<String,Object> datos = new LinkedHashMap<>();
    private LinkedList<String> atributos;

    public PerfilesUsuMovServiceBD() {

        conn = new Conectar().leerConexion();
        atributos = PerfilesUsuMovs.atributos();

    }

    @Override
    public Integer crearRegistro(PerfilesUsuMovs um) {
        
        return grabar(um);

    }

    @Override
    public Integer crearRegistro(HashMap<String, Object> datos) {
        
        PerfilesUsuMovs um = hmap2Object(datos);
        return grabar(um);

    }

    public HashMap<String,Object> actualizarRegistro(PerfilesUsuMovs a) {
        return actualizar(a);
    }

    public HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos) {
        PerfilesUsuMovs a = hmap2Object(datos);
        return actualizar(a);
    }


    private Integer grabar(PerfilesUsuMovs um) {

        String qry = "";
        Boolean con_id = Boolean.FALSE;
        int pkey = 0;
        if ((um.getId() != null) || (um.getId() > 0)) {
            qry = String.format("INSERT INTO %s (id, userperf, fecha, tipo) VALUES (?,?,?,?) RETURNING id ", TABLA);
            con_id = Boolean.TRUE;
        } else {
            qry = String.format("INSERT INTO %s (userperf, fecha, tipo) VALUES (?,?,?) RETURNING id ", TABLA);
        }

        try {
            String[] columnNames = new String[]{"id"};
            int ind = 1;
            PreparedStatement pstmt = conn.prepareStatement(qry, columnNames);
            if (con_id) {
                pstmt.setInt(ind++, um.getId());
            }

            pstmt.setInt(ind++, um.getPerfusu());
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

    private HashMap<String, Object> actualizar(PerfilesUsuMovs aum) {

        HashMap<String,Object> datos = null;
        String qry = "UPDATE public.perfiles_usuario_movs " +
                        "set userperf = ?, " +
                        "fecha = ?, " +
                        "tipo = ? " +
                        "WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, aum.getPerfusu());
            pstmt.setDate(2, aum.getFecha());
            pstmt.setString(3, aum.getTipo());
            pstmt.setInt(5, aum.getId());

            if (pstmt.executeUpdate() > 0) {

                datos = new HashMap<>();
                datos.put("id", aum.getId());
                datos.put("userperf", aum.getPerfusu());
                datos.put("fecha", aum.getFecha());
                datos.put("tipo", aum.getTipo());

            }
            
        } catch (SQLException e) {}

        return datos;

    }

    private PerfilesUsuMovs hmap2Object(HashMap<String,Object> datos) {
        
        PerfilesUsuMovs um = new PerfilesUsuMovs();
        um.setId((Integer)datos.get("id"));
        um.setPerfusu((Integer)datos.get("userperf"));
        um.setFecha((Date)datos.get("fecha"));
        um.setTipo((String)datos.get("tipo"));

        return um;        

    }

    private HashMap<String,Object> object2HMap(PerfilesUsuMovs um) {
        
        HashMap<String,Object> datos = new HashMap<>();
        
        datos.put("id", um.getId());
        datos.put("userperf", um.getPerfusu());
        datos.put("fecha", um.getFecha());
        datos.put("tipo", um.getTipo());

        return datos;

    }

    @Override
    public LinkedList<String> atributos() {
        
        return PerfilesUsuMovs.atributos();
        
    }

    @Override
    public LinkedHashMap<String, String> tipos() {
        return PerfilesUsuMovs.tipos();
    }

}
