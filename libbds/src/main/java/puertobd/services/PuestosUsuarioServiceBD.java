package puertobd.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import puertobd.entities.PuestosUsu;
import puertobd.services.interfaces.PuestosUsuarioIface;
import puertobd.tools.Conectar;

public class PuestosUsuarioServiceBD 
            implements PuestosUsuarioIface  {

    private final String TABLA = "public.puestos_usuario";
    private Connection conn = null;
    private LinkedHashMap<String,Object> datos = new LinkedHashMap<>();
    private LinkedList<String> atributos;

    public PuestosUsuarioServiceBD() {

        conn = new Conectar().leerConexion();
        atributos = PuestosUsu.atributos();

    }

    @Override
    public Integer crearRegistro(PuestosUsu gu) {
        
        return grabar(gu);

    }

    @Override
    public Integer crearRegistro(LinkedHashMap<String, Object> datos) {
        
        PuestosUsu gu = hmap2Object(datos);
        return grabar(gu);

    }

    public HashMap<String,Object> actualizarRegistro(PuestosUsu a) {
        return actualizar(a);
    }

    public HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos) {
        PuestosUsu a = hmap2Object(datos);
        return actualizar(a);
    }

    private Integer grabar(PuestosUsu gu) {

        String qry = "";
        Boolean con_id = Boolean.FALSE;
        int pkey = 0;
        if ((gu.getId() != null) || (gu.getId() > 0)) {
            qry = String.format("INSERT INTO %s (id, usuario, puesto, fechaa, fechab) VALUES (?,?,?,?,?) RETURNING id ", TABLA);
            con_id = Boolean.TRUE;
        } else {
            qry = String.format("INSERT INTO %s (usuario,puesto,fechaa,fechab) VALUES (?,?,?,?) RETURNING id ", TABLA);
        }

        try {
            String[] columnNames = new String[]{"id"};
            int ind = 1;
            PreparedStatement pstmt = conn.prepareStatement(qry, columnNames);
            if (con_id) {
                pstmt.setInt(ind++, gu.getId());
            }

            pstmt.setInt(ind++, gu.getUsuario());
            pstmt.setInt(ind++, gu.getPuesto());
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

    private HashMap<String, Object> actualizar(PuestosUsu gu) {

        HashMap<String,Object> datos = null;
        String qry = "UPDATE public.puestos_usuario " +
                        "set usuario = ?, " +
                        "puesto = ?, " +
                        "fechaa = ?, " +
                        "fechab = ? " +
                        "WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, gu.getUsuario());
            pstmt.setInt(2, gu.getPuesto());
            pstmt.setDate(3, gu.getFechaA());
            pstmt.setDate(4, gu.getFechaB());
            pstmt.setInt(5, gu.getId());

            if (pstmt.executeUpdate() > 0) {

                datos = new HashMap<>();
                datos.put("id", gu.getId());
                datos.put("usuario", gu.getUsuario());
                datos.put("puesto", gu.getPuesto());
                datos.put("feca", gu.getFechaA());
                datos.put("fecb", gu.getFechaB());

            }
            
        } catch (SQLException e) {}

        return datos;

    }

    private PuestosUsu hmap2Object(HashMap<String,Object> datos) {
        
        PuestosUsu gu = new PuestosUsu();
        gu.setId((Integer)datos.get("id"));
        gu.setUsuario((Integer)datos.get("usuario"));
        gu.setPuesto((Integer)datos.get("puesto"));
        gu.setFechaA((Date)datos.get("fechaa"));
        gu.setFechaB((Date)datos.get("fechab"));

        return gu;        

    }

    private HashMap<String,Object> object2HMap(PuestosUsu gu) {
        
        HashMap<String,Object> datos = new HashMap<>();
        
        datos.put("id", gu.getId());
        datos.put("usuario", gu.getUsuario());
        datos.put("puesto", gu.getPuesto());
        datos.put("fechaa", gu.getFechaA());
        datos.put("fechab", gu.getFechaB());

        return datos;

    }

    @Override
    public LinkedList<String> atributos() {
        
        return PuestosUsu.atributos();
        
    }

    @Override
    public LinkedHashMap<String, String> tipos() {
        return PuestosUsu.tipos();
    }

}
