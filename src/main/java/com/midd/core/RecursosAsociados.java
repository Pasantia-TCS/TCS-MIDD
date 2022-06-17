package com.midd.core;

import java.util.*;

import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.midd.core.Respuestas.*;
import com.midd.core.administracion.*;
import com.midd.core.modelo.*;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/asociados")
public class RecursosAsociados {
	private final ServiciosAsociados serviciosAsociados;
	private final ServiciosPerfil serviciosPerfil;
	private final PasswordEncoder cifrarClave;
	private final Respuestas respuestas;
	RenameLogger logger;

	// Constructor
	public RecursosAsociados(ServiciosAsociados serviciosAsociados, PasswordEncoder cifrarClave, Respuestas respuestas,
			ServiciosPerfil servicios_perfil) {
		super();
		this.serviciosAsociados = serviciosAsociados;
		this.cifrarClave = cifrarClave;
		this.respuestas = respuestas;
		this.serviciosPerfil = servicios_perfil;
		this.logger = new RenameLogger();
	}

	@GetMapping("/buscarAsociados")
	public ResponseEntity<List<Asociado>> obtenerTodosAsociados() {
		List<Asociado> asociados = serviciosAsociados.buscarTodo();
		return new ResponseEntity<>(asociados, HttpStatus.OK);
	}

	@GetMapping("/buscar/{id}")
	public ResponseEntity<Asociado> obtenerAsociadoPorId(@PathVariable("id") Long id) {
		Asociado asociado = serviciosAsociados.buscarAsociadoPorId(id);
		asociado.setClave("");
		return new ResponseEntity<>(asociado, HttpStatus.OK);
	}

