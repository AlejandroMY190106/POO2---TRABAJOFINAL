package Modelo.Repository;

import Modelo.Documentos.Inspección;
import java.util.Date;
import java.util.List;

public interface InspeccionRepository {
    void guardar(Inspección inspeccion);
    List<Inspección> obtenerTodas();
    Inspección obtenerPorId(int id);
    List<Inspección> obtenerPorEstructura(int estructuraId);
    List<Inspección> obtenerPorFecha(Date fecha);
}