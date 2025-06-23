package Modelo.Usuarios;

import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String contraseña;
    
    //CONSTRUCTOR
    public Usuario(int id, String nombre, String correo, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contrasena;
    }

    public Usuario(int id) {
        this.id = id;
    }
    
    
    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contraseña;
    }

    public void setContrasena(String contrasena) {
        this.contraseña = contrasena;
    }
    
    //METODOS
    public List<Usuario> obtenerUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        return usuarios;
    }
    
    public boolean iniciarSesion(String correo, String contraseña){
        List<Usuario> usuarios = obtenerUsuarios();
        boolean verificar = false;
        for(Usuario u : usuarios){
            if(correo != null && contraseña != null &&
               correo.equals(u.getCorreo()) &&
               contraseña.equals(u.getContrasena())){
                verificar = true;
                break;
            }
        }
        return verificar;
    }

}
