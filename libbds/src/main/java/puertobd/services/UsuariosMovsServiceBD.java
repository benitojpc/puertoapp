package puertobd.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import puertobd.entities.UsuariosMovs;
import puertobd.services.interfaces.UsuariosMovsIface;
import puertobd.tools.Conectar;

public class UsuariosMovsServiceBD 
            implements UsuariosMovsIface {

    private final String TABLA = "public.usuarios_movs";
    private Connection conn = null;
    private LinkedHashMap<String,Object> datos = new LinkedHashMap<>();
    private LinkedList<String> atributos;

    public UsuariosMovsServiceBD() {

        conn = new Conectar().leerConexion();
        atributos = UsuariosMovs.atributos();

    }

    @Override
    public Integer crearRegistro(UsuariosMovs um) {
        
        return grabar(um);

    }

    @Override
    public Integer crearRegistro(LinkedHashMap<String, Object> datos) {
        
        UsuariosMovs um = hmap2Object(datos);
        return grabar(um);

    }

    private Integer grabar(UsuariosMovs um) {

        String qry = "";
        Boolean con_id = Boolean.FALSE;
        int pkey = 0;
        if ((um.getId() != null) || (um.getId() > 0)) {
            qry = String.format("INSERT INTO %s (id, usuario, tipo, fecha) VALUES (?,?,?,?) RETURNING id ", TABLA);
            con_id = Boolean.TRUE;
        } else {
            qry = String.format("INSERT INTO %s (usuario, tipo, fecha) VALUES (?,?,?) RETURNING id ", TABLA);
        }

        try {
            String[] columnNames = new String[]{"id"};
            int ind = 1;
            PreparedStatement pstmt = conn.prepareStatement(qry, columnNames);
            if (con_id) {
                pstmt.setInt(ind++, um.getId());
            }

            pstmt.setInt(ind++, um.getUsuario());
            pstmt.setString(ind++, um.getTipo());
            pstmt.setDate(ind++, um.getFecha());

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

    private UsuariosMovs hmap2Object(HashMap<String,Object> datos) {
        
        UsuariosMovs um = new UsuariosMovs();
        um.setId((Integer)datos.get("id"));
        um.setUsuario((Integer)datos.get("usuario"));
        um.setTipo((String)datos.get("tipo"));
        um.setFecha((Date)datos.get("fecha"));

        return um;        

    }

    private HashMap<String,Object> object2HMap(UsuariosMovs um) {
        
        HashMap<String,Object> datos = new HashMap<>();
        
        datos.put("id", um.getId());
        datos.put("usuario", um.getUsuario());
        datos.put("tipo", um.getTipo());
        datos.put("fecha", um.getFecha());

        return datos;

    }

    @Override
    public LinkedList<String> atributos() {
        
        return UsuariosMovs.atributos();
        
    }

    @Override
    public LinkedHashMap<String, String> tipos() {
        return UsuariosMovs.tipos();
    }

}
