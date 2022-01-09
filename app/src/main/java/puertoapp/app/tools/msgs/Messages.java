package puertoapp.app.tools.msgs;

import java.util.HashMap;

public class Messages {
    
    private static HashMap<String,String> msgs      = new HashMap<>();
    private static HashMap<String,String> msgs_es   = new HashMap<>();
    static 
    {
        Messages.crearMsg();
        Messages.crearMsgEs();
    }
    
    private static void crearMsg() 
    {
        Messages.msgs.put("btn.cancel",   "Cancel");
        Messages.msgs.put("btn.validate", "Validate");

        Messages.msgs.put("msg.exit.title",   "CONFIRM APP CLOSE");
        Messages.msgs.put("msg.exit.header",  "The Application will be closed");
        Messages.msgs.put("msg.exit.context", "Are you sure to close the Application?");

        Messages.msgs.put("login.title", "USER VALIDATION");
        Messages.msgs.put("login.user",  "User");
        Messages.msgs.put("login.pass",  "Password");

        Messages.msgs.put("evalidate.title",   "ERROR USER VALIDATION");
        Messages.msgs.put("evalidate.header",  "Error when validating username and/or password");
        Messages.msgs.put("evalidate.context", "User's username and/or password are not valid");

        Messages.msgs.put("menu.entities", "Entities");
        Messages.msgs.put("menu.manage",   "App. Management");
        Messages.msgs.put("menu.help",     "Help");
        Messages.msgs.put("menui.close",   "Exit");
        Messages.msgs.put("menui.areas",   "Areas-Applications");
    }

    private static void crearMsgEs() 
    {
        Messages.msgs_es.put("btn.cancel",      "Cancelar");
        Messages.msgs_es.put("btn.validate",    "Validar");
        Messages.msgs_es.put("btn.close",       "Cerrar");
        Messages.msgs_es.put("btn.accept",      "Aceptar");
        Messages.msgs_es.put("btn.undo",        "Deshacer");
        Messages.msgs_es.put("btn.newareaapp",  "Nueva Área-App");
        Messages.msgs_es.put("btn.newarea",     "Nueva Área");

        Messages.msgs_es.put("msg.exit.title",   "CONFIRMAR CIERRE DE LA APLICACIÓN");
        Messages.msgs_es.put("msg.exit.header",  "Se procederá a cerrar la Aplicación");
        Messages.msgs_es.put("msg.exit.context", "¿Está seguro de querer cerrar la Aplicación?");

        Messages.msgs_es.put("login.title", "VALIDACIÓN DE USUARIO");
        Messages.msgs_es.put("login.user",  "Usuario");
        Messages.msgs_es.put("login.pass",  "Contraseña");

        Messages.msgs_es.put("evalidate.title",   "ERROR EN VALIDACIÓN DE USUARIO");
        Messages.msgs_es.put("evalidate.header",  "Error al validar nombre de usuario y/o contraseña del usuarios");
        Messages.msgs_es.put("evalidate.context", "El nombre de usuario y/o la contraseña no son válidos");

        Messages.msgs_es.put("menu.entities", "Entidades");
        Messages.msgs_es.put("menu.manage",   "Gestión Apps.");
        Messages.msgs_es.put("menu.help",     "Ayuda");
        Messages.msgs_es.put("menui.close",   "Salir");
        Messages.msgs_es.put("menui.areas",   "Áreas-Aplicaciones");

        Messages.msgs_es.put("vareaapps.title",     "Gestión de Aplicaciones y Rutas");
        Messages.msgs_es.put("vareaapps.tabview",   "Área-Aplicaciones");
        Messages.msgs_es.put("vareaapps.tabarea",   "Áreas");
        Messages.msgs_es.put("vareaapps.tabapps",   "Aplicaciones");

        Messages.msgs_es.put("vareaapps.tarea.title",   "Gestión de Áreas");
        Messages.msgs_es.put("vareaapps.tarea.area",    "Área");
        Messages.msgs_es.put("vareaapps.tarea.id",      "Área Id#");

        Messages.msgs_es.put("newareaapps.title", "Creación Área-Aplicación");
        Messages.msgs_es.put("newareaapps.app",   "Aplicación");
        Messages.msgs_es.put("newareaapps.area",  "Área");
        Messages.msgs_es.put("newareaapps.ruta",  "Ruta");
        Messages.msgs_es.put("newareaapps.notas", "Notas");

        Messages.msgs_es.put("newarea.title",       "Creación Área");
        Messages.msgs_es.put("newarea.header",      "Indique la denominación del área");
        Messages.msgs_es.put("newarea.area",        "Área");

        Messages.msgs_es.put( "tblvapp.idarea", "Id Area#" );
        Messages.msgs_es.put( "tblvapp.idapp",  "Id App#" );
        Messages.msgs_es.put( "tblvapp.area",   "Área" );
        Messages.msgs_es.put( "tblvapp.app",    "Aplic." );
        Messages.msgs_es.put( "tblvapp.ruta",   "Ruta" );
        Messages.msgs_es.put( "tblvapp.notas",  "Notas" );
        Messages.msgs_es.put( "tblvapp.fecha",  "Fecha" );
        
    }

    public static String getTexto( String key) { return Messages.texto(key, "es"); }
    public static String getTexto( String key, String local) { return Messages.texto(key, local); }

    private static String texto( String key, String local) { 

        String txt = "";

        switch (local.toLowerCase()) {
            case "en" -> txt = Messages.msgs.get( key );
            default   -> txt = Messages.msgs_es.get( key );
        }

        return txt;

    }

}
