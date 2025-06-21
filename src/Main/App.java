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

        int cantidad = Math.min(4, componentes.size());
        componentes = componentes.subList(0, cantidad);

        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id_estructura FROM componente_estructural WHERE id=?")) {
            EstructuraMySQLRepository estRepo = new EstructuraMySQLRepository();
            for (ComponenteEstructural c : componentes) {
                stmt.setInt(1, c.getId());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int idEstructura = rs.getInt("id_estructura");
                    Estructura estructura = estRepo.obtenerPorId(idEstructura);
                    c.setEstructura(estructura);
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (ComponenteEstructural c : componentes) {
            if (c.getEstructura() != null) {
                System.out.println("Componente ID " + c.getId() + " pertenece a la estructura: "
                        + c.getEstructura().getNombre() + " (ID=" + c.getEstructura().getId() + ")");
            }

            Thread generador = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    double temp = -10 + new Random().nextDouble() * 120; // -10 a 110 °C
                    double hum = new Random().nextDouble() * 100;        // 0 a 100 %
                    double cor = new Random().nextDouble() * 6;          // 0 a 6 índice
                    c.setEstado(new Estado(temp, hum, cor));
                    if (c.getEstructura() != null) {
                        System.out.printf(
                                "Estructura: %s (ID=%d) - Componente ID %d -> Nuevo estado: T=%.2f°C, H=%.2f%%, C=%.2f%n",
                                c.getEstructura().getNombre(),
                                c.getEstructura().getId(),
                                c.getId(),
                                temp, hum, cor);
                    }
                    try {
                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            generador.setDaemon(true);
            generador.start();
        }

        new Controlador();
    }
}