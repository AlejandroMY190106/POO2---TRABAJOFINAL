package Modelo.Estructura;


public class Estado {
    private final double temperatura;                
    private final double humedad;                 
    private final double nivelCorrosion;         
        
    //CONSTRUCTOR

    public Estado(double temperatura, double humedad, double nivelCorrosion) {
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.nivelCorrosion = nivelCorrosion;
    }

    
    
    //GETTERS

    public double getTemperatura() {
        return temperatura;
    }

    public double getHumedad() {
        return humedad;
    }

    public double getNivelCorrosion() {
        return nivelCorrosion;
    }


    
    
    

}
