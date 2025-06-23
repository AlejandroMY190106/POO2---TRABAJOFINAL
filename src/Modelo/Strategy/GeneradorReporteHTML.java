package Modelo.Strategy;

import Modelo.Documentos.Inspección;
import Modelo.Documentos.OrdenTrabajo;
import Modelo.Documentos.Reporte;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

public class GeneradorReporteHTML implements GeneradorReporte{
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void generarReporte(String nombreArchivo, List<Reporte> reportes) {
        try (FileWriter fw = new FileWriter(nombreArchivo + ".html");
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
            pw.println("<head>");
            pw.println("  <meta charset=\"UTF-8\">");
            pw.println("  <title>Reporte</title>");
            pw.println("  <style>");
            pw.println("    table { border-collapse: collapse; width: 100%; }");
            pw.println("    th, td { border: 1px solid #000; padding: 8px; text-align: left; }");
            pw.println("    th { background-color: #f2f2f2; }");
            pw.println("  </style>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("  <h1>Listado de Reportes</h1>");
            pw.println("  <table>");
            pw.println("    <thead>");
            pw.println("      <tr>");
            pw.println("        <th>id</th>");
            pw.println("        <th>Contenido</th>");
            pw.println("        <th>Fecha Generación</th>");
            pw.println("        <th>Inspecciones (IDs)</th>");
            pw.println("        <th>Orden Trabajo (ID)</th>");
            pw.println("      </tr>");
            pw.println("    </thead>");
            pw.println("    <tbody>");

            for (Reporte r : reportes) {
                pw.println("      <tr>");
                // id
                pw.println("        <td>" + r.getId() + "</td>");
                // contenido
                pw.println("        <td>" + escapeHTML(r.getContenido()) + "</td>");
                // fechaGeneracion
                String fechaStr = (r.getFecha() == null)
                                  ? ""
                                  : sdf.format(r.getFecha());
                pw.println("        <td>" + fechaStr + "</td>");
                // inspecciones: sólo IDs separados por coma
                List<Inspección> inspecciones = r.getInspecciones();
                StringBuilder idsInspecciones = new StringBuilder();
                if(inspecciones != null){
                for (int i = 0; i < inspecciones.size(); i++) {
                    idsInspecciones.append(inspecciones.get(i).getId());
                    if (i < inspecciones.size() - 1) {
                        idsInspecciones.append(", ");
                    }
                }}else{
                idsInspecciones.append("Sin inspecciones registradas");
                }
                pw.println("        <td>" + idsInspecciones.toString() + "</td>");
                // ordenTrabajo: solo ID
                OrdenTrabajo ot = r.getOrdenTrabajo();
                pw.println("        <td>" + (ot == null ? "No tiene una orden de Trabajo" : ot.getId()) + "</td>");

                pw.println("      </tr>");
            }

            pw.println("    </tbody>");
            pw.println("  </table>");
            pw.println("</body>");
            pw.println("</html>");

        } catch (IOException e) {
            System.err.println("Error al generar el HTML: " + e.getMessage());
        }
    }

    // Método de ayuda para escapar caracteres especiales en HTML
    private String escapeHTML(String texto) {
        if (texto == null) return "";
        return texto.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}
