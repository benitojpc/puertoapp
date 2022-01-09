module puertobd {
    
    requires transitive java.sql; 
    
    exports puertobd.services;
    exports puertobd.services.interfaces;
    exports puertobd.entities;

    provides puertobd.services.interfaces.AplicacionIface 
        with puertobd.services.AplicacionServiceBD;
    provides puertobd.services.interfaces.AreaIface 
        with puertobd.services.AreaServiceBD;
    provides puertobd.services.interfaces.AreaAppsIface 
        with puertobd.services.AreaAppsServiceBD;
    provides puertobd.services.interfaces.ViewAreaAppsIface 
        with puertobd.services.ViewAreaAppsServiceBD;        
    provides puertobd.services.interfaces.AplicacionesUsuIface 
        with puertobd.services.AplicacionesUsuServiceBD;
    provides puertobd.services.interfaces.AplicacionesUsuMovsIface 
        with puertobd.services.AplicacionesUsuMovServiceBD;
    provides puertobd.services.interfaces.GrupoIface 
        with puertobd.services.GrupoServiceBD;
    provides puertobd.services.interfaces.GruposUsuariosIface 
        with puertobd.services.GruposUsuariosServiceBD;
    provides puertobd.services.interfaces.PerfilesAppIface 
        with puertobd.services.PerfilesAppServiceBD;
    provides puertobd.services.interfaces.PerfilesUsuIface 
        with puertobd.services.PerfilesUsuServiceBD;
    provides puertobd.services.interfaces.PerfilesUsuMovsIface 
        with puertobd.services.PerfilesUsuMovServiceBD;
    provides puertobd.services.interfaces.PuestoIface 
        with puertobd.services.PuestoServiceBD;
    provides puertobd.services.interfaces.PuestosUsuarioIface 
        with puertobd.services.PuestosUsuarioServiceBD;
    provides puertobd.services.interfaces.UsuarioIface 
        with puertobd.services.UsuarioServiceBD;
    provides puertobd.services.interfaces.UsuariosMovsIface 
        with puertobd.services.UsuariosMovsServiceBD;

}
