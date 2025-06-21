package Modelo.Repository;

import Modelo.Documentos.Inspección;
import Modelo.Documentos.OrdenTrabajo;
import Modelo.Documentos.Reporte;
import Modelo.Estructura.Estructura;
import Modelo.Usuarios.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InspeccionMySQLRepository implements InspeccionRepository {

    public InspeccionMySQLRepository() {
        try (Connection conn = ConnectionMySQL.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS inspeccion (" +
                "  id INT PRIMARY KEY AUTO_INCREMENT, " +
                "  fecha DATE NOT NULL, " +
                "  id_estructura INT NOT NULL, " +
                "  id_usuario INT NOT NULL, " +
                "  id_orden INT NOT NULL, " +
                "  id_reporte INT NOT NULL, " +
                "  CONSTRAINT fk_inspeccion_estructura FOREIGN KEY (id_estructura) REFERENCES estructuras(id), " +
                "  CONSTRAINT fk_inspeccion_usuario   FOREIGN KEY (id_usuario)    REFERENCES usuarios(id), " +
                "  CONSTRAINT fk_inspeccion_orden     FOREIGN KEY (id_orden)      REFERENCES orden_trabajo(id), " +
                "  CONSTRAINT fk_inspeccion_reporte   FOREIGN KEY (id_reporte)    REFERENCES reporte(id)" +
                ")"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardar(Inspección inspeccion) {
        String sql = "INSERT INTO inspeccion (fecha, id_estructura, id_usuario, id_orden, id_reporte) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            java.sql.Date sqlFecha = new java.sql.Date(inspeccion.getFecha().getTime());
            stmt.setDate(1, sqlFecha);
            stmt.setInt(2, inspeccion.getEstructura().getId());

            // Ahora el usuario puede ser Técnico o Supervisor (o cualquier subclase de Usuario)
            Usuario u = inspeccion.getUsuario();
            stmt.setInt(3, u.getId());

            stmt.setInt(4, inspeccion.getOrden().getId());
            stmt.setInt(5, inspeccion.getReporte().getId());

            stmt.executeUpdate();

            ResultSet rsKeys = stmt.getGeneratedKeys();
            if (rsKeys.next()) {
                inspeccion.setId(rsKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Inspección> obtenerTodas() {
        List<Inspección> lista = new ArrayList<>();
        String sql = "SELECT * FROM inspeccion";

        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            EstructuraMySQLRepository estructuraRepo = new EstructuraMySQLRepository();
            UsuarioMySQLRepository usuarioRepo = new UsuarioMySQLRepository();
            OrdenTrabajoMySQLRepository ordenRepo = new OrdenTrabajoMySQLRepository();
            ReporteMySQLRepository reporteRepo = new ReporteMySQLRepository();

            while (rs.next()) {
                int id          = rs.getInt("id");
                Date fecha      = rs.getDate("fecha");
                int idEstruct   = rs.getInt("id_estructura");
                int idUsuario   = rs.getInt("id_usuario");
                int idOrden     = rs.getInt("id_orden");
                int idReporte   = rs.getInt("id_reporte");

                Estructura estructura = estructuraRepo.obtenerPorId(idEstruct);
                Usuario usuario = usuarioRepo.buscarPorId(Integer.toString(idUsuario));
                OrdenTrabajo orden = ordenRepo.buscarPorId(idOrden);
                Reporte reporte = reporteRepo.obtenerPorId(idReporte);

                Inspección inspeccion = new Inspección(id, fecha, usuario, estructura, orden, reporte);
                lista.add(inspeccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public Inspección obtenerPorId(int id) {
        Inspección inspeccion = null;
        String sql = "SELECT * FROM inspeccion WHERE id = ?";
        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Date fecha = rs.getDate("fecha");
                int idEstruct = rs.getInt("id_estructura");
                int idUsuario = rs.getInt("id_usuario");
                int idOrden = rs.getInt("id_orden");
                int idReporte = rs.getInt("id_reporte");

                Estructura estructura = new EstructuraMySQLRepository().obtenerPorId(idEstruct);
                
                // Recuperamos el usuario (será Técnico o Supervisor según el campo “tipo”)
                Usuario usuario = new UsuarioMySQLRepository().buscarPorId(Integer.toString(idUsuario));

                OrdenTrabajo orden = new OrdenTrabajoMySQLRepository().buscarPorId(idOrden);
                Reporte reporte = new ReporteMySQLRepository().obtenerPorId(idReporte);

                inspeccion = new Inspección(id, fecha, usuario, estructura, orden, reporte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inspeccion;
    }

    @Override
    public List<Inspección> obtenerPorEstructura(int estructuraId) {
        List<Inspección> lista = new ArrayList<>();
        String sql = "SELECT * FROM inspeccion WHERE id_estructura = ?";

        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, estructuraId);
            ResultSet rs = stmt.executeQuery();

            EstructuraMySQLRepository estructuraRepo = new EstructuraMySQLRepository();
            UsuarioMySQLRepository usuarioRepo = new UsuarioMySQLRepository();
            OrdenTrabajoMySQLRepository ordenRepo = new OrdenTrabajoMySQLRepository();
            ReporteMySQLRepository reporteRepo = new ReporteMySQLRepository();

            while (rs.next()) {
                int id          = rs.getInt("id");
                Date fecha      = rs.getDate("fecha");
                int idEstruct   = rs.getInt("id_estructura");
                int idUsuario   = rs.getInt("id_usuario");
                int idOrden     = rs.getInt("id_orden");
                int idReporte   = rs.getInt("id_reporte");

                Estructura estructura = estructuraRepo.obtenerPorId(idEstruct);

                // Ya no casteamos a Técnico; el resultado puede ser Supervisor o Técnico
                Usuario usuario = usuarioRepo.buscarPorId(Integer.toString(idUsuario));

                OrdenTrabajo orden    = ordenRepo.buscarPorId(idOrden);
                Reporte reporte       = reporteRepo.obtenerPorId(idReporte);

                Inspección inspeccion = new Inspección(id, fecha, usuario, estructura, orden, reporte);
                lista.add(inspeccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public List<Inspección> obtenerPorFecha(Date fechaBuscada) {
        List<Inspección> lista = new ArrayList<>();
        String sql = "SELECT * FROM inspeccion WHERE fecha = ?";

        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            java.sql.Date sqlFecha = new java.sql.Date(fechaBuscada.getTime());
            stmt.setDate(1, sqlFecha);
            ResultSet rs = stmt.executeQuery();

            EstructuraMySQLRepository estructuraRepo = new EstructuraMySQLRepository();
            UsuarioMySQLRepository usuarioRepo = new UsuarioMySQLRepository();
            OrdenTrabajoMySQLRepository ordenRepo = new OrdenTrabajoMySQLRepository();
            ReporteMySQLRepository reporteRepo = new ReporteMySQLRepository();

            while (rs.next()) {
                int id          = rs.getInt("id");
                Date fecha      = rs.getDate("fecha");
                int idEstruct   = rs.getInt("id_estructura");
                int idUsuario   = rs.getInt("id_usuario");
                int idOrden     = rs.getInt("id_orden");
                int idReporte   = rs.getInt("id_reporte");

                Estructura estructura = estructuraRepo.obtenerPorId(idEstruct);

                // Obtenemos el Usuario en lugar de castear a Técnico
                Usuario usuario = usuarioRepo.buscarPorId(Integer.toString(idUsuario));

                OrdenTrabajo orden    = ordenRepo.buscarPorId(idOrden);
                Reporte reporte       = reporteRepo.obtenerPorId(idReporte);

                Inspección inspeccion = new Inspección(id, fecha, usuario, estructura, orden, reporte);
                lista.add(inspeccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
