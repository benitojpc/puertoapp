package puertobd.services.interfaces;

import puertobd.entities.PerfilesApp;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public interface PerfilesAppIface {

    Integer crearRegistro(PerfilesApp a);
    Integer crearRegistro(HashMap<String,Object> datos);

    HashMap<String,Object> actualizarRegistro(PerfilesApp a);
    HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos);
    
    LinkedList<String> atributos();
    LinkedHashMap<String,String> tipos();
    
}