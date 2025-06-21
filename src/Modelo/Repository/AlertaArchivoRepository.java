package Modelo.Repository;

import Modelo.Documentos.Alerta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AlertaArchivoRepository implements AlertaRepository {

    // Ruta del archivo donde se guardarán las alertas
    private static final String RUTA_ARCHIVO = "alertas.txt";

    @Override
    public void guardar(Alerta alerta) {
        try {
            // Asegurarse de que exista el archivo (se crea si no existe)
            File archivo = new File(RUTA_ARCHIVO);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            // Obtener todas las alertas existentes para determinar el próximo ID
            List<Alerta> alertasExistentes = obtenerTodas();
            int maxId = 0;
            for (Alerta a : alertasExistentes) {
                if (a.getId() > maxId) {
                    maxId = a.getId();
                }
            }
            int nuevoId = maxId + 1;
            alerta.setId(nuevoId);

            // Abrir el archivo en modo "append" para agregar la nueva alerta al final
            try (FileWriter fw = new FileWriter(archivo, true);
                 BufferedWriter bw = new BufferedWriter(fw)) {

                // Formato: id;mensaje;nivel\n
                String linea = alerta.getId() + ";" + alerta.getMensaje() + ";" + alerta.getNivel();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Alerta buscarPorId(int id) {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            return null;
        }

        try (FileReader fr = new FileReader(archivo);
             BufferedReader br = new BufferedReader(fr)) {

            String linea;
            while ((linea = br.readLine()) != null) {
                // Cada línea tiene formato id;mensaje;nivel
                String[] partes = linea.split(";", 3);
                if (partes.length == 3) {
                    int idArchivo = Integer.parseInt(partes[0]);
                    if (idArchivo == id) {
                        String mensaje = partes[1];
                        String nivel = partes[2];
                        return new Alerta(idArchivo, mensaje, nivel);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Alerta> obtenerTodas() {
        List<Alerta> lista = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            return lista;
        }

        try (FileReader fr = new FileReader(archivo);
             BufferedReader br = new BufferedReader(fr)) {

            String linea;
            while ((linea = br.readLine()) != null) {
                // Cada línea tiene formato id;mensaje;nivel
                String[] partes = linea.split(";", 3);
                if (partes.length == 3) {
                    int idArchivo = Integer.parseInt(partes[0]);
                    String mensaje = partes[1];
                    String nivel = partes[2];
                    Alerta alerta = new Alerta(idArchivo, mensaje, nivel);
                    lista.add(alerta);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
