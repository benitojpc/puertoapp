package puertobd.services.interfaces;

import puertobd.entities.Aplicaciones;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;

public interface AplicacionIface {

    Integer crearRegistro(Aplicaciones a);
    Integer crearRegistro(HashMap<String,Object> datos);

    void cargarDatos(List<Object> datos);

    //LinkedHashMap<String,Object> aplicaciones(String qry);
    LinkedList<String> aplicaciones(String qry);
    HashMap<String,Object> actualizarRegistro(Aplicaciones a);
    HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos);

    Integer borrarRegistro(Integer id);

    Boolean existe(Integer id);
    Boolean existe(String s);

    String buscar(Object o);

    LinkedHashMap<String,String> tipos();
    
}