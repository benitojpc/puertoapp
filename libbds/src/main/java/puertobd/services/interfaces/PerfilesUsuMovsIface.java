package puertobd.services.interfaces;

import puertobd.entities.PerfilesUsuMovs;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public interface PerfilesUsuMovsIface {

    Integer crearRegistro(PerfilesUsuMovs a);
    Integer crearRegistro(HashMap<String,Object> datos);

    HashMap<String,Object> actualizarRegistro(PerfilesUsuMovs a);
    HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos);

    LinkedList<String> atributos();
    LinkedHashMap<String,String> tipos();
    
}