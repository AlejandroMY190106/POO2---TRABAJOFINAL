package Modelo.Repository;

import Modelo.Estructura.Estructura;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstructuraMySQLRepository implements EstructuraRepository {

    public EstructuraMySQLRepository() {
        try (Connection conn = ConnectionMySQL.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS estructuras (" +
                "  id INT PRIMARY KEY AUTO_INCREMENT, " +
                "  nombre VARCHAR(100) NOT NULL" +
                ")"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardar(Estructura estructura) {
        String sql = "INSERT INTO estructuras (nombre) VALUES (?)";
        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, estructura.getNombre());
            stmt.executeUpdate();

            ResultSet rsKeys = stmt.getGeneratedKeys();
            if (rsKeys.next()) {
                estructura.setId(rsKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    @Override
    public Estructura obtenerPorId(int id) {
        Estructura estructura = null;
        String sql = "SELECT * FROM estructuras WHERE id = ?";
        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                estructura = new Estructura(
                    rs.getInt("id"),
                    rs.getString("nombre"),null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estructura;
    }

    @Override
    public List<Estructura> obtenerTodas() {
        List<Estructura> lista = new ArrayList<>();
        String sql = "SELECT * FROM estructuras";
        try (Connection conn = ConnectionMySQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Estructura estructura = new Estructura(
                    rs.getInt("id"),
                    rs.getString("nombre"),null
                );
                lista.add(estructura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}