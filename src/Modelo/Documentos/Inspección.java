package Modelo.Documentos;

import Modelo.Estructura.Estructura;
import Modelo.Usuarios.Técnico;
import Modelo.Usuarios.Usuario;
import java.util.Date;

public class Inspección {
    private int id;
    private Date fecha;
    private Usuario usuario;
    private Estructura estructura;
    private OrdenTrabajo orden;
    private Reporte reporte;
    
    //CONSTRUCTOR
    public Inspección(int id, Date fecha, Usuario usuario, Estructura estructura, OrdenTrabajo orden, Reporte reporte) {
        this.id = id;
        this.fecha = fecha;
        this.usuario = usuario;
        this.estructura = estructura;
        this.orden = orden;
        this.reporte = reporte;
    }
    
    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Técnico usuario) {
        this.usuario = usuario;
    }

    public Estructura getEstructura() {
        return estructura;
    }

    public void setEstructura(Estructura estructura) {
        this.estructura = estructura;
    }

    public OrdenTrabajo getOrden() {
        return orden;
    }

    public void setOrden(OrdenTrabajo orden) {
        this.orden = orden;
    }

    public Reporte getReporte() {
        return reporte;
    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }
    
    
    
    //METODOS

    public void registrar() {
        System.out.println("Inspección Registrada");
    }
    
}
