package Modelo.Estructura;

import Modelo.Observer.GestorAlertas;
import java.util.ArrayList;
import java.util.List;
import Modelo.Observer.ObservadorEstado;


public class ComponenteEstructural { //SUJETO DEL OBSERVER
    private int id;
    private String tipo;
    private Estado estado;
    private Estructura estructura;
    private List<ObservadorEstado> observadores = new ArrayList();

    //CONSTRUCTOR
    public ComponenteEstructural(int id, String tipo, Estado estado) {
        this.id = id;
        this.tipo = tipo;
        this.estado = estado;
        
        ObservadorEstado observador = new GestorAlertas();
        addObserver(observador);
    }


    
    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
        notifyObservers();

    }
    
    
    
    //METODOS
    
    public void addObserver(ObservadorEstado o) {
        if (o != null && !observadores.contains(o)) {
            observadores.add(o);
        }
    }

    public void removeObserver(ObservadorEstado o) {
        observadores.remove(o);
    }
    
    
    
    private void notifyObservers(){
        for (ObservadorEstado observador : observadores){
            observador.update(this.estado);
        }
    }
    
    
    public Estructura getEstructura() {
        return estructura;
    }

    public void setEstructura(Estructura estructura) {
        this.estructura = estructura;
    }
    
}
