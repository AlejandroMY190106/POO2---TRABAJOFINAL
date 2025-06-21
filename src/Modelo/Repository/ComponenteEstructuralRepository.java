package Modelo.Repository;

import Modelo.Estructura.ComponenteEstructural;
import java.util.List;

public interface ComponenteEstructuralRepository {
    void guardar(ComponenteEstructural componente);
    List<ComponenteEstructural> obtenerTodas();
    ComponenteEstructural obtenerPorId(int id);
    List<ComponenteEstructural> obtenerPorEstructura(int estructuraId);
}