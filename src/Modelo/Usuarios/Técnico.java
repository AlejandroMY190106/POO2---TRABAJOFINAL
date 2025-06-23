package Modelo.Usuarios;

import Modelo.Documentos.Alerta;
import Modelo.Documentos.OrdenTrabajo;
import java.util.ArrayList;
import java.util.List;

public class Técnico extends Usuario{
    
    List<Alerta> alertas = new ArrayList<>();
    
    //CONSTRUCTOR
    public Técnico(int id, String nombre, String correo, String contrasena) {
        super(id, nombre, correo, contrasena);
    }
    
    //METODOS
    public List<Alerta> verAlertas() {
        return alertas;
    }
    

    public void obtenerAlertas(List<Alerta> listaAlerta){
        alertas = listaAlerta;
    }

    @Override
    public String toString() {
        return super.getNombre();
    }
    
}
