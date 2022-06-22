package com.midd.core;

import java.util.*;

import com.midd.core.administracion.*;
import com.midd.core.modelo.*;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.midd.core.Respuestas.*;

// END POINT DE ASIGNACION DE PROYECTOS
@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/asignaciones-proyectos")
public class RecursosAsignacionesProyecto {
	private final ServicosAsignacionProyecto servicio_asignaciones;
	private final Respuestas respuestas;
	private final ServicioEquipo servicio_equipo;
	private final ServiciosPerfil servicioPerfil;
	private final ServicioValidarFechaSDF servicioValidarFechaSDF;
	RenameLogger logger;

	public RecursosAsignacionesProyecto(ServicosAsignacionProyecto servicio_asignaciones, Respuestas respuestas,
			ServicioEquipo servicio_equipo, ServiciosPerfil servicioPerfil,
			ServicioValidarFechaSDF servicioValidarFechaSDF) {
		{
			this.servicio_asignaciones = servicio_asignaciones;
			this.respuestas = respuestas;
			this.servicio_equipo = servicio_equipo;
			this.servicioPerfil = servicioPerfil;
			this.servicioValidarFechaSDF = servicioValidarFechaSDF;
			this.logger = new RenameLogger();
		}
	}

	// METODO POST PARA AGREGAR UNA NUEVA ASIGNACION
	@PostMapping("/agregar-asignacion-proyecto")
	public ResponseEntity<?> agregarAsignacionProyecto(@RequestBody AsignacionProyecto asignacion_proyecto) {
		// VALIDACION DE NO ASIGNACION REPETIDA
		if (servicio_asignaciones.validarMiembro(asignacion_proyecto.getUltimatix_asi(),
				asignacion_proyecto.getId_equipo_asi())) {

			this.logger.setLoggerWarm("Usuario " + asignacion_proyecto.getUltimatix_asi()
					+ " ya se encuentra registrado en este proyecto "
					+ asignacion_proyecto.getId_equipo_asi());
			return new ResponseEntity<>(respuestas
					.respuestas("Usuario ya se encuentra registrado en este proyecto", "3000"),
					HttpStatus.BAD_REQUEST);
		}
		// VALIDACION DE FECHAS
		// FEIADOS Y FINES DE SEMANA
		if (servicioValidarFechaSDF.validarFecha(asignacion_proyecto.getFecha_inicio())) {
			this.logger.setLoggerWarm("Fecha inicio no puede ser Sábado, Domingo o Feriado");
			return new ResponseEntity<>(respuestas
					.respuestas("Fecha inicio no puede ser Sábado, Domingo o Feriado", "3000"),
					HttpStatus.BAD_REQUEST);
		}
		if (servicioValidarFechaSDF.validarFecha(asignacion_proyecto.getFecha_fin())) {
			this.logger.setLoggerWarm("Fecha de finalización no puede ser Sábado, Domingo o Feriado");
			return new ResponseEntity<>(respuestas
					.respuestas("Fecha de finalización no puede ser Sábado, Domingo o Feriado",
							"3000"),
					HttpStatus.BAD_REQUEST);
		}
		if (asignacion_proyecto.getFecha_inicio().after(asignacion_proyecto.getFecha_fin())) {
			this.logger.setLoggerWarm("Fecha inicio no puede ser mayor que la fecha fin");
			return new ResponseEntity<>(respuestas
					.respuestas("Fecha inicio no puede ser mayor que la fecha fin", "3000"),
					HttpStatus.BAD_REQUEST);
		}
		if (servicio_asignaciones.validarFechaPrevia(asignacion_proyecto.getUltimatix_asi(),
				asignacion_proyecto.getId_equipo_asi(),
				asignacion_proyecto.getFecha_inicio())) {
			this.logger.setLoggerWarm("Fecha inicio no puede ser menor a la fecha de baja anterior");
			return new ResponseEntity<>(respuestas
					.respuestas("Fecha inicio no puede ser menor a la fecha de baja anterior",
							"3000"),
					HttpStatus.BAD_REQUEST);
		}
		servicio_asignaciones.agregarMiembroEquipo(asignacion_proyecto.getUltimatix_asi(),
				asignacion_proyecto.getId_equipo_asi(), asignacion_proyecto.getAsignacion());
		asignacion_proyecto.setEstado(true);
		servicio_asignaciones.agregarAsignacionProyecto(asignacion_proyecto);
		Perfil mio = servicioPerfil.buscarPerfilMio(asignacion_proyecto.getUltimatix_asi());
		return obtenerAsignacionesUltimatix(mio);
	}

