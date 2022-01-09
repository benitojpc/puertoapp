package puertobd.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import puertobd.entities.PerfilesApp;
import puertobd.services.interfaces.PerfilesAppIface;
import puertobd.tools.Conectar;

public class PerfilesAppServiceBD 
            implements PerfilesAppIface {

    private final String TABLA = "public.perfiles_aplicacion";
    private Connection conn = null;
    private LinkedHashMap<String,Object> datos = new LinkedHashMap<>();
    private LinkedList<String> atributos;

    public PerfilesAppServiceBD() {

        conn = new Conectar().leerConexion();
        atributos = PerfilesApp.atributos();

    }

    @Override
    public Integer crearRegistro(PerfilesApp um) {
        
        return grabar(um);

    }

    @Override
    public Integer crearRegistro(HashMap<String, Object> datos) {
        
        PerfilesApp um = hmap2Object(datos);
        return grabar(um);

    }

    public HashMap<String,Object> actualizarRegistro(PerfilesApp a) {
        return actualizar(a);
    }

    public HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos) {
        PerfilesApp a = hmap2Object(datos);
        return actualizar(a);
    }


    private Integer grabar(PerfilesApp um) {

        String qry = "";
        Boolean con_id = Boolean.FALSE;
        int pkey = 0;
        if ((um.getId() != null) || (um.getId() > 0)) {
            qry = String.format("INSERT INTO %s (id, aplicacion,perfil,descripcion, activo) VALUES (?,?,?,?,?) RETURNING id ", TABLA);
            con_id = Boolean.TRUE;
        } else {
            qry = String.format("INSERT INTO %s (aplicacion,perfil,descripcion, activo) VALUES (?,?,?,?) RETURNING id ", TABLA);
        }

        try {
            String[] columnNames = new String[]{"id"};
            int ind = 1;
            PreparedStatement pstmt = conn.prepareStatement(qry, columnNames);
            if (con_id) {
                pstmt.setInt(ind++, um.getId());
            }

            pstmt.setInt(ind++, um.getAplicacion());
            pstmt.setString(ind++, um.getPerfil());
            pstmt.setString(ind++, um.getDescripcion());
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

    private HashMap<String, Object> actualizar(PerfilesApp aum) {

        HashMap<String,Object> datos = null;
        String qry = "UPDATE public.perfiles_aplicacion " +
                        "set aplicacion = ?, " +
                        "perfil = ?, " +
                        "descripcion = ?, " +
                        "activo = ? " +
                        "WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, aum.getAplicacion());
            pstmt.setString(2, aum.getPerfil());
            pstmt.setString(3, aum.getDescripcion());
            pstmt.setBoolean(4, aum.isActivo());
            pstmt.setInt(5, aum.getId());

            if (pstmt.executeUpdate() > 0) {

                datos = new HashMap<>();
                datos.put("id", aum.getId());
                datos.put("aplicacion", aum.getAplicacion());
                datos.put("perfil", aum.getPerfil());
                datos.put("descripcion", aum.getDescripcion());
                datos.put("activo", aum.isActivo());

            }
            
        } catch (SQLException e) {}

        return datos;

    }

    private PerfilesApp hmap2Object(HashMap<String,Object> datos) {
        
        PerfilesApp um = new PerfilesApp();
        um.setId((Integer)datos.get("id"));
        um.setAplicacion((Integer)datos.get("aplicacion"));
        um.setPerfil((String)datos.get("perfil"));
        um.setDescripcion((String)datos.get("descripcion"));
        um.setActivo((Boolean)datos.get("activo"));

        return um;        

    }

    private HashMap<String,Object> object2HMap(PerfilesApp um) {
        
        HashMap<String,Object> datos = new HashMap<>();
        
        datos.put("id", um.getId());
        datos.put("aplicacion", um.getAplicacion());
        datos.put("perfil", um.getPerfil());
        datos.put("descripcion", um.getDescripcion());        
        datos.put("activo", um.isActivo());

        return datos;

    }

    @Override
    public LinkedList<String> atributos() {
        
        return PerfilesApp.atributos();
        
    }

    @Override
    public LinkedHashMap<String, String> tipos() {
        return PerfilesApp.tipos();
    }

}
