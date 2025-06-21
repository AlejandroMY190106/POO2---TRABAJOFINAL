package Main;
/*
import Modelo.Documentos.Alerta;
import Modelo.Documentos.OrdenTrabajo;
import Modelo.Repository.AlertaArchivoRepository;
import Modelo.Repository.OrdenTrabajoMySQLRepository;
import Modelo.Repository.UsuarioMySQLRepository;
import Modelo.Usuarios.Técnico;
import Vista.AdministradorVista;
import Vista.AlertaVista;
import Vista.AsignarOrdenesSupervisorVista;
import Vista.GestionarUsuariosVista;
import Vista.LoginVista;
import Vista.SupervisorVista;
import Vista.TecnicoVista;
import Vista.VerOrdenesTecnicoVista;
import Vista.UpdateUserVista;
*/

public class PruevaVistas {

    public static void main(String[] args) {
        /*
        //El número del técnico creado varia depende de la orden
        OrdenTrabajoMySQLRepository ordenrepo = new OrdenTrabajoMySQLRepository();
        Técnico tecnico    = new Técnico(7,       "Luis Sánchez", "luis@dominio.com",   "pass123");
        List<OrdenTrabajo> ordentecnico = ordenrepo.obtenerPorUsuario(tecnico.getId());
        VerOrdenesTecnicoVista votv = new VerOrdenesTecnicoVista(ordentecnico);
        votv.setVisible(true);
        */
        
        /*
        TecnicoVista tv = new TecnicoVista();
        tv.setVisible(true);
        */
        
        /*
        SupervisorVista sv = new SupervisorVista();
        sv.setVisible(true);
        */
        
        /*
        LoginVista lv = new LoginVista();
        lv.setVisible(true);
        */
        
        /*
        GestionarUsuariosVista guv = new GestionarUsuariosVista();
        guv.setVisible(true);
        */
        
        /*
        UsuarioMySQLRepository usuariorepo = new UsuarioMySQLRepository();
        OrdenTrabajoMySQLRepository ordenrepo = new OrdenTrabajoMySQLRepository();
        List<Usuario> técnicos = usuariorepo.obtenerPorTipo("TÉCNICO");
        List<OrdenTrabajo> ordenes = ordenrepo.obtenerTodas();
        AsignarOrdenesSupervisorVista aosv = new AsignarOrdenesSupervisorVista(ordenes, técnicos);
        aosv.setVisible(true);
        */
        
        /*
        AlertaArchivoRepository alertarepo = new AlertaArchivoRepository();
        List<Alerta> alertas = alertarepo.obtenerTodas();
        AlertaVista av = new AlertaVista(alertas);
        av.setVisible(true);
        */
        
        /*
        AdministradorVista av = new AdministradorVista();
        av.setVisible(true);
        */

}

}
