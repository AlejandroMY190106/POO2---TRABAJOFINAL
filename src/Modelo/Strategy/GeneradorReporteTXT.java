package Modelo.Strategy;

import Modelo.Documentos.Inspección;
import Modelo.Documentos.OrdenTrabajo;
import Modelo.Documentos.Reporte;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

public class GeneradorReporteTXT implements GeneradorReporte{
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void generarReporte(String nombreArchivo, List<Reporte> reportes) {
        try (FileWriter fw = new FileWriter(nombreArchivo + ".txt");
             PrintWriter pw = new PrintWriter(fw)) {

            for (Reporte r : reportes) {
                pw.println("=======================================");
                // 1) id
                pw.println("ID del Reporte: " + r.getId());
                // 2) contenido
                pw.println("Contenido:");
                pw.println(r.getContenido());
                // 3) fechaGeneracion
                String fechaStr = (r.getFecha() == null)
                                  ? ""
                                  : sdf.format(r.getFecha());
                pw.println("Fecha de Generación: " + fechaStr);
                // 4) lista de inspecciones (sólo IDs)
                pw.print("Inspecciones (IDs): ");
                List<Inspección> inspecciones = r.getInspecciones();
                if(inspecciones != null){
                for (int i = 0; i < inspecciones.size(); i++) {
                    pw.print(inspecciones.get(i).getId());
                    if (i < inspecciones.size() - 1) {
                        pw.print(", ");
                    }
                }
                }else{
                    pw.print("Sin inspecciones registradas");
                }
                
                pw.println();
                // 5) id de la orden de trabajo
                OrdenTrabajo ot = r.getOrdenTrabajo();
                pw.println("Orden de Trabajo: " + (ot == null ? "No tiene una orden de Trabajo" : ot.getId()));
                pw.println(); // línea en blanco para separar reportes
            }

        } catch (IOException e) {
            System.err.println("Error al generar el TXT: " + e.getMessage());
        }
    }
}