	// METODO POST PARA ACTUALIZAR LA FECHA FIN DE LA ASIGNACION
	@PostMapping("/actualizar-Fecha-fin")
	public ResponseEntity<?> actualizarFechaFinAsignacionProyecto(
			@RequestBody AsignacionProyecto asignacion_proyecto) {
		try {
			AsignacionProyecto mi = servicio_asignaciones.buscarAsigancionProyectoId(
					asignacion_proyecto.getId_asignacion_proyecto_asg());

			// VALIDACION DE FERIADOS Y FINES DE SEMANA
			if (servicioValidarFechaSDF.validarFecha(asignacion_proyecto.getFecha_fin())) {
				this.logger.setLoggerWarm("Fecha de finalización no puede ser Sábado, Domingo o Feriado");
				return new ResponseEntity<>(respuestas
						.respuestas("Fecha de finalización no puede ser Sábado, Domingo o Feriado",
								"3000"),
						HttpStatus.BAD_REQUEST);
			}
			if (mi.getFecha_inicio().after(asignacion_proyecto.getFecha_fin())) {
				this.logger.setLoggerWarm("Fecha inicio no puede ser mayor que la fecha de finalización");
				return new ResponseEntity<>(respuestas
						.respuestas("Fecha inicio no puede ser mayor que la fecha fin", "3000"),
						HttpStatus.BAD_REQUEST);
			}
			mi.setFecha_fin(asignacion_proyecto.getFecha_fin());
			Perfil mio = servicioPerfil.buscarPerfilMio(asignacion_proyecto.getUltimatix_asi());
			return obtenerAsignacionesUltimatix(mio);

		} catch (Exception e) {
			this.logger.setLoggerWarm("La asignación " + asignacion_proyecto.getId_asignacion_proyecto_asg()
					+ " no se encuentra registrada");
			return new ResponseEntity<>(respuestas.respuestas("Asignación no registrada", "2021"),
					HttpStatus.BAD_REQUEST);
		}
	}

	// METODO POST PARA SALIR O DAR DE BAJA UNA ASIGNACION
	@PostMapping("/dar-baja")
	public ResponseEntity<?> actualizarFechaDeBaja(@RequestBody AsignacionProyecto asignacion_proyecto) {
		try {
			AsignacionProyecto mi = servicio_asignaciones.buscarAsigancionProyectoId(
					asignacion_proyecto.getId_asignacion_proyecto_asg());
			// VALIDACION DE FECHAS
			if (mi.getFecha_fin() != asignacion_proyecto.getFecha_baja()) {
				
				if (mi.getFecha_inicio().after(asignacion_proyecto.getFecha_baja())) {
					this.logger.setLoggerWarm("Fecha inicio no puede ser mayor que la fecha de baja");
					return new ResponseEntity<>(respuestas.respuestas(
							"Fecha inicio no puede ser mayor que la fecha de baja", "3000"),
							HttpStatus.BAD_REQUEST);
				}
				if (mi.getFecha_fin().before(asignacion_proyecto.getFecha_baja())) {
					this.logger.setLoggerWarm("Fecha fin no puede ser mayor que la fecha baja");
					return new ResponseEntity<>(respuestas.respuestas(
							"Fecha fin no puede ser mayor que la fecha de baja", "3000"),
							HttpStatus.BAD_REQUEST);
				}
			}
			// VALIDACION DE FERIADOS Y FINES DE SEMANA
			if (servicioValidarFechaSDF.validarFecha(asignacion_proyecto.getFecha_baja())) {
				this.logger.setLoggerWarm("Fecha de baja no puede ser Sábado, Domingo o Feriado");
				return new ResponseEntity<>(respuestas
						.respuestas("Fecha de baja no puede ser Sábado, Domingo o Feriado",
								"3000"),
						HttpStatus.BAD_REQUEST);
			}
			mi.setFecha_baja(asignacion_proyecto.getFecha_baja());
			// ACTUALIZACION DEL ESTADO DE LA ASIGNACION
			mi.setEstado(false);
			// RESTAR ASIGNACION
			servicio_asignaciones.restarAsignacion(mi.getUltimatix_asi(),
					mi.getAsignacion());
			// ELIMINAR MIEMBRO
			servicio_asignaciones.quitarMiembroEquipo(mi.getUltimatix_asi(),
					mi.getId_equipo_asi());
			servicio_asignaciones.agregarAsignacionProyecto(mi);
			Perfil mio = servicioPerfil.buscarPerfilMio(asignacion_proyecto.getUltimatix_asi());
			// SE RETORNA LA ASIGNACION DEL PERFIL ACTUALIZADA
			return obtenerAsignacionesUltimatix(mio);
		} catch (Exception e) {
			this.logger.setLoggerWarm("La asignación " + asignacion_proyecto.getId_asignacion_proyecto_asg()
					+ " no se encuentra registrada");
			return new ResponseEntity<>(respuestas.respuestas("Asignación no registrada", "2021"),
					HttpStatus.BAD_REQUEST);
		}
	}

