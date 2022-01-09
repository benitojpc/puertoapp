package puertobd.services.interfaces;

import puertobd.entities.AplicacionesUsuMovs;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public interface AplicacionesUsuMovsIface {

    Integer crearRegistro(AplicacionesUsuMovs a);
    Integer crearRegistro(HashMap<String,Object> datos);

    HashMap<String,Object> actualizarRegistro(AplicacionesUsuMovs a);
    HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos);
    
    LinkedList<String> atributos();
    LinkedHashMap<String,String> tipos();
    
}