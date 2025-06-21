package Vista;

import Modelo.Usuarios.Usuario;

import javax.swing.*;
import java.awt.*;

public class Ad_ActualizarUsuarioVista extends JFrame {

    private JTextField txtId, txtNombre, txtCorreo;
    private JPasswordField txtContrasena;
    private JComboBox<String> comboRol;
    private JButton btnGuardar, btnCancelar;

    public Ad_ActualizarUsuarioVista(Usuario u) {
        setTitle("Actualizar Usuario");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(buildBrandPanel("ACTUALIZAR USUARIO"), BorderLayout.WEST);

        /* ---------- Formulario ---------- */
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.anchor = GridBagConstraints.WEST;

        txtId         = new JTextField(String.valueOf(u.getId()), 15); txtId.setEditable(false);
        txtNombre     = new JTextField(u.getNombre(), 20);
        txtCorreo     = new JTextField(u.getCorreo(), 20);
        txtContrasena = new JPasswordField(u.getContrasena(), 20);
        comboRol      = new JComboBox<>(new String[]{"Administrador","Supervisor","Técnico"});
        /*Selecciona rol actual*/
        if (u instanceof Modelo.Usuarios.Administrador) comboRol.setSelectedIndex(0);
        else if (u instanceof Modelo.Usuarios.Supervisor) comboRol.setSelectedIndex(1);
        else comboRol.setSelectedIndex(2);

        addLabelAndComp(form, gbc, 0, "ID:",           txtId);
        addLabelAndComp(form, gbc, 1, "Nombre:",       txtNombre);
        addLabelAndComp(form, gbc, 2, "Correo:",       txtCorreo);
        addLabelAndComp(form, gbc, 3, "Contraseña:",   txtContrasena);
        addLabelAndComp(form, gbc, 4, "Rol:",          comboRol);

        /* Botones */
        JPanel pie = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnCancelar = createButton("Cancelar");
        btnGuardar  = createButton("Guardar cambios", true);
        pie.add(btnCancelar); pie.add(btnGuardar);

        add(form, BorderLayout.CENTER);
        add(pie,  BorderLayout.SOUTH);
    }

    /* ====== helpers (idénticos al archivo 1) ====== */
    private JPanel buildBrandPanel(String subtitulo) { /* igual que en Crear */
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setPreferredSize(new Dimension(400, 0));
        p.setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 20));

        JLabel l1 = new JLabel("GESTIÓN DE USUARIOS");
        l1.setFont(new Font("Arial", Font.BOLD, 24));
        l1.setForeground(new Color(0, 70, 140));
        JLabel sub = new JLabel(subtitulo);
        sub.setFont(new Font("Arial", Font.ITALIC, 18));
        sub.setForeground(new Color(90, 90, 90));
        sub.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

        p.add(l1); p.add(sub);
        return p;
    }
    private void addLabelAndComp(JPanel p, GridBagConstraints gbc,
                                 int y, String t, JComponent c) {
        gbc.gridy=y; gbc.gridx=0;
        JLabel l=new JLabel(t);
        l.setFont(new Font("Arial", Font.PLAIN, 16));
        p.add(l, gbc);
        gbc.gridx=1; p.add(c, gbc);
    }
    private JButton createButton(String txt){ return createButton(txt,false); }
    private JButton createButton(String txt, boolean primary) { /* igual al archivo 1 */
        JButton b = new JButton(txt) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2=(Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(primary?new Color(66,103,178):new Color(245,245,245));
                g2.fillRoundRect(0,0,getWidth(),getHeight(),20,20);
                g2.dispose(); super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {
                g.setColor(Color.LIGHT_GRAY);
                ((Graphics2D)g).drawRoundRect(0,0,getWidth()-1,getHeight()-1,20,20);
            }
        };
        b.setContentAreaFilled(false); b.setOpaque(false);
        b.setForeground(primary?Color.WHITE:Color.BLACK);
        b.setFont(new Font("Arial",primary?Font.BOLD:Font.PLAIN,16));
        b.setPreferredSize(new Dimension(180,45));
        b.setFocusPainted(false);
        return b;
    }

    /* Getters */
    public JTextField      getTxtNombre()      { return txtNombre; }
    public JTextField      getTxtCorreo()      { return txtCorreo; }
    public JPasswordField  getTxtContrasena()  { return txtContrasena; }
    public JComboBox<String> getComboRol()    { return comboRol; }
    public JButton         getBtnGuardar()     { return btnGuardar; }
    public JButton         getBtnCancelar()    { return btnCancelar; }
}