	@PostMapping("/agregarAsociado")
	public ResponseEntity<?> agregarAsociado(@RequestBody Asociado asociado) {
		try {
			serviciosAsociados.buscarAsociadoPorId(asociado.getId_numero_Ultimatix());
			this.logger.setLoggerWarm("El usuario " + asociado.getId_numero_Ultimatix() + " ya se encuentra registrado");
			return new ResponseEntity<>(respuestas.respuestas("Usuario ya registrado", "1001"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			asociado.setClave(cifrarClave.encode(asociado.getClave()));
			if (serviciosAsociados.validarCorreo(asociado) == false) {
				this.logger.setLoggerWarm("El correo " + asociado.getCorreo() + " ya se encuentra registrado");
				return new ResponseEntity<>(respuestas.respuestas("Correo ya registrado", "1002"),
						HttpStatus.BAD_REQUEST);
			}

			if (serviciosAsociados.validarTelefono(asociado) == false) {
				this.logger.setLoggerWarm("El telefono " + asociado.getTelefono() + " ya se encuentra registrado");
				return new ResponseEntity<>(respuestas.respuestas("Telefono ya registrado", "1003"),
						HttpStatus.BAD_REQUEST);
			}
			asociado.setEstado(true);
			asociado.setToken(cifrarClave.encode(asociado.getId_numero_Ultimatix().toString()));
			serviciosAsociados.agregarAsociado(asociado);
			asociado.setClave(null);
			this.logger.setLoggerInfo("El asociado " + asociado.getId_numero_Ultimatix() + " se ha registrado exitosamente");
			return new ResponseEntity<>(asociado, HttpStatus.OK);
		}
	}

	@PostMapping("/actualizarAsociado")
	public ResponseEntity<?> actualizarAsociado(@RequestBody Asociado asociado) {
		if (serviciosAsociados.buscarAsociadoId(asociado.getId_numero_Ultimatix())) {
			this.logger.setLoggerWarm("El asociado " + asociado.getId_numero_Ultimatix() + " no se encuentra registrado");
			return new ResponseEntity<>(respuestas.respuestas("Usuario no registrado", "1021"), HttpStatus.BAD_REQUEST);
		}
		if (serviciosAsociados.validarCorreoActualizar(asociado) == false) {
			this.logger.setLoggerWarm("El correo " + asociado.getCorreo() + " ya se encuentra registrado");
			return new ResponseEntity<>(respuestas.respuestas("Correo ya registrado", "1021"), HttpStatus.BAD_REQUEST);
		}
		if (serviciosAsociados.validarTelefonoActualizar(asociado) == false) {
			this.logger.setLoggerWarm("El telefono " + asociado.getTelefono() + " ya se encuentra registrado");
			return new ResponseEntity<>(respuestas.respuestas("Telefono ya registrado", "1023"),
					HttpStatus.BAD_REQUEST);
		}
		Asociado mio = serviciosAsociados.buscarAsociadoPorId(asociado.getId_numero_Ultimatix());
		mio.setCorreo(asociado.getCorreo());
		mio.setTelefono(asociado.getTelefono());
		mio.setIntentos(0);
		Asociado actualizadoAsociado = serviciosAsociados.actualizarAsociado(mio);
		actualizadoAsociado.setClave("");
		actualizadoAsociado.setToken("");
		this.logger.setLoggerInfo("Usuario "+asociado.getId_numero_Ultimatix()+" ha sido actualizado");
		return new ResponseEntity<>(actualizadoAsociado, HttpStatus.OK);
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Asociado> eliminarAsociadoPorId(@PathVariable("id") Long id) {
		serviciosAsociados.eliminarAsociado(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@PostMapping("/tcs-login")
	public ResponseEntity<?> iniciarSesion(@RequestBody Asociado asociado) {
		Asociado nuevo = null;
		// Guardar fecha del primer intento
		try {
			nuevo = serviciosAsociados.buscarAsociadoPorId(asociado.getId_numero_Ultimatix());
			if (nuevo.getIntentos() >= 3 || !nuevo.getEstado()) {
				nuevo.setEstado(false);
				Perfil mio = serviciosPerfil.buscarPerfilMio(nuevo.getId_numero_Ultimatix());
				mio.setEstado(false);
				serviciosPerfil.actualizarPerfil(mio);
				serviciosAsociados.actualizarAsociado(nuevo);
				this.logger.setLoggerWarm("Usuario "+asociado.getId_numero_Ultimatix()+" ha sido bloqueado");
				return new ResponseEntity<>(respuestas.respuestas("Usuario bloqueado", "1013"), HttpStatus.BAD_REQUEST);
			}

			java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
			if (nuevo.getFecha_login() != null) {
				if (nuevo.getFecha_login().before(date) && !nuevo.getFecha_login().toString().equals(date.toString())) {
					nuevo.setIntentos(0);
					serviciosAsociados.actualizarAsociado(nuevo);
				}
			}

		} catch (Exception e) {
			this.logger.setLoggerWarm("El asociado " + asociado.getId_numero_Ultimatix() + " no se encuentra registrado");
			return new ResponseEntity<>(respuestas.respuestas("Usuario o contraseña incorrecta", "1012"),
					HttpStatus.BAD_REQUEST);

		}
		if (cifrarClave.matches(asociado.getClave(), nuevo.getClave()) == false) {
			this.logger.setLoggerWarm("La contraseña ingresada de " + asociado.getId_numero_Ultimatix() + " es incorrecta");
			if (nuevo.getIntentos() == 0) {
				nuevo = actualizarFecha(nuevo);
				serviciosAsociados.actualizarAsociado(nuevo);
			}
			nuevo.setIntentos(nuevo.getIntentos() + 1);
			serviciosAsociados.actualizarAsociado(nuevo);
			this.logger.setLoggerInfo("Usuario "+asociado.getId_numero_Ultimatix()+" ha intentado ingresar, Intento: "+nuevo.getIntentos());
			return new ResponseEntity<>(respuestas.respuestas(
					"Usuario o contraseña incorrecta , Intento: " + nuevo.getIntentos() + " de " + " 3", "1012"),
					HttpStatus.BAD_REQUEST);

		}
		nuevo.setIntentos(0);
		nuevo.setEstado(true);
		serviciosAsociados.actualizarAsociado(nuevo);
		try {
			nuevo = serviciosAsociados.buscarAsociadoPorId(asociado.getId_numero_Ultimatix());
		} catch (Exception e) {
			this.logger.setLoggerWarm("El asociado " + asociado.getId_numero_Ultimatix() + " no se encuentra registrado");
			return new ResponseEntity<>(respuestas.respuestas("Usuario o contraseña incorrecta", "1012"),
					HttpStatus.BAD_REQUEST);
		}
		if (cifrarClave.matches(asociado.getClave(), nuevo.getClave()) == false) {
			this.logger.setLoggerWarm("La contraseña ingresada de " + asociado.getId_numero_Ultimatix() + " es incorrecta");
			return new ResponseEntity<>(respuestas.respuestas("Usuario o contraseña incorrecta", "1012"),
					HttpStatus.BAD_REQUEST);
		}
		this.logger.setLoggerInfo("El asociado " + asociado.getId_numero_Ultimatix() + " ha igresado exitosamente");
		nuevo.setClave(null);
		return new ResponseEntity<>(nuevo, HttpStatus.OK);
	}

	public Asociado actualizarFecha(Asociado asociado) {
		java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
		asociado.setFecha_login(date);
		return asociado;
	}

	@PostMapping("/cambio-password")
	public ResponseEntity<?> cambio_password(@RequestBody Asociado asociado) {
		try {
			Asociado nuevo = serviciosAsociados.buscarAsociadoPorId(asociado.getId_numero_Ultimatix());
			if (nuevo.getEstado()) {
				if (nuevo.getToken().equals(asociado.getToken())) {
					nuevo.setClave(cifrarClave.encode(asociado.getClave()));
					serviciosAsociados.actualizarAsociado(nuevo);
					this.logger.setLoggerWarm("Usuario "+asociado.getId_numero_Ultimatix()+" ha cambiado su contraseña");
					return new ResponseEntity<>(respuestas.respuestas("Usuario ha cambiado su contraseña", "1002"),
							HttpStatus.OK);
				}
				this.logger.setLoggerInfo("Usuario "+asociado.getId_numero_Ultimatix()+" ha intentado cambiar su contraseña con token inválido");
				return new ResponseEntity<>(respuestas.respuestas("Token no valido", "1002"), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(
				respuestas.respuestas("Usuario bloqueado no puede cambiar de contraseña", "1013"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(respuestas.respuestas("Usuario no registrado", "1001"), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/desbloqueo-asociado")
	public ResponseEntity<?> desbloqueo(@RequestBody Asociado asociado) {

		try {
			Asociado nuevo = serviciosAsociados.buscarAsociadoPorId(asociado.getId_numero_Ultimatix());
			if (nuevo.getEstado()) {
				nuevo.setEstado(false);
				Perfil mio = serviciosPerfil.buscarPerfilMio(nuevo.getId_numero_Ultimatix());
				mio.setEstado(false);
				serviciosPerfil.actualizarPerfil(mio);
				serviciosAsociados.actualizarAsociado(nuevo);
				this.logger.setLoggerWarm("Usuario "+asociado.getId_numero_Ultimatix()+" ha sido bloqueado");
				return new ResponseEntity<>(respuestas.respuestas("Usuario bloqueado con exito", "1002"),
						HttpStatus.OK);
			} else {
				nuevo.setEstado(true);
				Perfil mio = serviciosPerfil.buscarPerfilMio(nuevo.getId_numero_Ultimatix());
				mio.setEstado(true);
				serviciosPerfil.actualizarPerfil(mio);
				nuevo.setIntentos(0);
				serviciosAsociados.actualizarAsociado(nuevo);
				this.logger.setLoggerWarm("Usuario "+asociado.getId_numero_Ultimatix()+" ha sido desbloqueado");
				return new ResponseEntity<>(respuestas.respuestas("Usuario desbloqueado con exito", "1002"),
						HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(respuestas.respuestas("Usuario no registrado", "1001"), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/devolver-token")
	public ResponseEntity<?> tokens(@RequestBody Asociado asociado) {
		Map<String, Object> respuesta = new HashMap<>();
		try {
			Asociado mio = serviciosAsociados.buscarAsociadoPorId(asociado.getId_numero_Ultimatix());
			respuesta.put("token", mio.getToken().toString());
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(respuestas.respuestas("Usuario no registrado", "1001"), HttpStatus.BAD_REQUEST);
		}
	}
	
}