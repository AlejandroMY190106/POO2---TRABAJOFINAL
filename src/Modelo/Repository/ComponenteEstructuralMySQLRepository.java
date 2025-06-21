package Modelo.Repository;

import Modelo.Estructura.ComponenteEstructural;
import Modelo.Estructura.Estado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComponenteEstructuralMySQLRepository implements ComponenteEstructuralRepository {

    public ComponenteEstructuralMySQLRepository() {
        try (Connection conn = ConnectionMySQL.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS componente_estructural (" +
                "  id INT PRIMARY KEY AUTO_INCREMENT, " +
                "  tipo VARCHAR(50) NOT NULL, " +
                "  temperatura DECIMAL(5,2) NOT NULL, " +
                "  humedad DECIMAL(5,2) NOT NULL, " +
                "  nivel_de_corrosion DECIMAL(5,2) NOT NULL, " +
                "  id_estructura INT NOT NULL, " +
                "  CONSTRAINT fk_componente_estructura FOREIGN KEY (id_estructura) REFERENCES estructuras(id)" +
                ")"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardar(ComponenteEstructural componente) {
        String sql = "INSERT INTO componente_estructural (tipo, temperatura, humedad, nivel_de_corrosion, id_estructura) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, componente.getTipo());
            stmt.setDouble(2, componente.getEstado().getTemperatura());
            stmt.setDouble(3, componente.getEstado().getHumedad());
            stmt.setDouble(4, componente.getEstado().getNivelCorrosion());
            stmt.setInt(5, componente.getEstructura().getId());
            stmt.executeUpdate();

            ResultSet rsKeys = stmt.getGeneratedKeys();
            if (rsKeys.next()) {
                componente.setId(rsKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<ComponenteEstructural> obtenerTodas() {
        List<ComponenteEstructural> lista = new ArrayList<>();
        String sql = "SELECT * FROM componente_estructural";

        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String tipo = rs.getString("tipo");
                double temperatura = rs.getDouble("temperatura");
                double humedad = rs.getDouble("humedad");
                double nivelCorrosion = rs.getDouble("nivel_de_corrosion");

                Estado estado = new Estado(temperatura, humedad, nivelCorrosion);
                ComponenteEstructural componente = new ComponenteEstructural(id, tipo, estado);
                lista.add(componente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public ComponenteEstructural obtenerPorId(int id) {
        ComponenteEstructural componente = null;
        String sql = "SELECT * FROM componente_estructural WHERE id = ?";
        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                componente = new ComponenteEstructural(
                    rs.getInt("id"),
                    rs.getString("tipo"),
                    new Estado(rs.getDouble("temperatura"),rs.getDouble("humedad"),rs.getDouble("nivel_de_corrosion")));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return componente;
    }

    @Override
    public List<ComponenteEstructural> obtenerPorEstructura(int estructuraId) {
        List<ComponenteEstructural> lista = new ArrayList<>();
        String sql = "SELECT * FROM componente_estructural WHERE id_estructura = ?";
        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, estructuraId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ComponenteEstructural componente = new ComponenteEstructural(
                    rs.getInt("id"),
                    rs.getString("tipo"),
                    new Estado(rs.getDouble("temperatura"),rs.getDouble("humedad"),rs.getDouble("nivel_de_corrosion")));
                lista.add(componente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}