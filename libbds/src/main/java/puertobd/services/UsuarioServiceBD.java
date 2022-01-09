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
import puertobd.entities.Usuarios;

public class UsuarioServiceBD 
        implements puertobd.services.interfaces.UsuarioIface {
    
    private final String TABLA = "usuarios";
    private Connection conn = null;
    private LinkedHashMap<String,Object> datos = new LinkedHashMap<>();
    private LinkedList<String> atributos;

    public UsuarioServiceBD() {

        conn = new Conectar().leerConexion();
        atributos = Usuarios.atributos();

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

    public LinkedList<String> usuarios ( String orden ) {

        String orderBy = orden.isEmpty() ? "" : String.format("order by %s", orden);
        String qry = String.format( "SELECT * from %s %s",TABLA, orderBy );
        LinkedList<String> items = new LinkedList<>();
        System.out.println(qry);

        try {

            ResultSet rs = leerDatos( qry );
            while ( rs.next() ) { items.add( new Usuarios( rs ).usuario2Str() ); }

        } catch (SQLException sqle) {
            System.out.println( "Error al recuperar la tabla " ); 
            sqle.printStackTrace();
        }

        return items;
        
    }

    private ResultSet leerDatos ( String qry ) 
                throws SQLException {
        
        return conn.createStatement().executeQuery( qry );

    }

    // borrar registro no elimina el registro, sólo marca situacion como falso.
    public Integer borrarRegistro(Integer id) {
        
        int rows = 0;
        String qry = "UPDATE public.Usuarios " +
                        "set situacion = ? " +
                        "WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setBoolean(1, false);
            pstmt.setInt(2, id);

            rows = pstmt.executeUpdate();


        } catch (SQLException e) {}

        return rows;

    }

    public Integer crearRegistro(Usuarios a) {
        return grabar(a);
    }

    public Integer crearRegistro(HashMap<String,Object> datos) {
        Usuarios a = hmap2Object(datos);
        return grabar(a);
    }

    public HashMap<String,Object> actualizarRegistro(Usuarios a) {
        return actualizar(a);
    }

    public HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos) {
        Usuarios a = hmap2Object(datos);
        return actualizar(a);
    }


    private Integer grabar(Usuarios a) {

        String qry = "";
        Boolean con_id = Boolean.FALSE;
        int pkey = 0;
        if ((a.getId() != null) || (a.getId() > 0)) {
            qry = "INSERT INTO public.Usuarios (id,carnet, dnie, ape1, ape2, nomb,login,situacion) VALUES (?,?,?,?,?,?,?,?) RETURNING id ";
            con_id = Boolean.TRUE;
        } else {
            qry = "INSERT INTO public.Usuarios (carnet, dnie, ape1, ape2, nomb,login,situacion) VALUES (?,?,?,?,?,?,?) RETURNING id ";
        }

        try {
            String[] columnNames = new String[]{"id"};
            int ind = 1;
            PreparedStatement pstmt = conn.prepareStatement(qry, columnNames);
            if (con_id) {
                pstmt.setInt(ind++, a.getId());
            }

            pstmt.setString(ind++, a.getCarnet());
            pstmt.setString(ind++, a.getDnie());
            pstmt.setString(ind++, a.getApe1());
            pstmt.setString(ind++, a.getApe2());
            pstmt.setString(ind++, a.getNombre());
            pstmt.setString(ind++, a.getlogin().isEmpty() ? "" : a.getlogin());
            pstmt.setBoolean(ind++, a.isSituacion());

            if (pstmt.executeUpdate() > 0) {
                //System.out.println(String.format("Registro grabado: %d", a.getId()));
                ResultSet gkeys = pstmt.getGeneratedKeys();
                if (gkeys.next()) {
                    pkey = gkeys.getInt(1);
                }
            } else {
                //System.out.println(String.format("Registro no grabado: %d", a.getId()));
            }

        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
            return 0;
        }
        return pkey;

    }

    private HashMap<String, Object> actualizar(Usuarios a) {

        HashMap<String,Object> datos = new HashMap<>();
        String qry = "UPDATE public.Usuarios " +
                        "set carnet = ?," + 
                        "dnie = ?," + 
                        "ape1 = ?," + 
                        "ape2 = ?," + 
                        "nombre = ?, " +
                        "login = ?, " +
                        "situacion = ? " +
                        "WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setString(1, a.getCarnet());
            pstmt.setString(2, a.getDnie());
            pstmt.setString(3, a.getApe1());
            pstmt.setString(4, a.getApe2().isEmpty() ? "" : a.getApe2());
            pstmt.setString(5, a.getNombre());
            pstmt.setString(6, a.getlogin());
            pstmt.setBoolean(7, a.isSituacion());
            pstmt.setInt(8, a.getId());

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                datos.put("id", a.getId());
                datos.put("carnet", a.getCarnet());
                datos.put("dnie", a.getDnie());
                datos.put("ape1", a.getApe1());
                datos.put("ape2", a.getApe2());
                datos.put("nombre", a.getNombre());
                datos.put("login", a.getlogin());
                datos.put("situacion", a.isSituacion());
            } else {
                Usuarios ap = buscar(a.getId());
                if (ap != null) {
                    datos.put("id", ap.getId());
                    datos.put("carnet", ap.getCarnet());
                    datos.put("dnie", ap.getDnie());
                    datos.put("ape1", ap.getApe1());
                    datos.put("ape2", ap.getApe2());
                    datos.put("nombre", ap.getNombre());
                    datos.put("login", ap.getlogin());
                    datos.put("situacion", ap.isSituacion());
                }
            }   

        } catch (SQLException e) {}

        return datos;

    }

    private Usuarios buscar(Integer id) {

        Usuarios app = null;
        String qry = String.format("SELECT * FROM %s WHERE id = %d", TABLA, id);

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(qry);

            while (rs.next()) {
                app = new Usuarios(rs.getInt("id"),
                                        rs.getString("carnet"),
                                        rs.getString("dnie"),
                                        rs.getString("ape1"),
                                        rs.getString("ape2"),
                                        rs.getString("nombre"),
                                        rs.getString("login"), 
                                        rs.getBoolean("situacion"));
            }

        } catch (SQLException e) {}

        return app;

    }

    private Usuarios hmap2Object(HashMap<String,Object> datos) {
        
        Usuarios a = new Usuarios();
        a.setId((Integer)datos.get("id"));
        a.setCarnet((String)datos.get("carnet"));
        a.setDnie((String)datos.get("dnie"));
        a.setApe1((String)datos.get("ape1"));
        a.setApe2((String)datos.get("ape2"));                                
        a.setNombre((String)datos.get("nombre"));
        a.setlogin((String)datos.get("login"));
        a.setSituacion((Boolean)datos.get("situacion"));
        return a;        

    }

    private HashMap<String,Object> object2HMap(Usuarios a) {
        
        HashMap<String,Object> datos = new HashMap<>();
        
        datos.put("id", a.getId());
        datos.put("carnet", a.getCarnet());
        datos.put("dnie", a.getDnie());
        datos.put("ape1", a.getApe1());
        datos.put("ape2", a.getApe2());
        datos.put("nombre", a.getNombre());
        datos.put("login", a.getlogin());
        datos.put("situacion", a.isSituacion());

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
                qry = String.format("SELECT * FROM %s WHERE lower(login) = %c", TABLA, ((String)o).toLowerCase());
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(qry);

            Usuarios user = null;
            while (rs.next()) {

                user = new Usuarios(rs.getInt("id"),
                                    rs.getString("carnet"),
                                    rs.getString("dnie"),
                                    rs.getString("ape1"),
                                    rs.getString("ape2"),
                                    rs.getString("nombre"),
                                    rs.getString("login"), 
                                    rs.getBoolean("situacion"));

            }

            return user.usuario2Str();

        } catch (SQLException e) {

            String msg = String.format("Error recuperando usuario");
            System.out.println(String.format("%s\nError: %s", msg, e.getMessage()));

        }
        
        return null;

    }

    @Override
    public LinkedList<String> atributos() {

        return Usuarios.atributos();

    }

    @Override
    public LinkedHashMap<String, String> tipos() {
        return Usuarios.tipos();
    }

}
