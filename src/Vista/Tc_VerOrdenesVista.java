package Vista;

import Modelo.Documentos.OrdenTrabajo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Tc_VerOrdenesVista extends JFrame {
    private JTable tablaOrdenes;

    public Tc_VerOrdenesVista(List<OrdenTrabajo> ordenesDelTecnico) {
        setTitle("Órdenes Asignadas - Técnico");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel izquierdo
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(Color.WHITE);
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setPreferredSize(new Dimension(400, 0));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 20));

        JLabel titulo = new JLabel("ÓRDENES ASIGNADAS");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(0, 70, 140));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel("Consulta tus órdenes");
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitulo.setForeground(new Color(90, 90, 90));
        subtitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descripcion = new JLabel("<html>Revisa las órdenes de trabajo<br>que te han sido asignadas.</html>");
        descripcion.setFont(new Font("Arial", Font.PLAIN, 16));
        descripcion.setForeground(new Color(60, 60, 60));
        descripcion.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelIzquierdo.add(titulo);
        panelIzquierdo.add(subtitulo);
        panelIzquierdo.add(descripcion);

        // Panel derecho con tabla
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(Color.WHITE);
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(40, 10, 40, 40));

        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"ID", "Descripción", "Estado", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        for (OrdenTrabajo ot : ordenesDelTecnico) {
            modelo.addRow(new Object[]{
                    ot.getId(),
                    ot.getDescripcion(),
                    ot.getNivel(),
                    ot.getFecha()
            });
        }

        tablaOrdenes = new JTable(modelo);
        tablaOrdenes.setRowHeight(24);
        JScrollPane scroll = new JScrollPane(tablaOrdenes);
        scroll.setBorder(BorderFactory.createTitledBorder("Órdenes Asignadas"));

        panelDerecho.add(scroll, BorderLayout.CENTER);

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);

    }


}
