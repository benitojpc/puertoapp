package puertobd.services.interfaces;

import puertobd.entities.Puestos;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;

public interface PuestoIface {

    Integer crearRegistro(Puestos a);
    Integer crearRegistro(HashMap<String,Object> datos);

    HashMap<String,Object> actualizarRegistro(Puestos a);
    HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos);

    Integer borrarRegistro(Integer id);

    Boolean existe(Integer id);
    Boolean existe(String s);
    
    String buscar(Object o);

    LinkedList<String> atributos();
    LinkedHashMap<String,String> tipos();
    
}