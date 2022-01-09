package puertoapp.app.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import puertoapp.app.models.VAreaAppsModel;
import puertoapp.app.tools.Vars;
import puertobd.entities.ViewAreaApps;
import puertobd.services.ViewAreaAppsServiceBD;

public class VAreaApps {

    private static ViewAreaAppsServiceBD vaas = new ViewAreaAppsServiceBD();

    public static void verDatos() {
        LinkedList<String> apps = vaas.registros( "" );
        apps.forEach( System.out::println );
    }

    public static ObservableList<VAreaAppsModel> datos() 
    {

        String fmtf = "dd/MM/yyyy HH:mm:ss";

        @SuppressWarnings("UnusedAssignment")
        ObservableList<VAreaAppsModel> lapps = null;
        
        @SuppressWarnings("UnusedAssignment")
        List<ViewAreaApps> apps = vaas.datos();

        //apps.forEach( ViewAreaApps::ViewAreaApps2Str );

        lapps = FXCollections.observableList( convertApps(apps) );

        //Comparator for String, by tipo
        Comparator<? super VAreaAppsModel> ordenar = 
            (VAreaAppsModel o1, VAreaAppsModel o2) -> ( o1.getFecha().compareTo(o2.getFecha())) ;
    
        Collections.sort(lapps, ordenar);
        ObservableList<VAreaAppsModel> olist = FXCollections.observableArrayList(lapps);
        return olist;

    }
    
    private static List<VAreaAppsModel> convertApps(List<ViewAreaApps> apps) 
    {

        List<VAreaAppsModel> lstapp = new ArrayList<>();
        apps.forEach((ViewAreaApps app) -> { lstapp.add( new VAreaAppsModel( app ));});
        return lstapp;
        
    }

    public static VAreaAppsModel convert(ViewAreaApps a)
    {

        VAreaAppsModel am = new VAreaAppsModel(a);
        return am;

    }

}
