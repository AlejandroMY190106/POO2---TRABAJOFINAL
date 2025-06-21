package Vista;

import javax.swing.*;
import java.awt.*;

public class Sp_SupervisorVista extends JFrame {
    private JButton btnVerAlertas;
    private JButton btnAsignarOrden;
    private JButton btnVerReportes;
    private JButton btnCerrarSesion;

    public Sp_SupervisorVista() {
        setTitle("Supervisor - Sistema de Mantenimiento Estructural");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel izquierdo (branding)
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(Color.WHITE);
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setPreferredSize(new Dimension(400, 0));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 20));

        JLabel titulo1 = new JLabel("SISTEMA DE MANTENIMIENTO");
        titulo1.setFont(new Font("Arial", Font.BOLD, 24));
        titulo1.setForeground(new Color(0, 70, 140));
        titulo1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titulo2 = new JLabel("ESTRUCTURAL");
        titulo2.setFont(new Font("Arial", Font.BOLD, 24));
        titulo2.setForeground(new Color(0, 70, 140));
        titulo2.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subTitulo = new JLabel("Panel del Supervisor");
        subTitulo.setFont(new Font("Arial", Font.ITALIC, 18));
        subTitulo.setForeground(new Color(90, 90, 90));
        subTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        subTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descripcion = new JLabel("<html>Supervise alertas,<br>asigne órdenes y revise<br>reportes del sistema.</html>");
        descripcion.setFont(new Font("Arial", Font.PLAIN, 16));
        descripcion.setForeground(new Color(60, 60, 60));
        descripcion.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelIzquierdo.add(titulo1);
        panelIzquierdo.add(titulo2);
        panelIzquierdo.add(subTitulo);
        panelIzquierdo.add(descripcion);

        // Panel derecho (opciones)
        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setBackground(Color.WHITE);

        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        panelOpciones.setBackground(Color.WHITE);
        panelOpciones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(134, 134, 134, 255)),
                BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));

        // Crear botones
        btnVerAlertas = crearBoton("Ver Alertas");
        btnAsignarOrden = crearBoton("Asignar Orden de Trabajo");
        btnVerReportes = crearBoton("Ver Reportes");
        btnCerrarSesion = crearBoton("Cerrar Sesión", true);

        for (JButton btn : new JButton[]{btnVerAlertas, btnAsignarOrden, btnVerReportes, btnCerrarSesion}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelOpciones.add(btn);
            panelOpciones.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        panelDerecho.add(panelOpciones);

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto, boolean especial) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(especial ? new Color(66, 103, 178) : new Color(245, 245, 245));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(Color.LIGHT_GRAY);
                ((Graphics2D) g).drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }
        };

        boton.setContentAreaFilled(false);
        boton.setOpaque(false);
        boton.setForeground(especial ? Color.WHITE : Color.BLACK);
        boton.setFont(new Font("Arial", especial ? Font.BOLD : Font.PLAIN, 16));
        boton.setPreferredSize(new Dimension(220, 45));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setFocusPainted(false);
        return boton;
    }

    private JButton crearBoton(String texto) {
        return crearBoton(texto, false);
    }

    // Getters
    public JButton getBtnVerAlertas() {
        return btnVerAlertas;
    }

    public JButton getBtnAsignarOrden() {
        return btnAsignarOrden;
    }

    public JButton getBtnVerReportes() {
        return btnVerReportes;
    }

    public JButton getBtnCerrarSesion() {
        return btnCerrarSesion;
    }
}
