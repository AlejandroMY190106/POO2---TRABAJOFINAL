package vista;

import Modelo.Documentos.Alerta;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AlertaVista extends JFrame {
    private JTable tablaAlertas;
    private DefaultTableModel modeloTabla;

    public AlertaVista(List<Alerta> alertas) {
        setTitle("Alertas - Sistema de Mantenimiento Estructural");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel de título
        JLabel titulo = new JLabel("Lista de Alertas Registradas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(200, 30, 30));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        // Definición columnas
        String[] columnas = {"Código", "Mensaje", "Nivel de Emergencia"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        };

        // Agregar filas con los datos de alertas
        for (Alerta alerta : alertas) {
            Object[] fila = {
                    alerta.getId(),
                    alerta.getMensaje(),
                    alerta.getNivel()
            };
            modeloTabla.addRow(fila);
        }

        tablaAlertas = new JTable(modeloTabla);
        tablaAlertas.setFillsViewportHeight(true);
        tablaAlertas.setRowHeight(25);
        tablaAlertas.getTableHeader().setReorderingAllowed(false);
        tablaAlertas.setFont(new Font("Tahoma", Font.PLAIN, 14));

        // Centrar texto en la columna "Nivel de Emergencia"
        tablaAlertas.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(CENTER);
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaAlertas);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Alertas"));
        add(scrollPane, BorderLayout.CENTER);
    }
}
