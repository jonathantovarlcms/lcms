package org.usco.lcms.migracion;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.usco.lcms.migracion.conectores.IConector;
import org.usco.lcms.migracion.definiciones.ConectarServidor;
import org.usco.lcms.migracion.definiciones.MigrarServidores;
import org.usco.lcms.migracion.modelo.IModelo;

@RestController
public class MigracionController {
	/**
	 * @return Devueleve la lista de servidores configurados
	 */
	@RequestMapping(value = "/migracion/servidores", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<IConector>> listaServidores() {
		
		Utilidad oUtilidad = new Utilidad();
		
		return new ResponseEntity<ArrayList<IConector>>(
				oUtilidad.obtenerConectores(), 
				HttpStatus.OK);
    }
	
	/**
	 * Realiza una prueba de conexión al servidor de base de datos
	 * 
	 * @param servidor
	 * @return Información de la prueba de conexión
	 */
	@RequestMapping(value = "/migracion/servidores/conectar", method = RequestMethod.POST)
    public ResponseEntity<ConectarServidor> conectar(@RequestBody ConectarServidor servidor) {
		String idServidor;
		IConector conectorServidor;
		Utilidad oUtilidad = new Utilidad();
		ArrayList<IConector> listaConectores = oUtilidad.obtenerConectores();
		
		servidor.setConectado(false);
		idServidor = servidor.getID();
		for(int j=0; j<listaConectores.size(); j++) {
			conectorServidor = listaConectores.get(j);
			if (conectorServidor.getID().equals(idServidor)) {
				try {
					if (conectorServidor.probarConexion()) {
						servidor.setConectado(true);							
					}
				} catch (ClassNotFoundException | SQLException e) {
					servidor.setMensaje(e.getMessage());
				}
				break;
			}
		}
		
		return new ResponseEntity<ConectarServidor>(
				servidor, 
				HttpStatus.OK);
    }
	
	/**
	 * @return 
	 */
	@RequestMapping(value = "/migracion/servidores/preparar", method = RequestMethod.POST)
    public ResponseEntity<IModelo> preparar(@RequestBody MigrarServidores servidores) {
		IConector conectorServidor, conectorOrigen = null;
		Utilidad oUtilidad = new Utilidad();
		ArrayList<IConector> conectoresDestino = new ArrayList<IConector>(); 
		ArrayList<IConector> listaConectores = oUtilidad.obtenerConectores();
		
		String idServidor = servidores.getServidorOrigen().getID();
		for(int j=0; j<listaConectores.size(); j++) {
			conectorServidor = listaConectores.get(j);
			if (conectorServidor.getID().equals(idServidor)) {
				conectorOrigen = conectorServidor; 
			}
		}
		for(int i=0; i<servidores.getServidoresDestino().size(); i++) {
			idServidor = servidores.getServidoresDestino().get(i).getID();
			for(int j=0; j<listaConectores.size(); j++) {
				conectorServidor = listaConectores.get(j);
				if (conectorServidor.getID().equals(idServidor)) {
					conectoresDestino.add(conectorServidor); 
				}
			}
		}
		IModelo retorno = oUtilidad.prepararMigracion(conectorOrigen, conectoresDestino);
		
		return new ResponseEntity<IModelo>(
				retorno, 
				HttpStatus.OK);
    }	
	
	@RequestMapping(value = "/migracion/", method = RequestMethod.GET)
    public ModelAndView conectar() {
		return new ModelAndView("hello");
    }
}
