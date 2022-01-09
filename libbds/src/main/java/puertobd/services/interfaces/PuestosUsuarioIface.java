package puertobd.services.interfaces;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import puertobd.entities.PuestosUsu;

public interface PuestosUsuarioIface {
    
    public Integer crearRegistro(PuestosUsu um);
    public Integer crearRegistro(LinkedHashMap<String,Object> datos);

    HashMap<String,Object> actualizarRegistro(PuestosUsu a);
    HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos);

    LinkedList<String> atributos();
    LinkedHashMap<String,String> tipos();

}
