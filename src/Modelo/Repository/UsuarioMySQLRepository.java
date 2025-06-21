package Modelo.Repository;

import Modelo.Usuarios.Usuario;
import Modelo.Usuarios.Administrador;
import Modelo.Usuarios.Supervisor;
import Modelo.Usuarios.Técnico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioMySQLRepository implements UsuarioRepository {

    public UsuarioMySQLRepository() {
        try (Connection conn = ConnectionMySQL.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS usuarios (" +
                "  id INT PRIMARY KEY AUTO_INCREMENT, " +
                "  nombre VARCHAR(100) NOT NULL, " +
                "  correo VARCHAR(150) NOT NULL, " +
                "  contraseña VARCHAR(100) NOT NULL, " +
                "  tipo VARCHAR(100) NOT NULL" +               // ← Nueva columna "tipo"
                ")"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, contraseña, tipo) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getContrasena());

            // Determinar el campo "tipo" según la instancia concreta de Usuario
            String tipo;
            if (usuario instanceof Administrador) {
                tipo = "ADMINISTRADOR";
            } else if (usuario instanceof Supervisor) {
                tipo = "SUPERVISOR";
            } else if (usuario instanceof Técnico) {
                tipo = "TÉCNICO";
            } else {
                // Si hay alguna otra subclase o caso por defecto
                tipo = "USUARIO";
            }
            stmt.setString(4, tipo);

            stmt.executeUpdate();

            // Obtener el ID generado y asignarlo al objeto usuario
            ResultSet rsKeys = stmt.getGeneratedKeys();
            if (rsKeys.next()) {
                usuario.setId(rsKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Usuario buscarPorId(String id) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int    usuarioId   = rs.getInt("id");
                String nombre      = rs.getString("nombre");
                String correo      = rs.getString("correo");
                String contraseña  = rs.getString("contraseña");
                String tipo        = rs.getString("tipo");

                // Según el valor de "tipo", instancio la subclase correspondiente
                switch (tipo.toUpperCase()) {
                    case "ADMINISTRADOR":
                        usuario = new Administrador(usuarioId, nombre, correo, contraseña);
                        break;
                    case "SUPERVISOR":
                        usuario = new Supervisor(usuarioId, nombre, correo, contraseña);
                        break;
                    case "TÉCNICO":
                        usuario = new Técnico(usuarioId, nombre, correo, contraseña);
                        break;
                    default:
                          usuario = null;
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public List<Usuario> obtenerTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = ConnectionMySQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int    usuarioId   = rs.getInt("id");
                String nombre      = rs.getString("nombre");
                String correo      = rs.getString("correo");
                String contraseña  = rs.getString("contraseña");
                String tipo        = rs.getString("tipo");

                Usuario usuario;
                switch (tipo.toUpperCase()) {
                    case "ADMINISTRADOR":
                        usuario = new Administrador(usuarioId, nombre, correo, contraseña);
                        break;
                    case "SUPERVISOR":
                        usuario = new Supervisor(usuarioId, nombre, correo, contraseña);
                        break;
                    case "TÉCNICO":
                        usuario = new Técnico(usuarioId, nombre, correo, contraseña);
                        break;
                    default:
                        usuario = null;
                        break;
                }
                lista.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    @Override
    public List<Usuario> obtenerPorTipo(String tipo) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE tipo = ?";

        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo.toUpperCase());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int usuarioId = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String contraseña = rs.getString("contraseña");

                Usuario usuario;
                switch (tipo.toUpperCase()) {
                    case "ADMINISTRADOR":
                        usuario = new Administrador(usuarioId, nombre, correo, contraseña);
                        break;
                    case "SUPERVISOR":
                        usuario = new Supervisor(usuarioId, nombre, correo, contraseña);
                        break;
                    case "TÉCNICO":
                        usuario = new Técnico(usuarioId, nombre, correo, contraseña);
                        break;
                    default:
                        usuario = null;
                        break;
                }

                if (usuario != null) {
                    lista.add(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
