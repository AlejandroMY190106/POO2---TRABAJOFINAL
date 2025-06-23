package Modelo.Strategy;

import Modelo.Documentos.Inspección;
import Modelo.Documentos.OrdenTrabajo;
import Modelo.Documentos.Reporte;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

public class GeneradorReporteCSV implements GeneradorReporte{
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void generarReporte(String nombreArchivo, List<Reporte> reportes) {
        try (FileWriter fw = new FileWriter(nombreArchivo + ".csv");
             PrintWriter pw = new PrintWriter(fw)) {

            // Cabecera CSV
            pw.println("id" + ";" +
                       "contenido" + ";" +
                       "fechaGeneracion" + ";" +
                       "inspecciones_ids" + ";" +
                       "ordenTrabajo_id");

            for (Reporte r : reportes) {
                StringBuilder line = new StringBuilder();

                // 1) id del reporte
                line.append(r.getId()).append(";");

                // 2) contenido
                String contenido = r.getContenido().replaceAll("\"", "\"\"");
                line.append("\"").append(contenido).append("\"").append(";");

                // 3) fechaGeneracion (formato)
                String fechaStr = (r.getFecha() == null)
                                  ? ""
                                  : sdf.format(r.getFecha());
                line.append(fechaStr).append(";");

                // 4) lista de inspecciones: sólo los ids, separados por punto y coma
                List<Inspección> inspecciones = r.getInspecciones();
                StringBuilder idsInspecciones = new StringBuilder();
                if(inspecciones != null){
                for (int i = 0; i < inspecciones.size(); i++) {
                    idsInspecciones.append(inspecciones.get(i).getId());
                    if (i < inspecciones.size() - 1) {
                        idsInspecciones.append(";");
                    }
                }
                }else{
                    idsInspecciones.append("Sin inspecciones registradas;");
                }
                line.append(idsInspecciones.toString()).append(";");

                // 5) id de la orden de trabajo
                OrdenTrabajo ot = r.getOrdenTrabajo();
                line.append(ot == null ? "No tiene una orden de Trabajo" : ot.getId());

                pw.println(line.toString());
            }

        } catch (IOException e) {
            System.err.println("Error al generar el CSV: " + e.getMessage());
        }
    }
}
