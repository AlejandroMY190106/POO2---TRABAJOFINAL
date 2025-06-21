package Controlador;

import Modelo.Documentos.Alerta;
import Modelo.Documentos.OrdenTrabajo;
import Modelo.Documentos.Reporte;
import Modelo.Repository.*;
import Modelo.Usuarios.*;
import Vista.*;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador principal que enlaza todas las vistas del proyecto.
 * Implementa un flujo muy básico de navegación entre pantallas y
 * operaciones CRUD para usuarios utilizando los repositorios
 * disponibles. No pretende ser un sistema completo, pero permite
 * recorrer las diferentes ventanas tal como se especifica en el
 * esquema proporcionado.
 */
public class Controlador {

    private final UsuarioRepository usuarioRepo = new UsuarioMySQLRepository();
    private final OrdenTrabajoRepository ordenRepo = new OrdenTrabajoMySQLRepository();
    private final ReporteRepository reporteRepo = new ReporteMySQLRepository();
    private final AlertaRepository alertaRepo = new AlertaArchivoRepository();

    private LoginVista loginVista;
    private Ad_AdministradorVista adminVista;
    private Sp_SupervisorVista supervisorVista;
    private Tc_TecnicoVista tecnicoVista;

    /** Inicia la aplicación mostrando la pantalla de login */
    public Controlador() {
        SwingUtilities.invokeLater(this::mostrarLogin);
    }

    /* ============================ Login y selección de rol ================== */
    private void mostrarLogin() {
        loginVista = new LoginVista();
        loginVista.getBotonIniciarSesion().addActionListener(e -> validarLogin());
        loginVista.setVisible(true);
    }

    private void validarLogin() {
        String correo = loginVista.getCorreo();
        String pass   = loginVista.getContrasena();

        Usuario u = usuarioRepo.buscarPorCorreo(correo);
        if (u != null && pass != null && pass.equals(u.getContrasena())) {
            loginVista.dispose();
            if (u instanceof Administrador) {
                mostrarAdmin((Administrador) u);
            } else if (u instanceof Supervisor) {
                mostrarSupervisor((Supervisor) u);
            } else if (u instanceof Técnico) {
                mostrarTecnico((Técnico) u);
            }
        } else {
            JOptionPane.showMessageDialog(loginVista, "Credenciales inválidas");
        }
    }

    /* =============================== ADMINISTRADOR ========================== */
    private void mostrarAdmin(Administrador admin) {
        adminVista = new Ad_AdministradorVista();
        adminVista.getBtnGestionarUsuarios().addActionListener(e -> {
            adminVista.setVisible(false);
            mostrarGestionUsuarios();
        });
        adminVista.getBtnVerReportes().addActionListener(e -> mostrarListadoReportes());
        adminVista.getBtnCerrarSesion().addActionListener(e -> {
            adminVista.dispose();
            mostrarLogin();
        });
        adminVista.setVisible(true);
    }

    private void mostrarGestionUsuarios() {
        Ad_GestionarUsuariosVista gu = new Ad_GestionarUsuariosVista();
        gu.getBtnRegistrar().addActionListener(e -> mostrarCrearUsuario());
        gu.getBtnConsultar().addActionListener(e -> mostrarVisualizarUsuarios());
        gu.getBtnActualizar().addActionListener(e -> preguntarYActualizar());
        gu.getBtnVolver().addActionListener(e -> {
            gu.dispose();
            adminVista.setVisible(true);
        });
        gu.setVisible(true);
    }

    private void mostrarCrearUsuario() {
        Ad_CrearUsuarioVista v = new Ad_CrearUsuarioVista();
        v.getBtnRegistrar().addActionListener(e -> {
            try {
                int id = 1;
                String nombre = v.getTxtNombre().getText();
                String correo = v.getTxtCorreo().getText();
                String pass = new String(v.getTxtContrasena().getPassword());
                int tipo = v.getComboRol().getSelectedIndex();

                Usuario nuevo;
                switch (tipo) {
                    case 0: nuevo = new Administrador(id, nombre, correo, pass); break;
                    case 1: nuevo = new Supervisor(id, nombre, correo, pass); break;
                    default: nuevo = new Técnico(id, nombre, correo, pass); break;
                }
                usuarioRepo.guardar(nuevo);
                JOptionPane.showMessageDialog(v, "Usuario registrado");
                v.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(v, "ID inválido");
            }
        });
        v.getBtnCancelar().addActionListener(e -> v.dispose());
        v.setVisible(true);
    }

