package puertobd.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import puertobd.entities.PerfilesUsu;
import puertobd.services.interfaces.PerfilesUsuIface;
import puertobd.tools.Conectar;

public class PerfilesUsuServiceBD 
            implements PerfilesUsuIface {

    private final String TABLA = "public.perfiles_usuario";
    private Connection conn = null;
    private LinkedHashMap<String,Object> datos = new LinkedHashMap<>();
    private LinkedList<String> atributos;

    public PerfilesUsuServiceBD() {

        conn = new Conectar().leerConexion();
        atributos = PerfilesUsu.atributos();

    }

    @Override
    public Integer crearRegistro(PerfilesUsu um) {
        
        return grabar(um);

    }

    @Override
    public Integer crearRegistro(HashMap<String, Object> datos) {
        
        PerfilesUsu um = hmap2Object(datos);
        return grabar(um);

    }

    public HashMap<String,Object> actualizarRegistro(PerfilesUsu a) {
        return actualizar(a);
    }

    public HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos) {
        PerfilesUsu a = hmap2Object(datos);
        return actualizar(a);
    }


    private Integer grabar(PerfilesUsu um) {

        String qry = "";
        Boolean con_id = Boolean.FALSE;
        int pkey = 0;
        if ((um.getId() != null) || (um.getId() > 0)) {
            qry = String.format("INSERT INTO %s (id, usuario, perfil, activo) VALUES (?,?,?,?) RETURNING id ", TABLA);
            con_id = Boolean.TRUE;
        } else {
            qry = String.format("INSERT INTO %s (usuario, perfil, activo) VALUES (?,?,?) RETURNING id ", TABLA);
        }

        try {
            String[] columnNames = new String[]{"id"};
            int ind = 1;
            PreparedStatement pstmt = conn.prepareStatement(qry, columnNames);
            if (con_id) {
                pstmt.setInt(ind++, um.getId());
            }

            pstmt.setInt(ind++, um.getUsuario());
            pstmt.setInt(ind++, um.getPerfil());
            pstmt.setBoolean(ind++, um.isActivo());

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

    private HashMap<String, Object> actualizar(PerfilesUsu aum) {

        HashMap<String,Object> datos = null;
        String qry = "UPDATE public.perfiles_usuario " +
                        "set usuario = ?, " +
                        "perfil = ?, " +
                        "activo = ? " +
                        "WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, aum.getUsuario());
            pstmt.setInt(2, aum.getPerfil());
            pstmt.setBoolean(3, aum.isActivo());
            pstmt.setInt(5, aum.getId());

            if (pstmt.executeUpdate() > 0) {

                datos = new HashMap<>();
                datos.put("id", aum.getId());
                datos.put("usuario", aum.getUsuario());
                datos.put("perfil", aum.getPerfil());
                datos.put("activo", aum.isActivo());

            }
            
        } catch (SQLException e) {}

        return datos;

    }

    private PerfilesUsu hmap2Object(HashMap<String,Object> datos) {
        
        PerfilesUsu um = new PerfilesUsu();
        um.setId((Integer)datos.get("id"));
        um.setUsuario((Integer)datos.get("usuario"));
        um.setPerfil((Integer)datos.get("perfil"));
        um.setActivo((Boolean)datos.get("activo"));

        return um;        

    }

    private HashMap<String,Object> object2HMap(PerfilesUsu um) {
        
        HashMap<String,Object> datos = new HashMap<>();
        
        datos.put("id", um.getId());
        datos.put("usuario", um.getUsuario());
        datos.put("perfil", um.getPerfil());
        datos.put("activo", um.isActivo());

        return datos;

    }

    @Override
    public LinkedList<String> atributos() {
        
        return PerfilesUsu.atributos();
        
    }

    @Override
    public LinkedHashMap<String, String> tipos() {
        return PerfilesUsu.tipos();
    }

}
