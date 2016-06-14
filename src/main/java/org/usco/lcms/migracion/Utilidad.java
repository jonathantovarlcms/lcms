package org.usco.lcms.migracion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;

import org.usco.lcms.migracion.conectores.ConectorPostgres;
import org.usco.lcms.migracion.conectores.ConectorSqlServer;
import org.usco.lcms.migracion.conectores.IConector;
import org.usco.lcms.migracion.constantes.TiposModeloBaseDatos;
import org.usco.lcms.migracion.modelo.IModelo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import commonj.timers.Timer;
import commonj.timers.TimerListener;
import commonj.timers.TimerManager;

public class Utilidad implements TimerListener {
	private static ArrayList<IConector> conectores;
	private static Integer ejecucion;
	private static final ReentrantReadWriteLock bloqueo = new ReentrantReadWriteLock();
	
	public void timerExpired(Timer timer) {
	      System.out.println("timer expired called on " + timer);
	      // some useful work here ...
	      // let's just cancel the timer
	      System.out.println("cancelling timer ...");
	      timer.cancel();
	}
	
	public IModelo prepararMigracion(IConector conectorOrigen, ArrayList<IConector> conectoresDestino) {
		IModelo modeloDestino;
		IModelo modeloOrigen = conectorOrigen.obtenerModelo();
		modeloOrigen.preparar();
		for(int i=0; i<conectoresDestino.size(); i++) {
			modeloDestino = conectoresDestino.get(i).obtenerModelo();
			modeloDestino.preparar();
		}
		return modeloOrigen;
	}
	/**
	 * Interpreta el archivo XML de configuracion
	 * @param archivo Archivo xml a interpretar
	 */
	private void interpretarXmlConfiguracion(File archivo) {
		if (archivo.exists()) {
			String motor, servidor, baseDatos, usuario, clave;
			try {
				FileInputStream sArchivo = new FileInputStream(archivo);
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(sArchivo);
				doc.getDocumentElement().normalize();
				NodeList listaBases = doc.getElementsByTagName("BaseDatos");
				for (int i = 0; i < listaBases.getLength(); i++) {
					Node nodoBase = listaBases.item(i);
					if (nodoBase.getNodeType() == Node.ELEMENT_NODE) {
						Element eBase = (Element)nodoBase;
						if (eBase.getElementsByTagName("Motor").getLength()>0) {
							motor = eBase.getElementsByTagName("Motor").item(0).getTextContent();
							servidor = eBase.getElementsByTagName("Servidor").item(0).getTextContent();
							baseDatos = eBase.getElementsByTagName("BaseDatos").item(0).getTextContent();
							usuario = eBase.getElementsByTagName("Usuario").item(0).getTextContent();
							clave = eBase.getElementsByTagName("Clave").item(0).getTextContent();
							if (motor.equals(TiposModeloBaseDatos.POSTGRES)) {
								conectores.add(new ConectorPostgres(servidor, baseDatos, usuario, clave));
							} else if (motor.equals(TiposModeloBaseDatos.SQLSERVER)) {
								conectores.add(new ConectorSqlServer(servidor, baseDatos, usuario, clave));
							}
						}
					}
				}
			} catch (FactoryConfigurationError | ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * @return Lista de conectos a bases de datos configuradas
	 */
	public ArrayList<IConector> obtenerConectores() {
		bloqueo.readLock().lock();
		if (conectores == null) {
			bloqueo.readLock().unlock();
			bloqueo.writeLock().lock();
			try {
				if (conectores == null) {
					conectores = new ArrayList<IConector>();
					
					File archivo = new File(Utilidad.class.getProtectionDomain().getCodeSource().getLocation().getPath());
					String rutaConfiguracion = archivo.getParent();
					this.interpretarXmlConfiguracion(new File(rutaConfiguracion + "/bases-datos.xml"));
					this.interpretarXmlConfiguracion(new File(rutaConfiguracion + "/bases-datos-priv.xml"));
					
					/*InitialContext contexto = new InitialContext();
					TimerManager tm = (TimerManager)contexto.lookup("java:comp/env/tm/default");
					tm.schedule (new Utilidad(), 0, 10*1000);*/
				}
			} finally {
				bloqueo.writeLock().unlock();
			}
		} else {
			bloqueo.readLock().unlock();
		}
		
		/*IModelo mSqlServer = cSqlServer.obtenerModelo();
		mSqlServer.preparar();
		
		StringBuilder retorno = mSqlServer.obtenerSql();
		
		return retorno.toString();*/
		return conectores;
	}
}
