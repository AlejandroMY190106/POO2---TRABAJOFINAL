package Modelo.Repository;

import Modelo.Documentos.Reporte;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteMySQLRepository implements ReporteRepository {

    public ReporteMySQLRepository() {
        try (Connection conn = ConnectionMySQL.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS reporte (" +
                "  id INT PRIMARY KEY AUTO_INCREMENT, " +
                "  contenido TEXT NOT NULL, " +
                "  fecha DATE NOT NULL, " +
                "  id_orden INT NOT NULL, " +
                "  CONSTRAINT fk_reporte_orden FOREIGN KEY (id_orden) REFERENCES orden_trabajo(id)" +
                ")"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardar(Reporte reporte) {
        String sql = "INSERT INTO reporte (contenido, fecha, id_orden) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, reporte.getContenido());
            stmt.setDate(2, new java.sql.Date(reporte.getFecha().getTime()));
            stmt.setInt(3, reporte.getOrdenTrabajo().getId());
            stmt.executeUpdate();

            ResultSet rsKeys = stmt.getGeneratedKeys();
            if (rsKeys.next()) {
                reporte.setId(rsKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Reporte> obtenerTodas() {
        List<Reporte> lista = new ArrayList<>();
        String sql = "SELECT * FROM reporte";

        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String contenido = rs.getString("contenido");
                Date fecha = rs.getDate("fecha");
                int idOrden = rs.getInt("id_orden");

                Reporte reporte = new Reporte(id, contenido, fecha, null, null);
                lista.add(reporte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public Reporte obtenerPorId(int id) {
        Reporte reporte = null;
        String sql = "SELECT * FROM reporte WHERE id = ?";
        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                reporte = new Reporte(
                    rs.getInt("id"),
                    rs.getString("contenido"),
                    rs.getDate("fecha"),
                    null,null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reporte;
    }

    @Override
    public List<Reporte> obtenerPorOrden(int ordenId) {
        List<Reporte> lista = new ArrayList<>();
        String sql = "SELECT * FROM reporte WHERE id_orden = ?";
        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ordenId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reporte reporte = new Reporte(
                    rs.getInt("id"),
                    rs.getString("contenido"),
                    rs.getDate("fecha"),
                    null,null
                );
                lista.add(reporte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}