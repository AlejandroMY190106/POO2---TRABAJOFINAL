package Main;

import Modelo.Estructura.ComponenteEstructural;
import Modelo.Estructura.Estructura;
import Modelo.Estructura.Estado;
import java.util.ArrayList;
import java.util.List;

public class TrabajoFinalNuevo {
    public static void main(String[] args) {
        // 1. Crear una lista vacía de componentes para la estructura
        List<ComponenteEstructural> listaComponentes = new ArrayList<>();

        // 2. Crear la Estructura (id = 1, nombre = "Planta Principal")
        Estructura estructura = new Estructura(1, "Planta Principal", listaComponentes);

        // 3. Crear un Estado inicial para el componente (temperatura=25, humedad=50.0, corrosión=1.0)
        Estado estadoInicial = new Estado(25.0, 50.0, 1.0);

        // 4. Crear el ComponenteEstructural (id = 101, tipo = "Sensor Térmico", con el estado inicial)
        //    El constructor de ComponenteEstructural ya registra automáticamente un GestorAlertas como observador.
        ComponenteEstructural componente = new ComponenteEstructural(101, "Sensor Térmico", estadoInicial);

        // 5. Asociar el componente a la estructura
        componente.setEstructura(estructura);
        estructura.agregarComponente(componente);

        // (Opcional) Verificar el estado inicial:
        System.out.println("Estado inicial del componente:");
        System.out.println("  Temperatura = " + componente.getEstado().getTemperatura());
        System.out.println("  Humedad     = " + componente.getEstado().getHumedad());
        System.out.println("  Corrosión   = " + componente.getEstado().getNivelCorrosion());
        System.out.println();

        // 6. Cambiar el estado del componente para "disparar" notificaciones en GestorAlertas.
        //    Por ejemplo, ponemos valores extremos que cumplan las condiciones de alerta.
        Estado nuevoEstado = new Estado(100, 10.0, 5.5);
        System.out.println("Cambiando estado del componente a valores críticos...");
        componente.setEstado(nuevoEstado);

        // 7. (Opcional) Si quisieras ver en consola que la alerta se notifica, necesitarías 
        //    modificar GestorAlertas.update(...) para llamar a alertaActual.notificar() internamente.
        //    En la implementación actual, GestorAlertas.crearAlerta(...) guarda la Alerta pero no la imprime automáticamente.
        //    Por simplicidad, agregamos aquí una pequeña pausa antes de terminar el programa:
        try {
            Thread.sleep(500);  // 0.5 segundos
        } catch (InterruptedException ignored) { }

        System.out.println("Fin del flujo principal del programa.");
    }
}

