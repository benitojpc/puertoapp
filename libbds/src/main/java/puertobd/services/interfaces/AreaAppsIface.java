package puertobd.services.interfaces;

import puertobd.entities.AreaApps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public interface AreaAppsIface {

    Integer crearRegistro(AreaApps a);
    Integer crearRegistro(HashMap<String,Object> datos);

    HashMap<String,Object> actualizarRegistro(AreaApps a);
    HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos);

    LinkedList<String> atributos();
    LinkedHashMap<String,String> tipos();
    
}