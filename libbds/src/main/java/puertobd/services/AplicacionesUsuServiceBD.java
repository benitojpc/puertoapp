package puertobd.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import puertobd.entities.AplicacionesUsu;
import puertobd.tools.Conectar;

public class AplicacionesUsuServiceBD 
        implements puertobd.services.interfaces.AplicacionesUsuIface {
    
    private final String TABLA = "public.aplicaciones_usuario";
    private Connection conn = null;
    private LinkedHashMap<String,Object> datos = new LinkedHashMap<>();
    private LinkedList<String> atributos;
    protected AplicacionesUsu au = null;

    public AplicacionesUsuServiceBD() {

        conn = new Conectar().leerConexion();
        atributos = AplicacionesUsu.atributos();

    }

    public Integer crearRegistro(AplicacionesUsu au) {
        return grabar(au);
    }

    public Integer crearRegistro(HashMap<String,Object> datos) {
        AplicacionesUsu au = hmap2Object(datos);
        return grabar(au);
    }

    public HashMap<String,Object> actualizarRegistro(AplicacionesUsu au) {
        return actualizar(au);
    }

    public HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos) {
        AplicacionesUsu au = hmap2Object(datos);
        return actualizar(au);
    }

    private Integer grabar(AplicacionesUsu au) {
        
        String qry = "";
        Boolean con_id = Boolean.FALSE;
        int pkey = 0;
        if ((au.getId() != null) || (au.getId() > 0)) {
            qry = "INSERT INTO public.aplicaciones_usuario (id,usuario,aplicacion,login,activa) VALUES (?,?,?,?,?) RETURNING id ";
            con_id = Boolean.TRUE;
        } else {
            qry = "INSERT INTO public.aplicaciones_usuario (usuario,aplicacion,login,activa) VALUES (?,?,?,?) RETURNING id ";
        }

        try {
            String[] columnNames = new String[]{"id"};

            int ind = 1;
            PreparedStatement pstmt = conn.prepareStatement(qry, columnNames);
            if (con_id) {
                pstmt.setInt(ind++, au.getId());
            }
            pstmt.setInt(ind++, au.getUsuario());
            pstmt.setInt(ind++, au.getAplicacion());
            pstmt.setString(ind++, au.getLogin());
            pstmt.setBoolean(ind++, au.getActiva());

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

    private HashMap<String, Object> actualizar(AplicacionesUsu au) {

        HashMap<String,Object> datos = null;
        String qry = "UPDATE public.aplicaciones_usuario " +
                        "set usuario = ?, " +
                        "aplicacion = ?, " +
                        "login = ?, " +
                        "activa = ? " +
                        "WHERE id = ?";

        try {
            
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, au.getUsuario());
            pstmt.setInt(2, au.getAplicacion());
            pstmt.setString(3, au.getLogin());
            pstmt.setBoolean(4, au.getActiva());
            pstmt.setInt(5, au.getId());

            int rows = pstmt.executeUpdate();

            if (rows > 0) {

                datos = new HashMap<>();
                datos.put("id", au.getId());
                datos.put("usuario", au.getUsuario());
                datos.put("aplicacion", au.getAplicacion());
                datos.put("login", au.getLogin());
                datos.put("activa", au.getActiva());

            } 

        } catch (SQLException e) {}

        return datos;

    }

    private AplicacionesUsu hmap2Object(HashMap<String,Object> datos) {
        
        AplicacionesUsu au = new AplicacionesUsu();
        au.setId((Integer)datos.get("id"));
        au.setUsuario((Integer)datos.get("usuario"));
        au.setAplicacion((Integer)datos.get("aplicacion"));
        au.setLogin((String)datos.get("login"));
        au.setActiva((Boolean)datos.get("activa"));
        return au;        

    }

    private HashMap<String,Object> object2HMap(AplicacionesUsu au) {
        
        HashMap<String,Object> datos = new HashMap<>();

        datos.put("id", au.getId());
        datos.put("usuario", au.getUsuario());
        datos.put("aplicacion", au.getAplicacion());
        datos.put("login", au.getLogin());
        datos.put("activa", au.getActiva());        
        return datos;

    }

    @Override
    public LinkedList<String> atributos() {

        return AplicacionesUsu.atributos();
        
    }

    @Override
    public LinkedHashMap<String, String> tipos() {
        return AplicacionesUsu.tipos();
    }

}
