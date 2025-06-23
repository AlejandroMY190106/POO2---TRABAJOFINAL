package Modelo.Strategy;

import Modelo.Documentos.Inspección;
import Modelo.Documentos.OrdenTrabajo;
import Modelo.Documentos.Reporte;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.text.SimpleDateFormat;

public class GeneradorReporteJSON implements GeneradorReporte{
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public void generarReporte(String nombreArchivo, List<Reporte> reportes) {
        try (FileWriter fw = new FileWriter(nombreArchivo + ".json");
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("[");
            for (int i = 0; i < reportes.size(); i++) {
                Reporte r = reportes.get(i);
                pw.println("  {");
                // id
                pw.println("    \"id\": " + r.getId() + ",");
                // contenido
                pw.println("    \"contenido\": \"" + escaparJSON(r.getContenido()) + "\",");
                // fechaGeneracion (ISO 8601)
                String fechaStr = (r.getFecha() == null)
                                  ? ""
                                  : sdf.format(r.getFecha());
                pw.println("    \"fechaGeneracion\": \"" + fechaStr + "\",");

                // lista de inspecciones: arreglo de IDs
                List<Inspección> inspecciones = r.getInspecciones();
                pw.print("    \"inspecciones_ids\": [");
                if(inspecciones !=null){
                for (int j = 0; j < inspecciones.size(); j++) {
                    pw.print(inspecciones.get(j).getId());
                    if (j < inspecciones.size() - 1) {
                        pw.print(", ");
                    }
                }}else{
                    pw.print("null");
                }
                pw.println("],");

                // id de ordenTrabajo
                OrdenTrabajo ot = r.getOrdenTrabajo();
                pw.println("    \"ordenTrabajo_id\": " + (ot == null ? "null" : ot.getId()));

                pw.print("  }");
                if (i < reportes.size() - 1) {
                    pw.println(",");
                } else {
                    pw.println();
                }
            }
            pw.println("]");

        } catch (IOException e) {
            System.err.println("Error al generar el JSON: " + e.getMessage());
        }
    }

    // Método simple para escapar comillas y barras en JSON
    private String escaparJSON(String texto) {
        if (texto == null) return "";
        return texto.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("/", "\\/")
                    .replace("\b", "\\b")
                    .replace("\f", "\\f")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }
}
