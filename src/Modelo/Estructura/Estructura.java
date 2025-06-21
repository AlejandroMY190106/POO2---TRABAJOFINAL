package Modelo.Estructura;

import java.util.ArrayList;
import java.util.List;

public class Estructura {
    private int id;
    private String nombre;
    private List<ComponenteEstructural> componentes = new ArrayList<>();
    
    //CONSTRUCTOR
    public Estructura(int id, String nombre, List<ComponenteEstructural> componentes) {
        this.id = id;
        this.nombre = nombre;
        this.componentes = componentes;
    }
    
    //GETTERS AND SETTERS    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ComponenteEstructural> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<ComponenteEstructural> componentes) {
        this.componentes = componentes;
    }
    
    //METODOS
    public void agregarComponente(ComponenteEstructural nuevoComponente){
        this.componentes.add(nuevoComponente);
    }
}
