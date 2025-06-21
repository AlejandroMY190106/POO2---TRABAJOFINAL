package Modelo.Observer;

import Modelo.Documentos.Alerta;
import Modelo.Estructura.Estado;
import Modelo.Repository.AlertaArchivoRepository;

public class GestorAlertas implements ObservadorEstado {
    private Alerta alertaActual;
    private final AlertaArchivoRepository repo = new AlertaArchivoRepository();

    public void crearAlerta(String mensaje, String nivel) {
        alertaActual = new Alerta(1, mensaje, nivel);
        // Imprimir en consola
        System.out.println("ALERTA [" + nivel + "]: " + mensaje);
        // Guardar en el archivo en tiempo real
        repo.guardar(alertaActual);
    }

    @Override
    public void update(Estado estado) {
        double temperatura = estado.getTemperatura();
        double humedad = estado.getHumedad();
        double corrosion = estado.getNivelCorrosion();

        //TEMPERATURA NORMAL MENOR A 50 MAYOR QUE 0
        if (temperatura > 90) {
            crearAlerta("Temperatura EXCESIVA: " + temperatura + "°C", "ALTO");
        } else if (temperatura > 70) {
            crearAlerta("Temperatura alta: " + temperatura + "°C", "MEDIO");
        }else if (temperatura > 50) {
            crearAlerta("Temperatura moderadamente alta: " + temperatura + "°C", "BAJO");
        }else if (temperatura < 0) {
            crearAlerta("Temperatura por debajo de 0°C: " + temperatura + "°C", "MEDIO");
        }
        
        
        //HUMEDAD NORMAL ENTRE MAYOR A 20 MENOR QUE 50
        if (humedad > 80.0) {
            crearAlerta("Humedad MUY alta: " + humedad + "%", "ALTO");
        } else if (humedad > 50.0) {
            crearAlerta("Humedad moderada: " + humedad + "%", "MEDIO");
        } else {
            if (humedad < 20.0) {
                crearAlerta("Humedad MUY baja: " + humedad + "%", "MEDIO");
            }
        }

        //CORROSIÓN NORMAL MENOR A 0    
        if (corrosion >= 5.0) {
            crearAlerta("Corrosión CRÍTICA: " + corrosion, "CRÍTICO");
        } else if (corrosion >= 2.0) {
            crearAlerta("Corrosión moderada: " + corrosion, "MEDIO");
        }
    }
}
