package Vista;

import Modelo.Documentos.Reporte;
import Modelo.Documentos.Inspección;
import Modelo.Strategy.*;

import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class Gn_VerDetalleReporteVista extends JFrame {

    public Gn_VerDetalleReporteVista(Reporte r) {
        setTitle("Detalle del Reporte " + r.getId());
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        /* ---------- Panel de marca ---------- */
        JPanel izq = new JPanel();
        izq.setBackground(Color.WHITE);
        izq.setLayout(new BoxLayout(izq, BoxLayout.Y_AXIS));
        izq.setPreferredSize(new Dimension(400, 0));
        izq.setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 20));

        JLabel l1 = new JLabel("DETALLE DE REPORTE");
        l1.setFont(new Font("Arial", Font.BOLD, 24));
        l1.setForeground(new Color(0, 70, 140));
        JLabel l2 = new JLabel("ID: " + r.getId());
        l2.setFont(new Font("Arial", Font.PLAIN, 16));
        l2.setForeground(new Color(60, 60, 60));

        izq.add(l1);
        izq.add(Box.createRigidArea(new Dimension(0, 15)));
        izq.add(l2);

        /* ---------- Texto completo ---------- */
        JTextArea area = new JTextArea();
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        StringBuilder sb = new StringBuilder();
        sb.append("Fecha: ").append(sdf.format(r.getFecha())).append("\n");
        if (r.getOrdenTrabajo() != null) {
            sb.append("Orden de Trabajo: ").append(r.getOrdenTrabajo().getId()).append("\n");
        }
        sb.append("\n--- CONTENIDO ---\n")
                .append(r.getContenido()).append("\n");

        sb.append("\n--- INSPECCIONES ---\n");
        if (r.getInspecciones() != null && !r.getInspecciones().isEmpty()) {
            int i = 1;
            for (Inspección insp : r.getInspecciones()) {
                sb.append(i++).append(") ").append(insp.toString()).append("\n");
            }
        } else {
            sb.append("Sin inspecciones registradas.\n");
        }

        area.setText(sb.toString());

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 40));
        /* ---------- Panel de exportar ---------- */
        JPanel panelExportar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelExportar.setBackground(Color.WHITE);
        panelExportar.setBorder(BorderFactory.createEmptyBorder(0,0,20,20));
        JComboBox<String> comboFormato = new JComboBox<>(new String[]{"TXT", "CSV", "HTML", "JSON"});
        JButton btnGenerar = new JButton("Generar Reporte");
        panelExportar.add(comboFormato);
        panelExportar.add(btnGenerar);

        btnGenerar.addActionListener(ev -> {
            GestorReporte gestor = new GestorReporte();
            String formato = (String) comboFormato.getSelectedItem();
            switch (formato) {
                case "TXT": gestor.setGenerador(new GeneradorReporteTXT()); break;
                case "CSV": gestor.setGenerador(new GeneradorReporteCSV()); break;
                case "HTML": gestor.setGenerador(new GeneradorReporteHTML()); break;
                case "JSON": gestor.setGenerador(new GeneradorReporteJSON()); break;
            }

            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Guardar Reporte");
            int res = fc.showSaveDialog(Gn_VerDetalleReporteVista.this);
            if (res == JFileChooser.APPROVE_OPTION) {
                String path = fc.getSelectedFile().getAbsolutePath();
                int dot = path.lastIndexOf('.');
                if (dot > 0) {
                    path = path.substring(0, dot);
                }
                List<Reporte> lista = Arrays.asList(r);
                gestor.exportar(path, lista);
                JOptionPane.showMessageDialog(Gn_VerDetalleReporteVista.this,
                        "Reporte generado correctamente");
            }
        });
        add(izq,   BorderLayout.WEST);
        add(scroll, BorderLayout.CENTER);
        add(panelExportar, BorderLayout.SOUTH);
    }
}
