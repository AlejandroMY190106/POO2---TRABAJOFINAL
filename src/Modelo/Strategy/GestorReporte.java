package Modelo.Strategy;

import Modelo.Documentos.Reporte;
import java.util.List;

public class GestorReporte {

    private GeneradorReporte generador;
    
    public void setGenerador(GeneradorReporte generador){
        //el objeto generador que se pasa como parámetro, podría ser
        //TXT, CSV, HTML o JSON
        this.generador = generador;
    }
    
    public void exportar(String nombreArchivo, List<Reporte> reportes){
        if(generador==null){
            System.out.println("Seleccione una estrategia de reporte...");
        }else{
            this.generador.generarReporte(nombreArchivo, reportes);
        }
    }
    
    
}
