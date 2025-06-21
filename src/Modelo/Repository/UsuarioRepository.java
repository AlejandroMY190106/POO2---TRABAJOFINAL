package Modelo.Repository;

import Modelo.Usuarios.Usuario;
import java.util.List;

public interface UsuarioRepository {
    void guardar(Usuario usuario);
    Usuario buscarPorId(String id);
    List<Usuario> obtenerTodos();
    List<Usuario> obtenerPorTipo(String tipo);
}