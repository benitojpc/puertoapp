package puertoapp.app.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import puertoapp.app.models.AreaModel;
import puertobd.entities.Areas;
import puertobd.services.AreaAppsServiceBD;
import puertobd.services.AreaServiceBD;

public class AreaService {

    private static AreaServiceBD ars = new AreaServiceBD();

    public static ObservableList<AreaModel> comboAreas() { return convert( ars.datos() ); }

    private static ObservableList<AreaModel> convert( LinkedList<Areas> areas ) 
    {

        LinkedList<AreaModel> amodel = new LinkedList<>();
        areas.forEach( area -> { amodel.add( convert( area ) ); } );
        //areas.forEach( System.out::println );

        ObservableList<AreaModel> lista = FXCollections.observableArrayList(amodel);
        return lista;

    }

    public static AreaModel buscar( Integer id ) 
    {
        Areas area = ars.encontrar( id );
        return convert( area );
    }

    public static Integer grabar( AreaModel am ) 
    {
        am.mostrar();
        return ars.crearRegistro( convert( am ) );
    }
    
    public static AreaModel convert(Areas a)
    {

        AreaModel am = new AreaModel(a);
        return am;

    }

    private static Areas convert( AreaModel am ) 
    {
        Areas a = new Areas();
        a.setId( null );
        a.setArea( am.areaProperty().getValue() );
        return a;
    }
    
}
