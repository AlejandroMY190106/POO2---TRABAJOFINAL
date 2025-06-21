package Modelo.Repository;

import Modelo.Usuarios.Usuario;
import java.util.List;

public interface UsuarioRepository {
    void guardar(Usuario usuario);
    Usuario buscarPorId(String id);
    Usuario buscarPorCorreo(String correo);
    void actualizar(Usuario usuario);
    void eliminar(int id);
    List<Usuario> obtenerTodos();
    List<Usuario> obtenerPorTipo(String tipo);
}