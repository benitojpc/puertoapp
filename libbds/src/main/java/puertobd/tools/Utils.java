package puertobd.tools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Utils {

    public static final String DRIVER   = "org.postgresql.Driver";
    public static final String URL_DB   = "jdbc:postgresql://localhost:5432";
    public static final String DBNAME   = "gestusers";

    public static Object leerDeFichero(String rutaf) {
        
        try {
        
            // Reads data using the ObjectInputStream
            FileInputStream fis = new FileInputStream(rutaf);
            
            ObjectInputStream ois = new ObjectInputStream(fis);

            List<byte[]> datos = (List<byte[]>) ois.readObject(); 

            // datos.forEach( item -> {
            //     System.out.println(String.format("\tNombre: %s", Tools.AES.descifrar(item)));
            // });

            return datos;

        } catch (IOException | ClassNotFoundException e) {
            String msg = String.format("Error leyendo el fichero: %s", e.getMessage());
            System.out.println(msg);        
            return null;
        }

    }

    public static void guardarAFichero(Object datos, String rutaf) {
        
        try (final FileOutputStream fos = new FileOutputStream(rutaf)){
            
            try {
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(datos);

            } catch (IOException e) {
                String msg = String.format("Error al grabar lista de bytes: %s", e.getMessage());
                System.out.println(msg);        
            }

            if (fos != null) {
                fos.close();
            }
        
        }  catch (IOException e) {
            String msg = String.format("Error al crear fichero de cifrado: %s", e.getMessage());
            System.out.println(msg);
        }

    }
    
    public static void ficheroBinario(List<byte[]> datos, String rutaf) {
        
        try (final FileOutputStream fos = new FileOutputStream(rutaf)){
            
            datos.forEach( item -> {
                try {
                    fos.write(item);
                } catch (IOException e) {
                    String msg = String.format("Error al grabar lista de bytes: %s", e.getMessage());
                    System.out.println(msg);        
                }
            });
            
            if (fos != null) {
                fos.close();
            }
        
        }  catch (IOException e) {
            String msg = String.format("Error al crear fichero de cifrado: %s", e.getMessage());
            System.out.println(msg);
        }

    }

    public static String date2Str(Date d) {
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        String text = df.format(d);  
        return text;

    }


    public static String tstamp2Str(Timestamp d) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String timestampAsString = formatter.format(d.toLocalDateTime());

        return timestampAsString;

    }
    
    
    public static Date str2Date(String sd) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
         
            d = (Date)df.parse(sd);
            return d;
        
        } catch (ParseException e) {
            
            String msg = String.format("Error al convertir la fecha: %s", sd);
            System.out.println(String.format("%s%nError:%s", msg, e.getMessage()));
            return null;

        }

    }

    public static String bool2Str(Boolean b) {
        
        return (b ? "S" : "N");

    }

    public static Boolean str2Bool(String s) {

        return s.substring(0,1).equalsIgnoreCase("s");

    }
    
}
