package puertobd.services.interfaces;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import puertobd.entities.GruposUsu;

public interface GruposUsuariosIface {
    
    public Integer crearRegistro(GruposUsu um);
    public Integer crearRegistro(LinkedHashMap<String,Object> datos);

    HashMap<String,Object> actualizarRegistro(GruposUsu a);
    HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos);

    LinkedList<String> atributos();
    LinkedHashMap<String,String> tipos();

}
