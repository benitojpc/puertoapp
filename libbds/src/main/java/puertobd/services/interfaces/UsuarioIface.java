package puertobd.services.interfaces;

import puertobd.entities.Usuarios;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public interface UsuarioIface {

    Integer crearRegistro(Usuarios a);
    Integer crearRegistro(HashMap<String,Object> datos);

    HashMap<String,Object> actualizarRegistro(Usuarios a);
    HashMap<String,Object> actualizarRegistro(HashMap<String,Object> datos);

    Integer borrarRegistro(Integer id);

    Boolean existe(Integer id);
    Boolean existe(String s);

    String buscar(Object o);

    LinkedList<String> atributos();
    LinkedHashMap<String,String> tipos();

}