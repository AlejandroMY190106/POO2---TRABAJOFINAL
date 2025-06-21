package Modelo.Usuarios;

import Modelo.Documentos.Alerta;
import Modelo.Documentos.OrdenTrabajo;
import Modelo.Documentos.Reporte;
import java.util.ArrayList;
import java.util.List;

public class Supervisor extends Usuario{
    
    List<Alerta> alertas = new ArrayList<>();
    List<Reporte> reportes = new ArrayList<>();
    
    //CONSTRUCTOR
    public Supervisor(int id, String nombre, String correo, String contrasena) {
        super(id, nombre, correo, contrasena);
    }
    
    //METODOS
    public List<Alerta> verAlertas() {
        return alertas;
    }
    
    public void asignarOrdenTrabajo(OrdenTrabajo orden){
        //COMPLETAR
    }
    
    public List<Reporte> verReportes(){
    return reportes;
    }
    
    public void obtenerAlertas(List<Alerta> listaAlerta){
        alertas = listaAlerta;
    }
    
    public void obtenerReportes(List<Reporte> listaReportes){
        reportes = listaReportes;
    }
    
    
    
}
