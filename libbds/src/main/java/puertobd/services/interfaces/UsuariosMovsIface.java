package puertobd.services.interfaces;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import puertobd.entities.UsuariosMovs;

public interface UsuariosMovsIface {
    
    public Integer crearRegistro(UsuariosMovs um);
    public Integer crearRegistro(LinkedHashMap<String,Object> datos);

    LinkedList<String> atributos();
    LinkedHashMap<String,String> tipos();

}
