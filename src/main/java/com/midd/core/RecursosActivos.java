package com.midd.core;

import java.sql.Date;
import java.time.*;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.midd.core.Respuestas.*;
import com.midd.core.administracion.*;
import com.midd.core.modelo.*;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/activos")
public class RecursosActivos {
	private final ServiciosActivos serviciosActivos;
	private final Respuestas respuestas;
	private final ServicioValidarFechaSDF servicioValidarFechaSDF;
	RenameLogger logger;


	public RecursosActivos(ServiciosActivos serviciosActivos, Respuestas respuestas,
			ServicioValidarFechaSDF servicioValidarFechaSDF) {
		this.serviciosActivos = serviciosActivos;
		this.respuestas = respuestas;
		this.servicioValidarFechaSDF = servicioValidarFechaSDF;
		this.logger = new RenameLogger();
	}

	@PostMapping("/agregarActivo")
	public ResponseEntity<?> agregarActivo(@RequestBody Activos activo) {
		try {
			serviciosActivos.buscarPorId(activo.getId_activo());
			this.logger.setLoggerWarm("El activo " + activo.getId_activo() + " ya se encuentra registrado");;
			return new ResponseEntity<>(respuestas.respuestas("Activo ya registrado", "2001"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			if (activo.getTipo().equals("CPU/LAPTOP")) {
				if (serviciosActivos.validarMAC_repetida(activo.getDireccion_mac())) {
					this.logger.setLoggerWarm("La MAC " + activo.getDireccion_mac() + " ya se encuentra registrada");
					return new ResponseEntity<>(respuestas.respuestas("MAC ya registrada", "2002"),
							HttpStatus.BAD_REQUEST);
				}
				if (!serviciosActivos.isValidMacAddress(activo.getDireccion_mac())) {
					this.logger.setLoggerWarm("La MAC " + activo.getDireccion_mac() + " no es valida");
					return new ResponseEntity<>(respuestas.respuestas("MAC no válida", "2004"), HttpStatus.BAD_REQUEST);
				}

				if (!serviciosActivos.validarIPrepetida(activo.getDireccion_ip())) {
					this.logger.setLoggerWarm("La IP " + activo.getDireccion_ip() + " ya se encuentra registrada");
					return new ResponseEntity<>(respuestas.respuestas("IP ya registrada", "2003"),
							HttpStatus.BAD_REQUEST);
				}
				if (!serviciosActivos.isValidIPAddress(activo.getDireccion_ip())) {
					this.logger.setLoggerWarm("La IP " + activo.getDireccion_ip() + " no es valida");
					return new ResponseEntity<>(respuestas.respuestas("IP no válida", "2005"),
							HttpStatus.BAD_REQUEST);
				}
			}
			if(serviciosActivos.validarCodigoBarras(activo.getCodigo_barras())) {
				this.logger.setLoggerWarm("El código de activo " + activo.getCodigo_barras() + " ya se encuentra registrado");
				return new ResponseEntity<>(respuestas.respuestas("Código de activo ya registrado", "2003"),
						HttpStatus.BAD_REQUEST);
			}
			activo.setBorrado_logico(false);
			activo.setEstado(false);
			activo.setFecha_registro(Date.valueOf(LocalDate.now(ZoneId.of("GMT-05:00"))));
			LocalDate uno = activo.getFecha_entrega().toLocalDate();
			activo.setFecha_entrega(Date.valueOf(uno));
			if (servicioValidarFechaSDF.validarFecha(activo.getFecha_entrega())) {
				this.logger.setLoggerWarm("Fecha entrega no puede ser Sábado, Domingo o Feriado");
				return new ResponseEntity<>(respuestas
						.respuestas("Fecha entrega no puede ser Sábado, Domingo o Feriado", "3000"),
						HttpStatus.BAD_REQUEST);
			}
			if (serviciosActivos.validarFecha(activo.getFecha_entrega(), activo.getFecha_registro())) {
				this.logger.setLoggerWarm("La fecha de adjudicación " + activo.getFecha_entrega()
						+ " no puede ser mayor a la fecha de registro " + activo.getFecha_registro());
				return new ResponseEntity<>(
						respuestas.respuestas("La fecha de adjudicación no puede ser mayor a la fecha de registro",
								"2004"),
						HttpStatus.BAD_REQUEST);
			}
			if (!activo.getTipo().equals("CPU/LAPTOP")){
				activo.setReservada_ip(false);
			}
			serviciosActivos.agregarActivo(activo);
			this.logger.setLoggerInfo("El activo " + activo + " se registró exitosamente");
			List<Activos> activos = serviciosActivos.buscarPorUltimatix(activo.getId_ultimatix());
			
			return new ResponseEntity<>(activos, HttpStatus.OK);
		}
	}

	@GetMapping("/buscarTodo")
	public ResponseEntity<?> buscarTodo() {
		List<Activos> activos = serviciosActivos.buscarTodos();
		return new ResponseEntity<>(activos, HttpStatus.OK);
	}

	@PostMapping("/buscarUltimatix")
	public ResponseEntity<?> buscarActivosPorUltimatix(@RequestBody Activos activo) {
		List<Activos> activos = serviciosActivos.buscarPorUltimatix(activo.getId_ultimatix());
		return new ResponseEntity<>(activos, HttpStatus.OK);
	}

	@PostMapping("/eliminarActivo")
	public ResponseEntity<?> eliminarActivo(@RequestBody Activos activo) {
		try {
			Activos activo1 = serviciosActivos.buscarPorId(activo.getId_activo());
			if (activo1.getId_ultimatix().equals(activo.getId_ultimatix())) {
				activo1.setBorrado_logico(true);
				activo1.setFecha_eliminado(Date.valueOf(LocalDate.now(ZoneId.of("GMT-05:00"))));
				
				if (servicioValidarFechaSDF.validarFecha(activo1.getFecha_eliminado())) {
					this.logger.setLoggerWarm("Fecha de eliminación no puede ser Sábado, Domingo o Feriado");
					return new ResponseEntity<>(respuestas
							.respuestas("Fecha de eliminación no puede ser Sábado, Domingo o Feriado", "3000"),
							HttpStatus.BAD_REQUEST);
				}
				serviciosActivos.actualizarActivo(activo1);
				this.logger.setLoggerInfo("El activo " + activo + " se eliminó exitosamente (borrado logico)");
				List<Activos> activos = serviciosActivos.buscarPorUltimatix(activo.getId_ultimatix());
				return new ResponseEntity<>(activos, HttpStatus.OK);
			}
			this.logger.setLoggerWarm("El activo " + activo.getId_activo() + " no puede ser eliminado por el usuario "
					+ activo.getId_ultimatix() + "!");
			return new ResponseEntity<>(respuestas.respuestas("Usuario no puede eliminar activo", "2022"),
					HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			this.logger.setLoggerWarm("El activo " + activo.getId_activo() + " no se encuentra registrado");
			return new ResponseEntity<>(respuestas.respuestas("Activo no registrado", "2021"), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("devolverActivo")
	public ResponseEntity<?> devolverActivo(@RequestBody Activos activo) {
		try {
			Activos activo1 = serviciosActivos.buscarPorId(activo.getId_activo());
			if (activo1.getId_ultimatix().equals(activo.getId_ultimatix())) {
				activo1.setEstado(true);
				activo1.setFecha_devolucion(activo.getFecha_devolucion());
				LocalDate uno = activo1.getFecha_devolucion().toLocalDate();
				activo1.setFecha_devolucion(Date.valueOf(uno));
				if (serviciosActivos.validarFecha(activo1.getFecha_registro(), activo1.getFecha_devolucion())) {
					this.logger.setLoggerWarm("La fecha de devolucion " + activo1.getFecha_devolucion()
							+ " no puede ser menor a la fecha de registro " + activo1.getFecha_registro());
					return new ResponseEntity<>(respuestas
							.respuestas("La fecha de devolucion no puede ser menor a la fecha de registro", "2013"),
							HttpStatus.BAD_REQUEST);
				}
				serviciosActivos.actualizarActivo(activo1);
				if (servicioValidarFechaSDF.validarFecha(activo1.getFecha_devolucion())) {
					this.logger.setLoggerWarm("Fecha de devolución no puede ser Sábado, Domingo o Feriado");
					return new ResponseEntity<>(respuestas
							.respuestas("Fecha de devolución no puede ser Sábado, Domingo o Feriado", "3000"),
							HttpStatus.BAD_REQUEST);
				}
				this.logger.setLoggerInfo("El activo " + activo1 + " se devolvió exitosamente ()");
				List<Activos> activos = serviciosActivos.buscarPorUltimatix(activo.getId_ultimatix());
				return new ResponseEntity<>(activos, HttpStatus.OK);
			}
			this.logger.setLoggerWarm("El activo " + activo.getId_activo() + " no puede ser eliminado por el usuario "
					+ activo.getId_ultimatix() + "!");
			return new ResponseEntity<>(respuestas.respuestas("Usuario no puede devolver activo", "2012"),
					HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			this.logger.setLoggerWarm("El activo " + activo.getId_activo() + " no se encuentra registrado");
			return new ResponseEntity<>(respuestas.respuestas("Activo no registrado", "2011"), HttpStatus.BAD_REQUEST);
		}
	}

	// Edificios
	@GetMapping("/edificios")
	public ResponseEntity<?> buscarEdificios() {
		List<Edificio> edificios = serviciosActivos.buscarEdificios();
		return new ResponseEntity<>(edificios, HttpStatus.OK);
	}

	@PostMapping("/agregarEdificio")
	public ResponseEntity<?> agregarEdificio(@RequestBody Edificio nuevo) {
		List<Edificio> edificios = serviciosActivos.buscarEdificios();
		for (Edificio iterante : edificios) {
			if (iterante.getNombre().equals(nuevo.getNombre())) {
				return new ResponseEntity<>(respuestas.respuestas("Edificio ya registrado", "400"),
						HttpStatus.BAD_REQUEST);
			}
		}
		this.logger.setLoggerInfo("El Edificio "+nuevo.getNombre()+" ha sido creada con éxito");
		serviciosActivos.agregarEdificio(nuevo);
		return new ResponseEntity<>(nuevo, HttpStatus.OK);
	}

	@PostMapping("/eliminarEdificio")
	public ResponseEntity<?> eliminarEdificio(@RequestBody Edificio eliminar) {
		serviciosActivos.eliminarEdificio(eliminar.getId());
		this.logger.setLoggerWarm("El edificio "+eliminar.getId() + " ha sido eliminada");
		List<Edificio> edificios = serviciosActivos.buscarEdificios();
		return new ResponseEntity<>(edificios, HttpStatus.OK);
	}

	// Areas
	@GetMapping("/areas")
	public ResponseEntity<?> buscarAreas() {
		List<Area> areas = serviciosActivos.buscarAreas();
		return new ResponseEntity<>(areas, HttpStatus.OK);
	}

	@PostMapping("/agregarArea")
	public ResponseEntity<?> agregarEdificio(@RequestBody Area nuevo) {
		List<Area> areas = serviciosActivos.buscarAreas();
		for (Area iterante : areas) {
			if (iterante.getNombre().equals(nuevo.getNombre())) {
				return new ResponseEntity<>(respuestas.respuestas("Área ya registrado", "400"), HttpStatus.BAD_REQUEST);
			}
		}
		this.logger.setLoggerInfo("El Area "+nuevo.getNombre()+" ha sido creada con éxito");
		serviciosActivos.agregarArea(nuevo);
		return new ResponseEntity<>(nuevo, HttpStatus.OK);
	}

	@PostMapping("/eliminarArea")
	public ResponseEntity<?> eliminarArea(@RequestBody Area eliminar) {
		serviciosActivos.eliminarArea(eliminar.getId());
		this.logger.setLoggerWarm("El area "+eliminar.getId()+" ha sido eliminada");
		List<Area> areas = serviciosActivos.buscarAreas();
		return new ResponseEntity<>(areas, HttpStatus.OK);
	}

	// Tipos
	@GetMapping("/tipos")
	public ResponseEntity<?> buscarTipos() {
		List<Tipo> tipos = serviciosActivos.buscarTipos();
		return new ResponseEntity<>(tipos, HttpStatus.OK);
	}

	@PostMapping("/agregarTipo")
	public ResponseEntity<?> agregarTipos(@RequestBody Tipo nuevo) {
		List<Tipo> tipos = serviciosActivos.buscarTipos();
		for (Tipo iterante : tipos) {
			if (iterante.getNombre().equals(nuevo.getNombre())) {
				return new ResponseEntity<>(respuestas.respuestas("Tipo ya registrado", "400"), HttpStatus.BAD_REQUEST);
			}
		}
		serviciosActivos.agregarTipo(nuevo);
		return new ResponseEntity<>(nuevo, HttpStatus.OK);
	}

	@PostMapping("/eliminarTipo")
	public ResponseEntity<?> eliminarTipo(@RequestBody Tipo eliminar) {
		serviciosActivos.eliminarTipos(eliminar.getId());
		List<Tipo> tipos = serviciosActivos.buscarTipos();
		this.logger.setLoggerWarm("El tipo "+eliminar.getId()+" ha sido eliminada");
		return new ResponseEntity<>(tipos, HttpStatus.OK);
	}

	
}