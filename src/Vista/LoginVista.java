package vista;

import javax.swing.*;
import java.awt.*;

public class LoginVista extends JFrame {
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;
    private JButton botonIniciarSesion;

    public LoginVista() {
        setTitle("Sistema de Mantenimiento Estructural");
        setSize(900, 500); // Tamaño fijo
        setLocationRelativeTo(null); // Centrar en pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2)); // División izquierda-derecha

        // Panel izquierdo: título y descripción
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(Color.WHITE);
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 30));

        JLabel linea1 = new JLabel("SISTEMA DE MANTENIMIENTO");
        linea1.setFont(new Font("Arial", Font.BOLD, 22));
        linea1.setForeground(new Color(2, 97, 185));
        linea1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel linea2 = new JLabel("ESTRUCTURAL");
        linea2.setFont(new Font("Arial", Font.BOLD, 22));
        linea2.setForeground(new Color(2, 97, 185, 255));
        linea2.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descripcion = new JLabel("<html>Gestione el mantenimiento de<br>estructuras de forma eficiente.</html>");
        descripcion.setFont(new Font("Arial", Font.PLAIN, 16));
        descripcion.setForeground(Color.DARK_GRAY);
        descripcion.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        descripcion.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelIzquierdo.add(linea1);
        panelIzquierdo.add(linea2);
        panelIzquierdo.add(descripcion);

        // Panel derecho (contenedor con recuadro)
        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setBackground(Color.WHITE);

        // Panel formulario con borde
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        // Etiqueta y campo correo
        gbc.gridy = 0;
        JLabel etiquetaCorreo = new JLabel("Correo:");
        etiquetaCorreo.setFont(new Font("Arial", Font.PLAIN, 16));
        panelFormulario.add(etiquetaCorreo, gbc);

        gbc.gridy++;
        campoCorreo = new JTextField(20);
        campoCorreo.setFont(new Font("Arial", Font.PLAIN, 16));
        panelFormulario.add(campoCorreo, gbc);

        // Etiqueta y campo contraseña
        gbc.gridy++;
        JLabel etiquetaContrasena = new JLabel("Contraseña:");
        etiquetaContrasena.setFont(new Font("Arial", Font.PLAIN, 16));
        panelFormulario.add(etiquetaContrasena, gbc);

        gbc.gridy++;
        campoContrasena = new JPasswordField(20);
        campoContrasena.setFont(new Font("Arial", Font.PLAIN, 16));
        panelFormulario.add(campoContrasena, gbc);

        // Botón iniciar sesión
        gbc.gridy++;
        botonIniciarSesion = new JButton("Iniciar Sesión");
        botonIniciarSesion.setFont(new Font("Arial", Font.BOLD, 16));
        botonIniciarSesion.setBackground(new Color(94, 134, 227));
        botonIniciarSesion.setForeground(Color.WHITE);
        botonIniciarSesion.setFocusPainted(false);
        panelFormulario.add(botonIniciarSesion, gbc);

        panelDerecho.add(panelFormulario);

        // Añadir ambos lados
        add(panelIzquierdo);
        add(panelDerecho);
    }

    public String getCorreo() {
        return campoCorreo.getText();
    }

    public String getContrasena() {
        return new String(campoContrasena.getPassword());
    }

    public JButton getBotonIniciarSesion() {
        return botonIniciarSesion;
    }
}
