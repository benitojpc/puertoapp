package puertobd.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import puertobd.entities.ViewAreaApps;
import puertobd.services.interfaces.ViewAreaAppsIface;
import puertobd.tools.Conectar;

public class ViewAreaAppsServiceBD 
            implements ViewAreaAppsIface {

    private final String TABLA = "public.view_area_apps";
    private Connection conn = null;
    private LinkedHashMap<String,Object> datos = new LinkedHashMap<>();
    private LinkedList<String> atributos;
    private LinkedList<String> lst_apps;
    private LinkedList<ViewAreaApps> apps;

    public ViewAreaAppsServiceBD() {

        conn = new Conectar().leerConexion();
        atributos = ViewAreaApps.atributos();

    }

    public LinkedList<ViewAreaApps> datos()
    {
        String orderBy = "";
        String qry = String.format( "SELECT * from %s %s",TABLA, orderBy );

        try {
            
            ResultSet rs = leerDatos( qry );
            apps = new LinkedList<>();
        
            while ( rs.next() ) { apps.add( new ViewAreaApps( rs ) ); }

        } catch (SQLException sqle) {
            System.out.println( sqle.getMessage() );
            System.out.println( "Error al recuperar la tabla " ); 
        }

        return apps;
    }

    public LinkedList<String> registros( String orden ) 
    {

        String orderBy = orden.isEmpty() ? "" : String.format("order by %s", orden);
        String qry = String.format( "SELECT * from %s %s",TABLA, orderBy );

        try {

            ResultSet rs = leerDatos( qry );
            lst_apps = new LinkedList<>();
        
            while ( rs.next() ) { lst_apps.add( new ViewAreaApps( rs ).ViewAreaApps2Str() ); }

        } catch (SQLException sqle) {
            System.out.println( sqle.getMessage() );
            System.out.println( "Error al recuperar la tabla " ); 
        }

        return lst_apps;
        
    }
    private ResultSet leerDatos ( String qry ) 
        throws SQLException {

        if (conn == null) {
            //System.out.println( "leerDatos: Crear la conexión" );
            conn = new Conectar().leerConexion();
        }
        return conn.createStatement().executeQuery( qry );

    }

    @Override
    public LinkedList<String> atributos() {
        
        return ViewAreaApps.atributos();
        
    }

    @Override
    public LinkedHashMap<String, String> tipos() {
        return ViewAreaApps.tipos();
    }

}
