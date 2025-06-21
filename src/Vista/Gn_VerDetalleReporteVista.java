package Vista;

import Modelo.Documentos.Reporte;
import Modelo.Documentos.Inspección;

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

        add(izq,   BorderLayout.WEST);
        add(scroll, BorderLayout.CENTER);
    }
}
