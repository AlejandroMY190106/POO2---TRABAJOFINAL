package Vista;

import Modelo.Documentos.Reporte;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class Gn_VerReportesVista extends JFrame {

    private JTable   tablaReportes;
    private JButton  btnVerDetalle, btnCerrar;

    public Gn_VerReportesVista(List<Reporte> reportes) {
        setTitle("Reportes Generados");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        /* ---------- Panel de marca ---------- */
        JPanel izq = buildBrandPanel("LISTADO DE REPORTES");

        /* ---------- Tabla de reportes ---------- */
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Descripción", "Fecha", "Inspecciones"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Reporte r : reportes) {
            modelo.addRow(new Object[]{
                    r.getId(),
                    r.getContenido(),
                    sdf.format(r.getFecha()),
                    r.getInspecciones() != null ? r.getInspecciones().size() : 0
            });
        }

        tablaReportes = new JTable(modelo);
        tablaReportes.setRowHeight(24);
        JScrollPane scroll = new JScrollPane(tablaReportes);
        scroll.setBorder(BorderFactory.createTitledBorder("Reportes disponibles"));

        /* ---------- Botones ---------- */
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnCerrar     = createRoundedButton("Cerrar");
        btnVerDetalle = createRoundedButton("Ver Detalle", true);
        botones.add(btnCerrar);
        botones.add(btnVerDetalle);

        /* ---------- Montaje final ---------- */
        JPanel der = new JPanel(new BorderLayout());
        der.setBackground(Color.WHITE);
        der.setBorder(BorderFactory.createEmptyBorder(40, 10, 40, 40));
        der.add(scroll, BorderLayout.CENTER);
        der.add(botones, BorderLayout.SOUTH);

        add(izq, BorderLayout.WEST);
        add(der, BorderLayout.CENTER);
    }

    /* ======================================================== */
    /* ===== Helpers de estilo (mismo formato del proyecto) ==== */
    private JPanel buildBrandPanel(String subtitulo) {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setPreferredSize(new Dimension(400, 0));
        p.setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 20));

        JLabel l1 = new JLabel("SISTEMA DE MANTENIMIENTO");
        l1.setFont(new Font("Arial", Font.BOLD, 24));
        l1.setForeground(new Color(0, 70, 140));
        JLabel l2 = new JLabel("ESTRUCTURAL");
        l2.setFont(new Font("Arial", Font.BOLD, 24));
        l2.setForeground(new Color(0, 70, 140));
        JLabel sub = new JLabel(subtitulo);
        sub.setFont(new Font("Arial", Font.ITALIC, 18));
        sub.setForeground(new Color(90, 90, 90));
        sub.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        p.add(l1); p.add(l2); p.add(sub);
        return p;
    }

    private JButton createRoundedButton(String txt) { return createRoundedButton(txt, false); }
    private JButton createRoundedButton(String txt, boolean primary) {
        JButton b = new JButton(txt) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(primary ? new Color(66, 103, 178) : new Color(245, 245, 245));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {
                g.setColor(Color.LIGHT_GRAY);
                ((Graphics2D) g).drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
            }
        };
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setForeground(primary ? Color.WHITE : Color.BLACK);
        b.setFont(new Font("Arial", primary ? Font.BOLD : Font.PLAIN, 16));
        b.setPreferredSize(new Dimension(200, 45));
        b.setFocusPainted(false);
        return b;
    }

    /* ================= Getters para el controlador =========== */
    public JTable  getTablaReportes() { return tablaReportes; }
    public JButton getBtnVerDetalle() { return btnVerDetalle; }
    public JButton getBtnCerrar()     { return btnCerrar; }
}
