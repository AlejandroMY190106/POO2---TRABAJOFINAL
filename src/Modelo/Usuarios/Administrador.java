package Modelo.Usuarios;

import Modelo.Documentos.Reporte;
import java.util.ArrayList;
import java.util.List;

public class Administrador extends Usuario{
    
    List<Reporte> reportes = new ArrayList<>();
    
    //CONSTRUCTOR
    public Administrador(int id, String nombre, String correo, String contrasena) {
        super(id, nombre, correo, contrasena);
    }
    
    //METODOS
    public Usuario crearUsuario(int tipo, int id, String nombre, String correo, String contrasena) {
    // 1) Validar parámetros de entrada
    if (id <= 0 ||                         
        nombre == null || nombre.isEmpty() ||
        correo == null || correo.isEmpty() ||
        contrasena == null || contrasena.isEmpty() ||
        tipo < 0 || tipo > 2) {
        System.err.println("Parámetros inválidos al crear usuario");
        return null;
    }

    // 2) Crear la instancia concreta según el 'tipo'
    switch (tipo) {
        case 0:
            // Administrador
            return new Administrador(id, nombre, correo, contrasena);
        case 1:
            // Supervisor
            return new Supervisor(id, nombre, correo, contrasena);
        case 2:
            // Técnico
            return new Técnico(id, nombre, correo, contrasena);
        default:
            return null;
    }
}


    
    public List<Reporte> obtenerReportes(List<Reporte> listaReportes){
        reportes = listaReportes;
        return reportes;
    }
    
    
}
