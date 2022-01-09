package puertobd.services.interfaces;

import puertobd.entities.AreaApps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public interface ViewAreaAppsIface {

    LinkedList<String> atributos();
    LinkedHashMap<String,String> tipos();
    
}