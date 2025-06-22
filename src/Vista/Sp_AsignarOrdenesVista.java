package Vista;

import Modelo.Documentos.OrdenTrabajo;
import Modelo.Usuarios.Técnico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javax.swing.ListSelectionModel;

import java.awt.*;
import java.util.List;

public class Sp_AsignarOrdenesVista extends JFrame {
    private JTable tablaOrdenes;
    private JComboBox<Técnico> comboTecnicos;
    private JButton btnAsignar;

    public Sp_AsignarOrdenesVista(List<OrdenTrabajo> ordenes, List<Técnico> tecnicos) {
        setTitle("Asignar Orden de Trabajo - Supervisor");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel izquierdo (branding)
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(Color.WHITE);
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setPreferredSize(new Dimension(400, 0));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 20));

        JLabel titulo = new JLabel("ASIGNAR ORDEN");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(0, 70, 140));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel("Supervisor");
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitulo.setForeground(new Color(90, 90, 90));
        subtitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descripcion = new JLabel("<html>Seleccione una orden<br>y asígnele un técnico.</html>");
        descripcion.setFont(new Font("Arial", Font.PLAIN, 16));
        descripcion.setForeground(new Color(60, 60, 60));
        descripcion.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelIzquierdo.add(titulo);
        panelIzquierdo.add(subtitulo);
        panelIzquierdo.add(descripcion);

        // Panel derecho con tabla y formulario
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(Color.WHITE);
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(40, 10, 40, 40));

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Descripción", "Nivel", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (OrdenTrabajo ot : ordenes) {
            modelo.addRow(new Object[]{
                    ot.getId(),
                    ot.getDescripcion(),
                    ot.getNivel(),
                    ot.getFecha()
            });
        }

        tablaOrdenes = new JTable(modelo);
        tablaOrdenes.setRowHeight(24);
        tablaOrdenes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tablaOrdenes);
        scroll.setBorder(BorderFactory.createTitledBorder("Órdenes sin asignar"));

        comboTecnicos = new JComboBox<>(tecnicos.toArray(new Técnico[0]));
        btnAsignar = new JButton("Asignar");
        btnAsignar.setPreferredSize(new Dimension(120, 40));
        JPanel pie = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pie.add(comboTecnicos);
        pie.add(btnAsignar);

        panelDerecho.add(scroll, BorderLayout.CENTER);
        panelDerecho.add(pie, BorderLayout.SOUTH);

        btnAsignar.addActionListener(e -> {
            int fila = tablaOrdenes.getSelectedRow();
            Técnico tecnico = (Técnico) comboTecnicos.getSelectedItem();

            if (fila >= 0 && tecnico != null) {
                OrdenTrabajo orden = ordenes.get(fila);
                orden.setUsuarioAsignado(tecnico);
                JOptionPane.showMessageDialog(this,
                        "Orden asignada correctamente a " + tecnico.getNombre());
                modelo.removeRow(fila);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Seleccione una orden y un técnico.");
            }
        });

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);
    }
}