	// METODO POST PARA OBTENER ASIGNACIONES POR ULTIMATIX
	@PostMapping("/obtener-asignaciones-ultimatix")
	public ResponseEntity<?> obtenerAsignacionesUltimatix(@RequestBody Perfil perfil) {
		List<Map<String, Object>> lista_respuestas = new ArrayList<>();
		for (AsignacionProyecto asg : servicio_asignaciones.buscarTodasAsignacionesProyecto()) {
			// BUSQUEDA SECUENCIAS POR ULTIMATIX
			if (asg.getUltimatix_asi().equals(perfil.getId_ultimatix())) {
				Map<String, Object> respuesta = new HashMap<>();
				Equipo buscado = servicio_equipo.buscarEquipoId(asg.getId_equipo_asi());
				respuesta.put("id_asignacion_proyecto_asi", asg.getId_asignacion_proyecto_asg());
				respuesta.put("id_equipo", asg.getId_equipo_asi());
				respuesta.put("nombre_equipo_asi", buscado.getNombre_equipo_asi());
				respuesta.put("tipo_equipo_asi", buscado.getTipo_equipo_asi());
				respuesta.put("ultimatix_asi", asg.getUltimatix_asi());
				respuesta.put("asignacion", asg.getAsignacion());
				respuesta.put("fecha_inicio", asg.getFecha_inicio());
				respuesta.put("fecha_fin", asg.getFecha_fin());
				respuesta.put("fecha_baja", asg.getFecha_baja());
				respuesta.put("estado", asg.getEstado());
				lista_respuestas.add(respuesta);
			}
		}

		return new ResponseEntity<>(lista_respuestas, HttpStatus.OK);
	}

	// METODO POST PARA ACTUALIZAR NIVEL DE ASIGNACION
	@PostMapping("/actualizar-asignacion")
	public ResponseEntity<?> actualizarAsignacion(@RequestBody AsignacionProyecto asignacion) {
		AsignacionProyecto asignacion_db = servicio_asignaciones
				.buscarAsigancionProyectoId(asignacion.getId_asignacion_proyecto_asg());

		// VALIDACION DE FECHAS
		if (servicioValidarFechaSDF.validarFecha(asignacion.getFecha_fin())) {
			this.logger.setLoggerWarm("Fecha de baja no puede ser Sábado, Domingo o Feriado");
			return new ResponseEntity<>(respuestas
					.respuestas("Fecha de baja no puede ser Sábado, Domingo o Feriado",
							"3000"),
					HttpStatus.BAD_REQUEST);
		}

		if (asignacion_db.getFecha_inicio().after(asignacion.getFecha_fin())) {
			return new ResponseEntity<>(respuestas.respuestas(
					"Fecha inicio no puede ser mayor que la fecha final", "3000"),
					HttpStatus.BAD_REQUEST);

		}

		asignacion_db.setFecha_fin(asignacion.getFecha_fin());
		Perfil mio = servicioPerfil.buscarPerfilMio(asignacion_db.getUltimatix_asi());
		if (asignacion.getAsignacion() != 0) {
			int asignacion_user = mio.getAsignacion_usuario() - asignacion_db.getAsignacion();
			mio.setAsignacion_usuario(asignacion_user + asignacion.getAsignacion());
			asignacion_db.setAsignacion(asignacion.getAsignacion());
		}
		servicioPerfil.actualizarAsignacion(mio);
		servicio_asignaciones.agregarAsignacionProyecto(asignacion_db);

		return obtenerAsignacionesUltimatix(mio);

	}


	// METODO GET PARA BUSCAR TODAS LAS ASIGNACIONES
	@GetMapping("/buscar-asignaciones")
	public ResponseEntity<?> buscarAsignaciones() {
		List<Map<String, Object>> lista_respuestas = new ArrayList<>();

		for (AsignacionProyecto asg : servicio_asignaciones.buscarTodasAsignacionesProyecto()) {
			for (Equipo equipo : servicio_equipo.buscarTodosEquipos()) {
				// CREACION DE RESPONSE PERSONALIZADO
				if (equipo.getId_asi().equals(asg.getId_equipo_asi())) {
					Map<String, Object> respuesta = new HashMap<>();
					respuesta.put("id_asignacion_proyecto_asi",
							asg.getId_asignacion_proyecto_asg());
					respuesta.put("id_equipo", asg.getId_equipo_asi());
					respuesta.put("nombre_equipo_asi", equipo.getNombre_equipo_asi());
					respuesta.put("tipo_equipo_asi", equipo.getTipo_equipo_asi());
					respuesta.put("ultimatix_asi", asg.getUltimatix_asi());
					respuesta.put("asignacion", asg.getAsignacion());
					respuesta.put("fecha_inicio", asg.getFecha_inicio());
					respuesta.put("fecha_fin", asg.getFecha_fin());
					respuesta.put("fecha_baja", asg.getFecha_baja());
					respuesta.put("estado", asg.getEstado());
					lista_respuestas.add(respuesta);
				}
			}
		}
		return new ResponseEntity<>(lista_respuestas, HttpStatus.OK);
	}
}
