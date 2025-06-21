package Vista;

import Modelo.Documentos.OrdenTrabajo;
import Modelo.Usuarios.Técnico;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Sp_AsignarOrdenesVista extends JFrame {
    private JComboBox<OrdenTrabajo> comboOrdenes;
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

        // Panel derecho con formulario
        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setBackground(Color.WHITE);
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblOrden = new JLabel("Orden de Trabajo:");
        comboOrdenes = new JComboBox<>(ordenes.toArray(new OrdenTrabajo[0]));

        JLabel lblTecnico = new JLabel("Técnico:");
        comboTecnicos = new JComboBox<>(tecnicos.toArray(new Técnico[0]));

        btnAsignar = new JButton("Asignar");
        btnAsignar.setPreferredSize(new Dimension(120, 40));

        gbc.gridx = 0; gbc.gridy = 0;
        panelDerecho.add(lblOrden, gbc);
        gbc.gridx = 1;
        panelDerecho.add(comboOrdenes, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelDerecho.add(lblTecnico, gbc);
        gbc.gridx = 1;
        panelDerecho.add(comboTecnicos, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        panelDerecho.add(btnAsignar, gbc);

        btnAsignar.addActionListener(e -> {
            OrdenTrabajo orden = (OrdenTrabajo) comboOrdenes.getSelectedItem();
            Técnico tecnico = (Técnico) comboTecnicos.getSelectedItem();

            if (orden != null && tecnico != null) {
                orden.setUsuarioAsignado(tecnico);
                JOptionPane.showMessageDialog(this, "Orden asignada correctamente a " + tecnico.getNombre());
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una orden y un técnico.");
            }
        });

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);
    }
}
