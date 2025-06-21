package Main;

import Modelo.Repository.*;
import Modelo.Usuarios.Administrador;
import Modelo.Usuarios.Supervisor;
import Modelo.Usuarios.Técnico;
import Modelo.Usuarios.Usuario;
import Modelo.Estructura.Estructura;
import Modelo.Estructura.ComponenteEstructural;
import Modelo.Estructura.Estado;
import Modelo.Documentos.Inspección;
import Modelo.Documentos.OrdenTrabajo;
import Modelo.Documentos.Reporte;
import Modelo.Documentos.Alerta;

import java.util.List;
import java.util.ArrayList;

public class GenerarBD {

    public static void main(String[] args) {
        // --- 1) Instanciar todos los repositorios (crean/validan tablas en BD) ---
        UsuarioMySQLRepository       usuarioRepo       = new UsuarioMySQLRepository();
        EstructuraMySQLRepository    estructuraRepo    = new EstructuraMySQLRepository();
        ComponenteEstructuralMySQLRepository componenteRepo = new ComponenteEstructuralMySQLRepository();
        OrdenTrabajoMySQLRepository  ordenRepo         = new OrdenTrabajoMySQLRepository();
        ReporteMySQLRepository       reporteRepo       = new ReporteMySQLRepository();
        InspeccionMySQLRepository    inspeccionRepo    = new InspeccionMySQLRepository();
        AlertaArchivoRepository alertaRepo = new AlertaArchivoRepository();

        System.out.println("Tablas creadas o verificadas.\n");

        // --- 2) Insertar USUARIOS (padres) ---
        // Ya tenías: Juan, María, Luis
        Administrador admin      = new Administrador(0, "Juan Pérez",   "juan@dominio.com",   "pass123");
        Supervisor     supervisor = new Supervisor(0,    "María Gómez",  "maria@dominio.com",  "pass123");
        Técnico        tecnico    = new Técnico(0,       "Luis Sánchez", "luis@dominio.com",   "pass123");

        usuarioRepo.guardar(admin);
        usuarioRepo.guardar(supervisor);
        usuarioRepo.guardar(tecnico);

        // Ahora agregamos 2 usuarios adicionales:
        Técnico tecnico2 = new Técnico(0, "Carla Torres", "carla@dominio.com", "pass1234");
        Supervisor supervisor2 = new Supervisor(0, "Andrés Rojas", "andres@dominio.com", "pass1234");

        usuarioRepo.guardar(tecnico2);
        usuarioRepo.guardar(supervisor2);

        System.out.println("Usuarios insertados:");
        System.out.println("  Administrador   ID = " + admin.getId());
        System.out.println("  Supervisor      ID = " + supervisor.getId());
        System.out.println("  Técnico         ID = " + tecnico.getId());
        System.out.println("  Técnico 2       ID = " + tecnico2.getId());
        System.out.println("  Supervisor 2    ID = " + supervisor2.getId() + "\n");

        // --- 3) Insertar ESTRUCTURAS (padres) ---
        // Tenías una “Planta Principal”; agregamos dos más:
        Estructura estr1 = new Estructura(0, "Planta Principal", new ArrayList<>());
        Estructura estr2 = new Estructura(0, "Almacén Secundario", new ArrayList<>());
        Estructura estr3 = new Estructura(0, "Taller de Mantenimiento", new ArrayList<>());

        estructuraRepo.guardar(estr1);
        estructuraRepo.guardar(estr2);
        estructuraRepo.guardar(estr3);

        System.out.println("Estructuras insertadas:");
        System.out.println("  ID=" + estr1.getId() + ", Nombre=" + estr1.getNombre());
        System.out.println("  ID=" + estr2.getId() + ", Nombre=" + estr2.getNombre());
        System.out.println("  ID=" + estr3.getId() + ", Nombre=" + estr3.getNombre() + "\n");

        // --- 4) Insertar COMPONENTES_ESTRUCTURALES (dependen de Estructura) ---
        // Un par de componentes para estr1, uno para estr2 y uno para estr3:
        Estado estA = new Estado(10.2, 50.0, 0.5);
        ComponenteEstructural comp1 = new ComponenteEstructural(0, "Sensor Térmico", estA);
        comp1.setEstructura(estr1);
        componenteRepo.guardar(comp1);

        Estado estB = new Estado(40.0, 30.0, 1.2);
        ComponenteEstructural comp2 = new ComponenteEstructural(0, "Sensor de Humedad", estB);
        comp2.setEstructura(estr1);
        componenteRepo.guardar(comp2);

        Estado estC = new Estado(10.0, 80.0, 0.2);
        ComponenteEstructural comp3 = new ComponenteEstructural(0, "Sensor de Corriente", estC);
        comp3.setEstructura(estr2);
        componenteRepo.guardar(comp3);

        Estado estD = new Estado(60.0, 20.0, 2.5);
        ComponenteEstructural comp4 = new ComponenteEstructural(0, "Sensor de Corrosión", estD);
        comp4.setEstructura(estr3);
        componenteRepo.guardar(comp4);

        System.out.println("Componentes_Estructurales insertados:");
        System.out.println("  ID=" + comp1.getId() + " (Estructura ID=" + estr1.getId() + ")");
        System.out.println("  ID=" + comp2.getId() + " (Estructura ID=" + estr1.getId() + ")");
        System.out.println("  ID=" + comp3.getId() + " (Estructura ID=" + estr2.getId() + ")");
        System.out.println("  ID=" + comp4.getId() + " (Estructura ID=" + estr3.getId() + ")\n");

        // --- 5) Insertar ORDENES_DE_TRABAJO (dependen de Usuario y Estructura) ---
        // Para estr1 asignamos dos órdenes a técnico y técnico2:
        OrdenTrabajo orden1 = new OrdenTrabajo(
                0,
                "Inspección preventiva mensual",
                "ALTA",
                new java.util.Date(),
                tecnico,
                estr1,
                new ArrayList<>()
        );
        ordenRepo.guardar(orden1);

        OrdenTrabajo orden2 = new OrdenTrabajo(
                0,
                "Revisión de componentes de alarma",
                "MEDIA",
                new java.util.Date(),
                tecnico2,
                estr1,
                new ArrayList<>()
        );
        ordenRepo.guardar(orden2);

        // Para estr2 una orden a supervisor:
        OrdenTrabajo orden3 = new OrdenTrabajo(
                0,
                "Inventario de equipos en Almacén Secundario",
                "BAJA",
                new java.util.Date(),
                supervisor,
                estr2,
                new ArrayList<>()
        );
        ordenRepo.guardar(orden3);

        // Para estr3 una orden a supervisor2:
        OrdenTrabajo orden4 = new OrdenTrabajo(
                0,
                "Reparación de motor en Taller de Mantenimiento",
                "ALTA",
                new java.util.Date(),
                supervisor2,
                estr3,
                new ArrayList<>()
        );
        ordenRepo.guardar(orden4);

        System.out.println("Órdenes de Trabajo insertadas:");
        System.out.println("  ID=" + orden1.getId() + " (Técnico ID=" + tecnico.getId()  + ", Estructura ID=" + estr1.getId() + ")");
        System.out.println("  ID=" + orden2.getId() + " (Técnico ID=" + tecnico2.getId() + ", Estructura ID=" + estr1.getId() + ")");
        System.out.println("  ID=" + orden3.getId() + " (Supervisor ID=" + supervisor.getId() + ", Estructura ID=" + estr2.getId() + ")");
        System.out.println("  ID=" + orden4.getId() + " (Supervisor2 ID=" + supervisor2.getId() + ", Estructura ID=" + estr3.getId() + ")\n");

        // --- 6) Insertar REPORTES (depende de OrdenTrabajo) ---
        // Un reporte por cada orden, con listas vacías de inspecciones:
        Reporte reporte1 = new Reporte(
                0,
                "Reporte de inspección mensual de estr1 - técnico 1",
                new java.util.Date(),
                new ArrayList<>(),
                orden1
        );
        reporteRepo.guardar(reporte1);

        Reporte reporte2 = new Reporte(
                0,
                "Reporte de revisión de alarmas - técnico 2",
                new java.util.Date(),
                new ArrayList<>(),
                orden2
        );
        reporteRepo.guardar(reporte2);

        Reporte reporte3 = new Reporte(
                0,
                "Reporte de inventario de Almacén Secundario",
                new java.util.Date(),
                new ArrayList<>(),
                orden3
        );
        reporteRepo.guardar(reporte3);

        Reporte reporte4 = new Reporte(
                0,
                "Reporte de reparación de motor - taller",
                new java.util.Date(),
                new ArrayList<>(),
                orden4
        );
        reporteRepo.guardar(reporte4);

        System.out.println("Reportes insertados:");
        System.out.println("  ID=" + reporte1.getId() + " (Orden ID=" + orden1.getId() + ")");
        System.out.println("  ID=" + reporte2.getId() + " (Orden ID=" + orden2.getId() + ")");
        System.out.println("  ID=" + reporte3.getId() + " (Orden ID=" + orden3.getId() + ")");
        System.out.println("  ID=" + reporte4.getId() + " (Orden ID=" + orden4.getId() + ")\n");

        // --- 7) Insertar INSPECCIONES (dependen de Estructura, Usuario, OrdenTrabajo y Reporte) ---
        // Creamos inspecciones que “unen” cada uno de los reportes y órdenes creadas:
        Inspección insp1 = new Inspección(
                0,
                new java.util.Date(),
                tecnico,
                estr1,
                orden1,
                reporte1
        );
        inspeccionRepo.guardar(insp1);

        Inspección insp2 = new Inspección(
                0,
                new java.util.Date(),
                tecnico2,
                estr1,
                orden2,
                reporte2
        );
        inspeccionRepo.guardar(insp2);

        Inspección insp3 = new Inspección(
                0,
                new java.util.Date(),
                supervisor,
                estr2,
                orden3,
                reporte3
        );
        inspeccionRepo.guardar(insp3);

        Inspección insp4 = new Inspección(
                0,
                new java.util.Date(),
                supervisor2,
                estr3,
                orden4,
                reporte4
        );
        inspeccionRepo.guardar(insp4);

        System.out.println("Inspecciones insertadas:");
        System.out.println("  ID=" + insp1.getId() + " (Técnico ID=" + tecnico.getId()   + ", Estructura ID=" + estr1.getId() + ", Orden ID=" + orden1.getId() + ", Reporte ID=" + reporte1.getId() + ")");
        System.out.println("  ID=" + insp2.getId() + " (Técnico2 ID=" + tecnico2.getId() + ", Estructura ID=" + estr1.getId() + ", Orden ID=" + orden2.getId() + ", Reporte ID=" + reporte2.getId() + ")");
        System.out.println("  ID=" + insp3.getId() + " (Supervisor ID=" + supervisor.getId()  + ", Estructura ID=" + estr2.getId() + ", Orden ID=" + orden3.getId() + ", Reporte ID=" + reporte3.getId() + ")");
        System.out.println("  ID=" + insp4.getId() + " (Supervisor2 ID=" + supervisor2.getId() + ", Estructura ID=" + estr3.getId() + ", Orden ID=" + orden4.getId() + ", Reporte ID=" + reporte4.getId() + ")\n");

        // --- 8) Insertar ALERTAS (sin FKs) ---
        //SE DEBEN GENERAR EN GESTOR ALERTA
        Alerta alerta1 = new Alerta(
                0,
                "Corrosión alta en componente ID=" + comp4.getId(),
                "ALTO"
        );
        alertaRepo.guardar(alerta1);

        Alerta alerta2 = new Alerta(
                0,
                "Humedad baja detectada en componente ID=" + comp3.getId(),
                "MEDIO"
        );
        alertaRepo.guardar(alerta2);

        System.out.println("Alertas insertadas:");
        System.out.println("  ID=" + alerta1.getId() + " (Mensaje: “Corrosión alta en componente ID=" + comp4.getId() + "”)");
        System.out.println("  ID=" + alerta2.getId() + " (Mensaje: “Humedad baja detectada en componente ID=" + comp3.getId() + "”)\n");

        // --- 9) Recuperar listas desde cada repositorio (verificación) ---
        List<Usuario> usuariosAll = usuarioRepo.obtenerTodos();
        System.out.println("Lista de TODOS los Usuarios en BD:");
        for (Usuario u : usuariosAll) {
            System.out.println("  • ID=" + u.getId() + ", Nombre=" + u.getNombre());
        }
        System.out.println();

        List<Estructura> estructurasAll = estructuraRepo.obtenerTodas();
        System.out.println("Lista de TODAS las Estructuras en BD:");
        for (Estructura e : estructurasAll) {
            System.out.println("  • ID=" + e.getId() + ", Nombre=" + e.getNombre());
        }
        System.out.println();

        List<ComponenteEstructural> compsAllEstr1 = componenteRepo.obtenerPorEstructura(estr1.getId());
        System.out.println("Componentes en Estructura ID=" + estr1.getId() + ":");
        for (ComponenteEstructural c : compsAllEstr1) {
            System.out.println("  • ID=" + c.getId() + ", Tipo=" + c.getTipo());
        }
        System.out.println();

        List<ComponenteEstructural> compsAllEstr2 = componenteRepo.obtenerPorEstructura(estr2.getId());
        System.out.println("Componentes en Estructura ID=" + estr2.getId() + ":");
        for (ComponenteEstructural c : compsAllEstr2) {
            System.out.println("  • ID=" + c.getId() + ", Tipo=" + c.getTipo());
        }
        System.out.println();

        List<OrdenTrabajo> ordenesPorTec1 = ordenRepo.obtenerPorUsuario(tecnico.getId());
        System.out.println("Órdenes de Trabajo del Técnico ID=" + tecnico.getId() + ":");
        for (OrdenTrabajo o : ordenesPorTec1) {
            System.out.println("  • ID=" + o.getId() + ", Descripción=" + o.getDescripcion());
        }
        System.out.println();

        List<OrdenTrabajo> ordenesPorTec2 = ordenRepo.obtenerPorUsuario(tecnico2.getId());
        System.out.println("Órdenes de Trabajo del Técnico ID=" + tecnico2.getId() + ":");
        for (OrdenTrabajo o : ordenesPorTec2) {
            System.out.println("  • ID=" + o.getId() + ", Descripción=" + o.getDescripcion());
        }
        System.out.println();

        List<OrdenTrabajo> ordenesPorSup1 = ordenRepo.obtenerPorUsuario(supervisor.getId());
        System.out.println("Órdenes de Trabajo del Supervisor ID=" + supervisor.getId() + ":");
        for (OrdenTrabajo o : ordenesPorSup1) {
            System.out.println("  • ID=" + o.getId() + ", Descripción=" + o.getDescripcion());
        }
        System.out.println();

        List<OrdenTrabajo> ordenesPorSup2 = ordenRepo.obtenerPorUsuario(supervisor2.getId());
        System.out.println("Órdenes de Trabajo del Supervisor ID=" + supervisor2.getId() + ":");
        for (OrdenTrabajo o : ordenesPorSup2) {
            System.out.println("  • ID=" + o.getId() + ", Descripción=" + o.getDescripcion());
        }
        System.out.println();

        List<Reporte> reportesPorOrden1 = reporteRepo.obtenerPorOrden(orden1.getId());
        System.out.println("Reportes para Orden ID=" + orden1.getId() + ":");
        for (Reporte r : reportesPorOrden1) {
            System.out.println("  • ID=" + r.getId() + ", Contenido=" + r.getContenido());
        }
        System.out.println();

        List<Reporte> reportesPorOrden2 = reporteRepo.obtenerPorOrden(orden2.getId());
        System.out.println("Reportes para Orden ID=" + orden2.getId() + ":");
        for (Reporte r : reportesPorOrden2) {
            System.out.println("  • ID=" + r.getId() + ", Contenido=" + r.getContenido());
        }
        System.out.println();

        List<Inspección> inspeccionesEstr1 = inspeccionRepo.obtenerPorEstructura(estr1.getId());
        System.out.println("Inspecciones para Estructura ID=" + estr1.getId() + ":");
        for (Inspección i : inspeccionesEstr1) {
            System.out.println("  • ID=" + i.getId() + ", Fecha=" + i.getFecha());
        }
        System.out.println();

        List<Inspección> inspeccionesEstr2 = inspeccionRepo.obtenerPorEstructura(estr2.getId());
        System.out.println("Inspecciones para Estructura ID=" + estr2.getId() + ":");
        for (Inspección i : inspeccionesEstr2) {
            System.out.println("  • ID=" + i.getId() + ", Fecha=" + i.getFecha());
        }
        System.out.println();

        List<Inspección> inspeccionesEstr3 = inspeccionRepo.obtenerPorEstructura(estr3.getId());
        System.out.println("Inspecciones para Estructura ID=" + estr3.getId() + ":");
        for (Inspección i : inspeccionesEstr3) {
            System.out.println("  • ID=" + i.getId() + ", Fecha=" + i.getFecha());
        }
        System.out.println();

        System.out.println("--- FIN DE PRUEBAS DE REPOSITORY AMPLIADAS ---");
    }
}

