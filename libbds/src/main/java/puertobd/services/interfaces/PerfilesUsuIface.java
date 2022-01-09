package puertobd.services.interfaces;

import puertobd.entities.PerfilesUsu;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public interface PerfilesUsuIface {

    Integer crearRegistro(PerfilesUsu a);
    Integer crearRegistro(HashMap<String,Object> datos);

    HashMap<String,Object> actualizarRegistro(PerfilesUsu a);
    HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos);

    LinkedList<String> atributos();
    LinkedHashMap<String,String> tipos();
    
}