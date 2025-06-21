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
        if (temperatura > 108) {
            crearAlerta("Temperatura EXCESIVA: " + temperatura + "°C", "ALTO");
        } else if (temperatura > 105) {
            crearAlerta("Temperatura alta: " + temperatura + "°C", "MEDIO");
        }else if (temperatura > 100) {
            crearAlerta("Temperatura moderadamente alta: " + temperatura + "°C", "BAJO");
        }else if (temperatura < 0) {
            crearAlerta("Temperatura por debajo de 0°C: " + temperatura + "°C", "MEDIO");
        }
        
        
        //HUMEDAD NORMAL ENTRE MAYOR A 10 MENOR QUE 95
        if (humedad > 97.0) {
            crearAlerta("Humedad MUY alta: " + humedad + "%", "ALTO");
        } else if (humedad > 95.0) {
            crearAlerta("Humedad alta: " + humedad + "%", "MEDIO");
        } else {
            if (humedad < 10.0) {
                crearAlerta("Humedad MUY baja: " + humedad + "%", "MEDIO");
            }
        }

        //CORROSIÓN NORMAL MENOR A 5   
        if (corrosion >= 5.5) {
            crearAlerta("Corrosión CRÍTICA: " + corrosion, "CRÍTICO");
        } else if (corrosion >= 5.0) {
            crearAlerta("Corrosión moderada: " + corrosion, "MEDIO");
        }
    }
}
