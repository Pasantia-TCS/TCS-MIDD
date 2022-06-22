package com.midd.core.administracion;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import org.springframework.stereotype.Service;

import com.midd.core.Exepciones.ActivoNoEncontradoExcepcion;
import com.midd.core.Exepciones.AreaNoEncontradoExcepcion;
import com.midd.core.modelo.Activos;
import com.midd.core.modelo.Area;
import com.midd.core.modelo.Edificio;
import com.midd.core.modelo.Tipo;
import com.midd.core.repositorio.ActivosRepo;
import com.midd.core.repositorio.AreaRepo;
import com.midd.core.repositorio.EdificioRepo;
import com.midd.core.repositorio.TipoRepo;

@Service
public class ServiciosActivos {
	private final ActivosRepo activosRepo;
	private final AreaRepo areaRepo;
	private final EdificioRepo edificioRepo;
	private final TipoRepo tipoRepo;

	// CONSTRUCTOR
	public ServiciosActivos(ActivosRepo activosRepo, AreaRepo areaRepo, EdificioRepo edificioRepo, TipoRepo tipoRepo) {
		this.activosRepo = activosRepo;
		this.areaRepo = areaRepo;
		this.edificioRepo = edificioRepo;
		this.tipoRepo = tipoRepo;
	}
	// FUNCION ANGREGAR ACTIVO
	public Activos agregarActivo(Activos activo) {
		return activosRepo.save(activo);
	}
	
	// FUNCION VALIDAD MAC REPETIDA
	public boolean validarMAC_repetida(String mac) {
		List<Activos> activos_conMac = activosRepo.findAll();
		// OBTENER TODOS LOS ACTIVOS CON MAC
		 for (Activos iterante : activosRepo.findAll()) {
			 if(iterante.getDireccion_mac() != null){
				activos_conMac.add(iterante);
			 }
		}
		// VALIDADCION DE MAC REPETIDA
		for (Activos iterante : activos_conMac) {
			if ((iterante.getDireccion_mac().equals(mac))&&(!iterante.isEstado()&&(!iterante.isBorrado_logico()))) {
				return false;
			}
		}
		return true;
	}

	// FUNCION VALIDAR IP
	public boolean isValidIPAddress(String ip){
		String zeroTo255 = 
			  "(\\d{1,2}|(0|1)\\"
			+ "d{2}|2[0-4]\\d|25[0-5])";

		String regex =
			  zeroTo255 + "\\."
			  + zeroTo255 + "\\."
			  + zeroTo255 + "\\."
			  + zeroTo255;

		Pattern pattern = Pattern.compile(regex);

		if (ip == null) {
			return false;
		}

		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}
	
	// FUNCION VALIDAD MAC
	public boolean isValidMacAddress(String mac){
		String regex = "^([0-9A-Fa-f]{2}[:-])"
						+ "{5}([0-9A-Fa-f]{2})|"
						+ "([0-9a-fA-F]{4}\\."
						+ "[0-9a-fA-F]{4}\\."
						+ "[0-9a-fA-F]{4})$";

		Pattern pattern = Pattern.compile(regex);

		if (mac == null) {
			return false;
		}

		Matcher matcher = pattern.matcher(mac);

		return matcher.matches();
	}

	// VALIDAD IP REPETIDA
	public boolean validarIPrepetida(String ip) {
		 List<Activos> activos_conIP = activosRepo.findAll();
		 // OBTENER TODOS LOS ACTIVOS CON IP
		 for (Activos iterante : activosRepo.findAll()) {
			 if(iterante.getDireccion_ip() != null){
				activos_conIP.add(iterante);
				
			 }
		 }
		 // VERIFICAR IP REPETIDA		
		for (Activos iterante : activos_conIP) {
			if ((iterante.getDireccion_ip().equals(ip))&&(!iterante.isEstado()&&(!iterante.isBorrado_logico()))) {
				return false;
			}
		}
		return true;
	}
	
	// FUNCION VALIADAR CODIGO DEL ACTIVO
	public boolean validarCodigoBarras(String codigo) {
		List<Activos> mis = activosRepo.findAll();
		for (Activos iterante : mis) {
			if((iterante.getCodigo_barras().equals(codigo))&&(!iterante.isEstado())&&(!iterante.isBorrado_logico())) {
				return true;
			}
		}
		return false;
	}
	
	// FUNCION ACTUALIZAR ACTIVO
	public Activos actualizarActivo(Activos activo) {
		return activosRepo.save(activo);
	}
	
	// FUNCION ELIMINAR ACTIVO
	public void eliminarActivo(Long id) {
		activosRepo.deleteById(id);
	}
	
	// FUNCION BUSCAR TODOS LOS ACTIVOS
	public List<Activos> buscarTodos() {
		return activosRepo.findAll();
	}
	
	// FUNCION BUSCAR ACTIVO POR ID
	public Activos buscarPorId(Long id) {
		return activosRepo.findById(id)
				.orElseThrow(()->new ActivoNoEncontradoExcepcion("Activo con id "+id+" no encontrado"));
	}
	
	// BUSCAR ACTIVO POR UTIMATIX
	public List<Activos> buscarPorUltimatix(Long ultimatix){
		List<Activos> activos = activosRepo.findAll();
		List<Activos> enviar = new ArrayList<>();
		for (Activos iterante : activos) {
			if (iterante.getId_ultimatix().equals(ultimatix)) {
				enviar.add(iterante);
			}
		}
		return enviar;
	}
	
	// FUNCION VALIDACION DE FECHA
	public boolean validarFecha(Date fecha1, Date fecha2) {
		if ((fecha1.before(fecha2))||(fecha1.equals(fecha2))) {
			return false;
		}
		return true;
	}

	// FUNCION BUSCAR TODAS LAS AREAS 
	public List<Area> buscarAreas() {
		return areaRepo.findAll();
	}
	
	// FUNCION AGREGAR AREA
	public Area agregarArea(Area nuevo) {
		List<Area> areas = areaRepo.findAll();
		for (Area iterante : areas) {
			// VERIFICACION DE AREA REPETIDA
			if (iterante.getNombre().equals(nuevo.getNombre())) {
				throw new AreaNoEncontradoExcepcion("Area repetida");
			}
		}
		return areaRepo.save(nuevo);
	}

	// FUNCION ELIMINAR AREA
	public void eliminarArea(Long id){
		areaRepo.deleteById(id);
	}
	
	//FUNCION BUSCAR TODOS LOS EDIFICIOS
	public List<Edificio> buscarEdificios() {
		return edificioRepo.findAll();
	}
	
	// FUNCION AGREGAR EDIFICIO
	public Edificio agregarEdificio(Edificio nuevo) {
		return edificioRepo.save(nuevo);
	}

	// FUNCION ELIMINAR EDIFICIO POR ID
	public void eliminarEdificio(Long id){
		edificioRepo.deleteById(id);
	}
	
	//FUNCION BUSCAR ACTIVO POR SU TIPO
	public List<Tipo> buscarTipos() {
		return tipoRepo.findAll();
	}
	
	// FUNCION AGREGAR NUEVO TIPO DE ACTIVO
	public Tipo agregarTipo(Tipo nuevo) {
		return tipoRepo.save(nuevo);
	}

	// FUNCION ELIMINAR TIPO DE ACTIVO POR ID
	public void eliminarTipos(Long id) {
		tipoRepo.deleteById(id);
	}
}
