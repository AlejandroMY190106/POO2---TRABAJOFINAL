package Main;

import Modelo.Estructura.ComponenteEstructural;
import Modelo.Estructura.Estructura;
import Modelo.Estructura.Estado;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PruebaCursor {
    public static void main(String[] args) {
        // Crear estructura y componentes de ejemplo
        List<ComponenteEstructural> componentes = new ArrayList<>();
        Estructura estructura = new Estructura(1, "Estructura de Prueba", componentes);

        for (int i = 1; i <= 3; i++) {
            Estado estInicial = new Estado(25.0, 50.0, 1.0);
            ComponenteEstructural comp = new ComponenteEstructural(i, "Componente " + i, estInicial);
            comp.setEstructura(estructura);
            estructura.agregarComponente(comp);
        }

        Random rand = new Random();

        while (true) {
            for (ComponenteEstructural c : estructura.getComponentes()) {
                double temp = -10 + rand.nextDouble() * 120;   // -10 a 110 °C
                double hum  = rand.nextDouble() * 100;          // 0 a 100 %
                double cor  = rand.nextDouble() * 6;            // 0 a 6 índice

                Estado nuevo = new Estado(temp, hum, cor);
                c.setEstado(nuevo);  // Notifica a GestorAlertas
            }

            try {
                Thread.sleep(2000); // Esperar 2 segundos antes de la siguiente lectura
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
