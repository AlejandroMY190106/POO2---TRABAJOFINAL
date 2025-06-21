package Modelo.Repository;

import Modelo.Documentos.OrdenTrabajo;
import java.util.List;

public interface OrdenTrabajoRepository {
    void guardar(OrdenTrabajo orden);
    List<OrdenTrabajo> obtenerTodas();
    OrdenTrabajo buscarPorId(int id);
    List<OrdenTrabajo> obtenerPorUsuario(int usuarioId);
}