    private void mostrarVisualizarUsuarios() {
        List<Usuario> lista = usuarioRepo.obtenerTodos();
        Ad_VisualizarUsuarioVista v = new Ad_VisualizarUsuarioVista(lista);
        v.getBtnCerrar().addActionListener(e -> v.dispose());
        v.setVisible(true);
    }


    private void preguntarYActualizar() {
        String idStr = JOptionPane.showInputDialog("ID del usuario a actualizar:");
        if (idStr == null) return;
        Usuario u = usuarioRepo.buscarPorId(idStr);
        if (u == null) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado");
            return;
        }
        Ad_ActualizarUsuarioVista v = new Ad_ActualizarUsuarioVista(u);
        v.getBtnGuardar().addActionListener(e -> {
            u.setNombre(v.getTxtNombre().getText());
            u.setCorreo(v.getTxtCorreo().getText());
            u.setContrasena(new String(v.getTxtContrasena().getPassword()));
            int tipo = v.getComboRol().getSelectedIndex();
            Usuario actualizado;
            switch (tipo) {
                case 0: actualizado = new Administrador(u.getId(), u.getNombre(), u.getCorreo(), u.getContrasena()); break;
                case 1: actualizado = new Supervisor(u.getId(), u.getNombre(), u.getCorreo(), u.getContrasena()); break;
                default: actualizado = new Técnico(u.getId(), u.getNombre(), u.getCorreo(), u.getContrasena()); break;
            }
            usuarioRepo.actualizar(actualizado);
            JOptionPane.showMessageDialog(v, "Usuario actualizado");
            v.dispose();
        });
        v.getBtnCancelar().addActionListener(e -> v.dispose());
        v.setVisible(true);
    }

    /* =============================== SUPERVISOR ============================ */
    private void mostrarSupervisor(Supervisor sup) {
        supervisorVista = new Sp_SupervisorVista();
        supervisorVista.getBtnAsignarOrden().addActionListener(e -> mostrarAsignarOrden());
        supervisorVista.getBtnVerAlertas().addActionListener(e -> mostrarAlertas());
        supervisorVista.getBtnVerReportes().addActionListener(e -> mostrarListadoReportes());
        supervisorVista.getBtnCerrarSesion().addActionListener(e -> {
            supervisorVista.dispose();
            mostrarLogin();
        });
        supervisorVista.setVisible(true);
    }

    private void mostrarAsignarOrden() {
        List<OrdenTrabajo> ordenes = ordenRepo.obtenerTodas();
        List<OrdenTrabajo> sinAsignar = ordenes.stream()
                .filter(o -> o.getUsuarioAsignado() == null)
                .collect(Collectors.toList());
        List<Usuario> usuariosTec = usuarioRepo.obtenerPorTipo("TÉCNICO");
        List<Técnico> tecnicos = usuariosTec.stream()
                .filter(u -> u instanceof Técnico)
                .map(u -> (Técnico) u)
                .collect(Collectors.toList());
        Sp_AsignarOrdenesVista v = new Sp_AsignarOrdenesVista(sinAsignar, tecnicos);
v.setVisible(true);

    }

    /* ================================ TÉCNICO ============================== */
        private void mostrarTecnico(Técnico tec) {
        tecnicoVista = new Tc_TecnicoVista();
        tecnicoVista.getBtnVerOrdenes().addActionListener(e -> {
            List<OrdenTrabajo> ord = ordenRepo.obtenerPorUsuario(tec.getId());
            Tc_VerOrdenesVista v = new Tc_VerOrdenesVista(ord);
            v.setVisible(true);
        });
        tecnicoVista.getBtnVerAlertas().addActionListener(e -> mostrarAlertas());
        tecnicoVista.getBtnCerrarSesion().addActionListener(e -> {
            tecnicoVista.dispose();
            mostrarLogin();
        });
        tecnicoVista.setVisible(true);
    }

    /* ===================== Vistas Compartidas ============================= */
    private void mostrarAlertas() {
        List<Alerta> alertas = alertaRepo.obtenerTodas();
        Sp_AlertaVista v = new Sp_AlertaVista(alertas);
        v.setVisible(true);
    }

    private void mostrarListadoReportes() {
        List<Reporte> reps = reporteRepo.obtenerTodas();
        Gn_VerReportesVista v = new Gn_VerReportesVista(reps);
        v.getBtnCerrar().addActionListener(e -> v.dispose());
        v.getBtnVerDetalle().addActionListener(e -> {
            int fila = v.getTablaReportes().getSelectedRow();
            if (fila >= 0) {
                int id = (int) v.getTablaReportes().getValueAt(fila, 0);
                Reporte r = reporteRepo.obtenerPorId(id);
                new Gn_VerDetalleReporteVista(r).setVisible(true);
            }
        });
        v.setVisible(true);
    }
}

