package puertobd.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import puertobd.entities.GruposUsu;
import puertobd.services.interfaces.GruposUsuariosIface;
import puertobd.tools.Conectar;

public class GruposUsuariosServiceBD 
            implements GruposUsuariosIface  {

    private final String TABLA = "public.grupos_usuario";
    private Connection conn = null;
    private LinkedHashMap<String,Object> datos = new LinkedHashMap<>();
    private LinkedList<String> atributos;

    public GruposUsuariosServiceBD() {

        conn = new Conectar().leerConexion();
        atributos = GruposUsu.atributos();

    }

    @Override
    public Integer crearRegistro(GruposUsu gu) {
        
        return grabar(gu);

    }

    @Override
    public Integer crearRegistro(LinkedHashMap<String, Object> datos) {
        
        GruposUsu gu = hmap2Object(datos);
        return grabar(gu);

    }

    public HashMap<String,Object> actualizarRegistro(GruposUsu a) {
        return actualizar(a);
    }

    public HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos) {
        GruposUsu a = hmap2Object(datos);
        return actualizar(a);
    }

    private Integer grabar(GruposUsu gu) {

        String qry = "";
        Boolean con_id = Boolean.FALSE;
        int pkey = 0;
        if ((gu.getId() != null) || (gu.getId() > 0)) {
            qry = String.format("INSERT INTO %s (id, usuario, grupo, fechaa, fechab) VALUES (?,?,?,?,?) RETURNING id ", TABLA);
            con_id = Boolean.TRUE;
        } else {
            qry = String.format("INSERT INTO %s (usuario,grupo,fechaa,fechab) VALUES (?,?,?,?) RETURNING id ", TABLA);
        }

        try {
            String[] columnNames = new String[]{"id"};
            int ind = 1;
            PreparedStatement pstmt = conn.prepareStatement(qry, columnNames);
            if (con_id) {
                pstmt.setInt(ind++, gu.getId());
            }

            pstmt.setInt(ind++, gu.getUsuario());
            pstmt.setInt(ind++, gu.getGrupo());
            pstmt.setDate(ind++, gu.getFechaA());
            pstmt.setDate(ind++, gu.getFechaB());

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

    private HashMap<String, Object> actualizar(GruposUsu gu) {

        HashMap<String,Object> datos = null;
        String qry = "UPDATE public.grupos_usuario " +
                        "set usuario = ?, " +
                        "grupo = ?, " +
                        "fechaa = ?, " +
                        "fechab = ? " +
                        "WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, gu.getUsuario());
            pstmt.setInt(2, gu.getGrupo());
            pstmt.setDate(3, gu.getFechaA());
            pstmt.setDate(4, gu.getFechaB());
            pstmt.setInt(5, gu.getId());

            if (pstmt.executeUpdate() > 0) {

                datos = new HashMap<>();
                datos.put("id", gu.getId());
                datos.put("usuario", gu.getUsuario());
                datos.put("grupo", gu.getGrupo());
                datos.put("feca", gu.getFechaA());
                datos.put("fecb", gu.getFechaB());

            }
            
        } catch (SQLException e) {}

        return datos;

    }

    private GruposUsu hmap2Object(HashMap<String,Object> datos) {
        
        GruposUsu gu = new GruposUsu();
        gu.setId((Integer)datos.get("id"));
        gu.setUsuario((Integer)datos.get("usuario"));
        gu.setGrupo((Integer)datos.get("grupo"));
        gu.setFechaA((Date)datos.get("fechaa"));
        gu.setFechaB((Date)datos.get("fechab"));

        return gu;        

    }

    private HashMap<String,Object> object2HMap(GruposUsu gu) {
        
        HashMap<String,Object> datos = new HashMap<>();
        
        datos.put("id", gu.getId());
        datos.put("usuario", gu.getUsuario());
        datos.put("grupo", gu.getGrupo());
        datos.put("fechaa", gu.getFechaA());
        datos.put("fechab", gu.getFechaB());

        return datos;

    }

    @Override
    public LinkedList<String> atributos() {

        return GruposUsu.atributos();
        
    }

    @Override
    public LinkedHashMap<String, String> tipos() {
        return GruposUsu.tipos();
    }

}
