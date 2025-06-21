package Modelo.Repository;

import Modelo.Documentos.OrdenTrabajo;
import Modelo.Estructura.Estructura;
import Modelo.Usuarios.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdenTrabajoMySQLRepository implements OrdenTrabajoRepository {

    public OrdenTrabajoMySQLRepository() {
        try (Connection conn = ConnectionMySQL.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS orden_trabajo (" +
                "  id INT PRIMARY KEY AUTO_INCREMENT, " +
                "  descripcion TEXT NOT NULL, " +
                "  nivel VARCHAR(20) NOT NULL, " +
                "  fecha DATE NOT NULL, " +
                "  id_usuario INT NOT NULL, " +
                "  id_estructura INT NOT NULL, " +
                "  CONSTRAINT fk_orden_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id), " +
                "  CONSTRAINT fk_orden_estructura FOREIGN KEY (id_estructura) REFERENCES estructuras(id)" +
                ")"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardar(OrdenTrabajo orden) {
        String sql = "INSERT INTO orden_trabajo (descripcion, nivel, fecha, id_usuario, id_estructura) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, orden.getDescripcion());
            stmt.setString(2, orden.getNivel());
            stmt.setDate(3, new java.sql.Date(orden.getFecha().getTime()));

            // Ahora obtenemos el ID de cualquier Usuario (Técnico o Supervisor)
            Usuario u = orden.getUsuarioAsignado();
            stmt.setInt(4, u.getId());

            stmt.setInt(5, orden.getEstructuraRelacionada().getId());
            stmt.executeUpdate();

            ResultSet rsKeys = stmt.getGeneratedKeys();
            if (rsKeys.next()) {
                orden.setId(rsKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<OrdenTrabajo> obtenerTodas() {
        List<OrdenTrabajo> lista = new ArrayList<>();
        String sql = "SELECT * FROM orden_trabajo";

        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            UsuarioMySQLRepository usuarioRepo = new UsuarioMySQLRepository();
            EstructuraMySQLRepository estructuraRepo = new EstructuraMySQLRepository();

            while (rs.next()) {
                int id = rs.getInt("id");
                String descripcion = rs.getString("descripcion");
                String nivel = rs.getString("nivel");
                Date fecha = rs.getDate("fecha");
                int usuarioId = rs.getInt("id_usuario");
                int estructuraId = rs.getInt("id_estructura");

                Usuario usuario = usuarioRepo.buscarPorId(Integer.toString(usuarioId));
                Estructura estructura = estructuraRepo.obtenerPorId(estructuraId);

                OrdenTrabajo orden = new OrdenTrabajo(id, descripcion, nivel, fecha, usuario, estructura, new ArrayList<>());
                lista.add(orden);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public OrdenTrabajo buscarPorId(int id) {
        OrdenTrabajo orden = null;
        String sql = "SELECT * FROM orden_trabajo WHERE id = ?";
        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int    ordenId      = rs.getInt("id");
                String descripcion  = rs.getString("descripcion");
                String nivel        = rs.getString("nivel");
                Date   fecha        = rs.getDate("fecha");
                int    usuarioId    = rs.getInt("id_usuario");
                int    estructuraId = rs.getInt("id_estructura");

                // Obtener el Usuario (podrá ser Técnico ó Supervisor automáticamente)
                Usuario usuario = new UsuarioMySQLRepository().buscarPorId(Integer.toString(usuarioId));

                // Obtener la Estructura
                Estructura estructura = new EstructuraMySQLRepository().obtenerPorId(estructuraId);

                // Pasamos lista vacía de inspecciones (puedes llenar luego con getInspecciones por FK)
                orden = new OrdenTrabajo(
                    ordenId,
                    descripcion,
                    nivel,
                    fecha,
                    usuario,
                    estructura,
                    new ArrayList<>()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orden;
    }

    @Override
    public List<OrdenTrabajo> obtenerPorUsuario(int usuarioId) {
        List<OrdenTrabajo> lista = new ArrayList<>();
        String sql = "SELECT * FROM orden_trabajo WHERE id_usuario = ?";
        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int    ordenId      = rs.getInt("id");
                String descripcion  = rs.getString("descripcion");
                String nivel        = rs.getString("nivel");
                Date   fecha        = rs.getDate("fecha");
                int    estrId       = rs.getInt("id_estructura");

                // Obtener el Usuario asignado (será Técnico o Supervisor)
                Usuario usuario = new UsuarioMySQLRepository().buscarPorId(Integer.toString(usuarioId));

                // Obtener la Estructura relacionada
                Estructura estructura = new EstructuraMySQLRepository().obtenerPorId(estrId);

                OrdenTrabajo orden = new OrdenTrabajo(
                    ordenId,
                    descripcion,
                    nivel,
                    fecha,
                    usuario,
                    estructura,
                    new ArrayList<>()
                );
                lista.add(orden);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
