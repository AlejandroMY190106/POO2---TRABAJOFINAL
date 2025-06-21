package Modelo.Repository;

import Modelo.Estructura.Estructura;
import java.util.List;

public interface EstructuraRepository {
    void guardar(Estructura estructura);
    Estructura obtenerPorId(int id);
    List<Estructura> obtenerTodas();
}