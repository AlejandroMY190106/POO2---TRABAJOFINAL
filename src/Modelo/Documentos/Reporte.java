package Modelo.Documentos;

import java.util.Date;
import java.util.List;

public class Reporte {
    private int id;
    private String contenido;
    private Date fecha;
    private List<Inspección> inspecciones;
    private OrdenTrabajo ordenTrabajo;

    //CONSTRUCTOR
    public Reporte(int id, String contenido, Date fecha, List<Inspección> inspecciones, OrdenTrabajo ordenTrabajo) {
        this.id = id;
        this.contenido = contenido;
        this.fecha = fecha;
        this.inspecciones = inspecciones;
        this.ordenTrabajo = ordenTrabajo;
    }
    
    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Inspección> getInspecciones() {
        return inspecciones;
    }

    public void setInspecciones(List<Inspección> inspecciones) {
        this.inspecciones = inspecciones;
    }

    public OrdenTrabajo getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }
    
    //METODOS
    public void agregarInspección(Inspección nuevaInspección){
        this.inspecciones.add(nuevaInspección);
    }
}
