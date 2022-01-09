package puertobd.services.interfaces;

import puertobd.entities.Areas;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;

public interface AreaIface {

    Integer crearRegistro(Areas a);
    Integer crearRegistro(HashMap<String,Object> datos);

    void cargarDatos(List<Object> datos);

    //LinkedHashMap<String,Object> Area(String qry);
    LinkedList<String> areas(String qry);
    HashMap<String,Object> actualizarRegistro(Areas a);
    HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos);

    Integer borrarRegistro(Integer id);

    Boolean existe(Integer id);
    Boolean existe(String s);

    String buscar(Object o);

    LinkedHashMap<String,String> tipos();
    
}