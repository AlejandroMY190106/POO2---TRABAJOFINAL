package Main;

import Controlador.Controlador;
import Modelo.Estructura.ComponenteEstructural;
import Modelo.Estructura.Estado;
import Modelo.Repository.ComponenteEstructuralMySQLRepository;

import java.util.List;

import java.util.Random;

public class App {
    public static void main(String[] args) {
        ComponenteEstructuralMySQLRepository repo = new ComponenteEstructuralMySQLRepository();
        List<ComponenteEstructural> componentes = repo.obtenerTodas();
        if (componentes.isEmpty()) {
            System.out.println("No hay componentes registrados en la base de datos.");
            new Controlador();
            return;
        }

        // Tomamos el primer componente existente para generar estados aleatorios
        ComponenteEstructural componente = componentes.get(0);

        // Hilo que actualiza el estado del componente de forma aleatoria
        Thread generador = new Thread(() -> {
            Random rand = new Random();
            while (!Thread.currentThread().isInterrupted()) {
                double temp = -10 + rand.nextDouble() * 120; // -10 a 110 °C
                double hum = rand.nextDouble() * 100;        // 0 a 100 %
                double cor = rand.nextDouble() * 6;          // 0 a 6 índice
                componente.setEstado(new Estado(temp, hum, cor));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        generador.setDaemon(true);
        generador.start();

        new Controlador();
    }
}