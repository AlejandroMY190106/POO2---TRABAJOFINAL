package Modelo.Documentos;

public class Alerta {
    private int id;
    private String mensaje;
    private String nivel;
    
    //CONSTRUCTOR
    public Alerta(int id, String mensaje, String nivel) {
        this.id = id;
        this.mensaje = mensaje;
        this.nivel = nivel;
    }
    
    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
    
    //METODOS
    
    private void notificar(){
        System.out.println("Notificando Alerta: "+this.mensaje);
    }
    
}
