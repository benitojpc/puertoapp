package puertobd.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conectar {

    private Properties props = null;
    private Connection conn = null;
    private String url;

    public Conectar() {

        this.url = String.format( "%s/%s", Utils.URL_DB, Utils.DBNAME );

        iniciar();

    }

    public Conectar(String url) {

        this.url = url;
        iniciar();

    }

    public void cerrar() {

        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());    
            }
        }

    }

    public Connection leerConexion() {

        if (this.conn == null) {
            this.url = String.format( "%s/%s", Utils.URL_DB, Utils.DBNAME );
            iniciar();
        }
        
        return this.conn;

    }

    private void iniciar() {

        propiedades();
        //System.out.println(String.format("Url conexión: %s", this.url));
        try {
            conn = DriverManager.getConnection(this.url, props);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private void propiedades() {

        props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","canoa@1");
        props.setProperty("ssl","false");

    }

}