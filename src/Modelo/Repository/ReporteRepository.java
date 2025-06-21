package Modelo.Repository;

import Modelo.Documentos.Reporte;
import java.util.List;

public interface ReporteRepository {
    void guardar(Reporte reporte);
    List<Reporte> obtenerTodas();
    Reporte obtenerPorId(int id);
    List<Reporte> obtenerPorOrden(int ordenId);
}