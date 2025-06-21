package Modelo.Repository;

import Modelo.Documentos.Alerta;
import java.util.List;

public interface AlertaRepository {
    void guardar(Alerta alerta);
    Alerta buscarPorId(int id);
    List<Alerta> obtenerTodas();
}