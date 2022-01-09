package puertobd.services.interfaces;

import puertobd.entities.AplicacionesUsu;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public interface AplicacionesUsuIface {

    Integer crearRegistro(AplicacionesUsu a);
    Integer crearRegistro(HashMap<String,Object> datos);

    HashMap<String,Object> actualizarRegistro(AplicacionesUsu a);
    HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos);
    
    LinkedList<String> atributos();
    LinkedHashMap<String,String> tipos();
    
}