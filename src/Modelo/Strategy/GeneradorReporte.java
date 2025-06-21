package Modelo.Strategy;

import Modelo.Documentos.Reporte;
import java.util.List;

public interface GeneradorReporte {
    void generarReporte(String nombrearchivo, List<Reporte> reportes);
}
