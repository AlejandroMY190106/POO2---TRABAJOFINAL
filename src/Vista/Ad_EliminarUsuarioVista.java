package Vista;

import Modelo.Usuarios.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Ad_EliminarUsuarioVista extends JFrame {

    private JTable  tabla;
    private JButton btnEliminar, btnCerrar;

    public Ad_EliminarUsuarioVista(List<Usuario> usuarios) {
        setTitle("Eliminar Usuario");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(buildBrandPanel("ELIMINAR USUARIO"), BorderLayout.WEST);

        /* Tabla */
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID","Nombre","Correo","Rol"},0){
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        for (Usuario u : usuarios) {
            String rol = u instanceof Modelo.Usuarios.Administrador ? "Administrador"
                    : u instanceof Modelo.Usuarios.Supervisor    ? "Supervisor"
                    : "Técnico";
            modelo.addRow(new Object[]{u.getId(),u.getNombre(),u.getCorreo(),rol});
        }
        tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        JScrollPane sc = new JScrollPane(tabla);
        sc.setBorder(BorderFactory.createTitledBorder("Seleccione un usuario y elimine"));

        /* Botones */
        JPanel pie = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,5));
        btnCerrar   = createButton("Cerrar");
        btnEliminar = createButton("Eliminar", true);
        pie.add(btnCerrar); pie.add(btnEliminar);

        JPanel der = new JPanel(new BorderLayout());
        der.setBackground(Color.WHITE);
        der.setBorder(BorderFactory.createEmptyBorder(40,10,40,40));
        der.add(sc,  BorderLayout.CENTER);
        der.add(pie, BorderLayout.SOUTH);

        add(der, BorderLayout.CENTER);
    }

    /* helpers visuales (idénticos) */
    private JPanel buildBrandPanel(String subt){ /* mismo código que anteriores */
        JPanel p=new JPanel();
        p.setBackground(Color.WHITE);
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        p.setPreferredSize(new Dimension(400,0));
        p.setBorder(BorderFactory.createEmptyBorder(60,40,60,20));
        JLabel l1=new JLabel("GESTIÓN DE USUARIOS");
        l1.setFont(new Font("Arial",Font.BOLD,24));
        l1.setForeground(new Color(0,70,140));
        JLabel s=new JLabel(subt);
        s.setFont(new Font("Arial",Font.ITALIC,18));
        s.setForeground(new Color(90,90,90));
        s.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        p.add(l1); p.add(s); return p;
    }
    private JButton createButton(String t){return createButton(t,false);}
    private JButton createButton(String t,boolean primary){ /* igual */
        JButton b=new JButton(t){ @Override protected void paintComponent(Graphics g){
            Graphics2D g2=(Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(primary?new Color(66,103,178):new Color(245,245,245));
            g2.fillRoundRect(0,0,getWidth(),getHeight(),20,20); g2.dispose();
            super.paintComponent(g);} @Override protected void paintBorder(Graphics g){
            g.setColor(Color.LIGHT_GRAY);
            ((Graphics2D)g).drawRoundRect(0,0,getWidth()-1,getHeight()-1,20,20);}};
        b.setContentAreaFilled(false); b.setOpaque(false);
        b.setForeground(primary?Color.WHITE:Color.BLACK);
        b.setFont(new Font("Arial",primary?Font.BOLD:Font.PLAIN,16));
        b.setPreferredSize(new Dimension(140,45)); b.setFocusPainted(false); return b;
    }

    /* Getters */
    public JTable  getTabla()       { return tabla; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnCerrar()   { return btnCerrar; }
}
