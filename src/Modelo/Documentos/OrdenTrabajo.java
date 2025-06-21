// ----------------------
// Clase: OrdenTrabajo.java
// ----------------------

package Modelo.Documentos;

import Modelo.Estructura.Estructura;
import Modelo.Usuarios.Usuario;
import java.util.Date;
import java.util.List;

public class OrdenTrabajo {
    private int id;
    private String descripcion;
    private String Nivel;
    private Date fecha;
    private Usuario usuarioAsignado;       // ← Antes era “Técnico tecnicoAsignado”
    private Estructura estructuraRelacionada;
    private List<Inspección> inspecciones;

    // CONSTRUCTOR
    public OrdenTrabajo(int id,
                        String descripcion,
                        String nivel,
                        Date fecha,
                        Usuario usuarioAsignado,
                        Estructura estructuraRelacionada,
                        List<Inspección> inspecciones) {
        this.id = id;
        this.descripcion = descripcion;
        this.Nivel = nivel;
        this.fecha = fecha;
        this.usuarioAsignado = usuarioAsignado;
        this.estructuraRelacionada = estructuraRelacionada;
        this.inspecciones = inspecciones;
    }

    // GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivel() {
        return Nivel;
    }

    public void setNivel(String nivel) {
        this.Nivel = nivel;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuarioAsignado() {      // ← Ahora es Usuario
        return usuarioAsignado;
    }

    public void setUsuarioAsignado(Usuario usuarioAsignado) {
        this.usuarioAsignado = usuarioAsignado;
    }

    public Estructura getEstructuraRelacionada() {
        return estructuraRelacionada;
    }

    public void setEstructuraRelacionada(Estructura estructuraRelacionada) {
        this.estructuraRelacionada = estructuraRelacionada;
    }

    public List<Inspección> getInspecciones() {
        return inspecciones;
    }

    public void setInspecciones(List<Inspección> inspecciones) {
        this.inspecciones = inspecciones;
    }

    // Métodos
    @Override
    public String toString() {
        String nombreUsuario = (usuarioAsignado != null)
                               ? usuarioAsignado.getNombre()
                               : "Sin asignar";
        String nombreEstruct = (estructuraRelacionada != null)
                               ? estructuraRelacionada.getNombre()
                               : "Sin Estructura";

        return "OrdenTrabajo{" +
               "id=" + id +
               ", descripcion=" + descripcion +
               ", Nivel=" + Nivel +
               ", fecha=" + fecha +
               ", usuarioAsignado=" + nombreUsuario +
               ", estructuraRelacionada=" + nombreEstruct +
               '}';
    }

    public void agregarInspeccion(Inspección nuevaInspección) {
        this.inspecciones.add(nuevaInspección);
    }
}
