package Main;

import Controlador.Controlador;
import Modelo.Estructura.ComponenteEstructural;
import Modelo.Estructura.Estructura;
import Modelo.Estructura.Estado;
import Modelo.Repository.ComponenteEstructuralMySQLRepository;
import Modelo.Repository.EstructuraMySQLRepository;
import Modelo.Repository.ConnectionMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

        // Obtener y asignar la estructura a la que pertenece el componente
        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id_estructura FROM componente_estructural WHERE id=?")) {
            stmt.setInt(1, componente.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idEstructura = rs.getInt("id_estructura");
                EstructuraMySQLRepository estRepo = new EstructuraMySQLRepository();
                Estructura estructura = estRepo.obtenerPorId(idEstructura);
                componente.setEstructura(estructura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (componente.getEstructura() != null) {
            System.out.println("Componente ID " + componente.getId() + " pertenece a la estructura: "
                    + componente.getEstructura().getNombre() + " (ID=" + componente.getEstructura().getId() + ")");
        }

        // Hilo que actualiza el estado del componente de forma aleatoria
        Thread generador = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                double temp = -10 + new Random().nextDouble() * 120; // -10 a 110 °C
                double hum = new Random().nextDouble() * 100;        // 0 a 100 %
                double cor = new Random().nextDouble() * 6;          // 0 a 6 índice
                componente.setEstado(new Estado(temp, hum, cor));
                if (componente.getEstructura() != null) {
                    System.out.printf(
                            "Estructura: %s (ID=%d) -> Nuevo estado: T=%.2f°C, H=%.2f%%, C=%.2f%n",
                            componente.getEstructura().getNombre(),
                            componente.getEstructura().getId(),
                            temp, hum, cor);
                }
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