package Modelo.Repository;

import Modelo.Documentos.OrdenTrabajo;
import java.util.List;

public interface OrdenTrabajoRepository {
    void guardar(OrdenTrabajo orden);
    void guardarsinUsuario(OrdenTrabajo orden);
    void asignarUsuario(OrdenTrabajo orden , int id);
    List<OrdenTrabajo> obtenerTodas();
    OrdenTrabajo buscarPorId(int id);
    List<OrdenTrabajo> obtenerPorUsuario(int usuarioId);
    void eliminar(int id);
}