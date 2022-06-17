package com.midd.core;


import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.midd.core.Respuestas.*;
import com.midd.core.administracion.ServiciosPerfil;
import com.midd.core.modelo.*;

@RestController
@CrossOrigin(origins = "*",methods = {RequestMethod.GET,RequestMethod.POST})
@RequestMapping("/perfil/")
public class RecursosPerfil {
	
	private final ServiciosPerfil serviciosPerfil;
	private final Respuestas respuestas;
	RenameLogger logger;

	
	public RecursosPerfil(ServiciosPerfil serviciosPerfil, Respuestas respuestas) {
		this.serviciosPerfil = serviciosPerfil;
		this.respuestas = respuestas;
		this.logger = new RenameLogger();
	}
	
	@PostMapping("actualizarPerfil")
	public ResponseEntity<?> agregarActivo(@RequestBody Perfil nuevo){
		if (serviciosPerfil.buscarPerfilId(nuevo.getId_ultimatix())) {
			this.logger.setLoggerWarm("El asociado "+ nuevo.getId_ultimatix() +" no se encuentra registrado");
			return new ResponseEntity<>(respuestas.respuestas("Usuario no registrado", "2031"),HttpStatus.BAD_REQUEST);
		}
		serviciosPerfil.agregarPerfil(nuevo);
		this.logger.setLoggerInfo("Usuario "+nuevo.getId_ultimatix()+" actualizó el perfil");
		return new ResponseEntity<>(nuevo,HttpStatus.OK);
	}
	
	@PostMapping("habilidadUltimatix")
	public ResponseEntity<?> habilidadUltimatix(@RequestBody Perfil nuevo){
		if (serviciosPerfil.buscarPerfilId(nuevo.getId_ultimatix())) {
			this.logger.setLoggerWarm("El asociado "+ nuevo.getId_ultimatix() +" no se encuentra registrado");
			return new ResponseEntity<>(respuestas.respuestas("Usuario no registrado", "1011"),HttpStatus.BAD_REQUEST);
		}
		String[] mio = serviciosPerfil.habilidadesUltimatix(nuevo.getId_ultimatix());
		return new ResponseEntity<>(mio,HttpStatus.OK);
	}
	
	@PostMapping("ultimatixHabilidad")
	public ResponseEntity<?> ultimatixHabilidad(@RequestBody String habilidad){
		List<Perfil> mi = serviciosPerfil.buscarPerfiles(habilidad);
		return new ResponseEntity<>(mi,HttpStatus.OK);
	}
	
	@PostMapping("perfil")
	public ResponseEntity<?> perfil(@RequestBody Perfil nuevo){
		if (serviciosPerfil.buscarPerfilId(nuevo.getId_ultimatix())) {
			this.logger.setLoggerWarm("El asociado "+ nuevo.getId_ultimatix() +" no se encuentra registrado");
			return new ResponseEntity<>(respuestas.respuestas("Usuario no registrado", "2031"),HttpStatus.BAD_REQUEST);
		}
		this.logger.setLoggerInfo("El asociado "+ nuevo.getId_ultimatix() +" actualizo el perfil");
		Perfil mio = serviciosPerfil.perfilUltimatix(nuevo.getId_ultimatix());//Errores faltan colocar aun
		return new ResponseEntity<>(mio,HttpStatus.OK);
	}

	@GetMapping("perfiles")
	public ResponseEntity<?> perfilesTodo(){
		List<Perfil> mios = serviciosPerfil.buscarTodos();
		return new ResponseEntity<>(mios,HttpStatus.OK);
	}
	
	@PostMapping("sobreMi")
	public ResponseEntity<?> sobreMi(@RequestBody Perfil nuevo){
		if (serviciosPerfil.buscarPerfilId(nuevo.getId_ultimatix())) {
			this.logger.setLoggerWarm("El asociado "+ nuevo.getId_ultimatix() +" no se encuentra registrado");
			return new ResponseEntity<>(respuestas.respuestas("Usuario no registrado", "2031"),HttpStatus.BAD_REQUEST);
		}
		Perfil mio = serviciosPerfil.perfilUltimatix(nuevo.getId_ultimatix());
		mio.setSobreMi(nuevo.getSobreMi());
		serviciosPerfil.agregarPerfil(mio);
		this.logger.setLoggerInfo("El asociado "+mio.getNombres_completos()+" actualizó el Sobre Mi");
		return new ResponseEntity<>(mio,HttpStatus.OK);
	}

	// Permite editar habilidades tecnicas y nivel de conocimiento
	@PostMapping("editarMisHabilidades")
	public ResponseEntity<?> habilidades(@RequestBody Perfil nuevo){
		if (serviciosPerfil.buscarPerfilId(nuevo.getId_ultimatix())) {
			this.logger.setLoggerWarm("El asociado "+ nuevo.getId_ultimatix() +" no se encuentra registrado");
			return new ResponseEntity<>(respuestas.respuestas("Usuario no registrado", "2031"),HttpStatus.BAD_REQUEST);
		}
		Perfil mio = serviciosPerfil.perfilUltimatix(nuevo.getId_ultimatix());
		mio.setHabilidades(nuevo.getHabilidades());
		mio.setNivel_habilidad(nuevo.getNivel_habilidad());
		serviciosPerfil.agregarPerfil(mio);
		this.logger.setLoggerInfo("El asociado "+mio.getNombres_completos()+" actualizó Habilidades técnicas");
		return new ResponseEntity<>(mio,HttpStatus.OK);
	}
	
	// Permite editar habilidades funcionales y nivel de conocimiento
	@PostMapping("editarMisHabilidades-funcionales")
	public ResponseEntity<?> habilidadesFuncionales(@RequestBody Perfil nuevo){
		if (serviciosPerfil.buscarPerfilId(nuevo.getId_ultimatix())) {
			this.logger.setLoggerWarm("El asociado "+ nuevo.getId_ultimatix() +" no se encuentra registrado");
			return new ResponseEntity<>(respuestas.respuestas("Usuario no registrado", "2031"),HttpStatus.BAD_REQUEST);
		}
		Perfil mio = serviciosPerfil.perfilUltimatix(nuevo.getId_ultimatix());
		mio.setHabilidades_funcionales(nuevo.getHabilidades_funcionales());
		mio.setNivel_habilidad_funcional(nuevo.getNivel_habilidad_funcional());
		serviciosPerfil.agregarPerfil(mio);
		this.logger.setLoggerInfo("El asociado "+mio.getNombres_completos()+" actualizó Habilidades funcionales");
		return new ResponseEntity<>(mio,HttpStatus.OK);
	}
	
	// Permite editar aplicaciones y nivel de conocimiento
	@PostMapping("editarAplicaciones")
	public ResponseEntity<?> aplicaciones(@RequestBody Perfil nuevo){
		if (serviciosPerfil.buscarPerfilId(nuevo.getId_ultimatix())) {
			this.logger.setLoggerWarm("El asociado "+ nuevo.getId_ultimatix() +" no se encuentra registrado");
			return new ResponseEntity<>(respuestas.respuestas("Usuario no registrado", "2031"),HttpStatus.BAD_REQUEST);
		}
		Perfil mio = serviciosPerfil.perfilUltimatix(nuevo.getId_ultimatix());
		mio.setAplicaciones(nuevo.getAplicaciones());
		mio.setNivel_aplicaciones(nuevo.getNivel_aplicaciones());
		serviciosPerfil.agregarPerfil(mio);
		this.logger.setLoggerInfo("El asociado "+mio.getNombres_completos()+" actualizó Aplicaciones");
		return new ResponseEntity<>(mio,HttpStatus.OK);
	}	
	// Permite el cambio de usuario de Red
	@PostMapping("usuarioRed")
	public ResponseEntity<?> usuarioRed(@RequestBody Perfil nuevo){
		if (serviciosPerfil.buscarPerfilId(nuevo.getId_ultimatix())) {
			this.logger.setLoggerWarm("El asociado "+ nuevo.getId_ultimatix() +" no se encuentra registrado");
			return new ResponseEntity<>(respuestas.respuestas("Usuario no registrado", "2031"),HttpStatus.BAD_REQUEST);
		}
		Perfil mio = serviciosPerfil.perfilUltimatix(nuevo.getId_ultimatix());
		mio.setUsuario_red(nuevo.getUsuario_red());
		serviciosPerfil.agregarPerfil(mio);
		this.logger.setLoggerInfo("El asociado "+mio.getNombres_completos()+" actualizó el Usuario de Red");
		return new ResponseEntity<>(mio,HttpStatus.OK);
	}	

	//Permite actualizar el rol
	@PostMapping("actualizar-rol")
	public ResponseEntity<?> actualizarRol(@RequestBody Perfil perfil) {
		Perfil perfil_bd = serviciosPerfil.perfilUltimatix(perfil.getId_ultimatix());
		if(perfil_bd.getRol().equals("user")){
			perfil_bd.setRol("admin");
		}else{
			perfil_bd.setRol("user");
		}
		serviciosPerfil.agregarPerfil(perfil_bd);
		this.logger.setLoggerInfo("El asociado "+perfil_bd.getNombres_completos()+" actualizó el Sobre Mi");
		return new ResponseEntity<>(respuestas.respuestas("El usuario con ultimatix: " + perfil.getId_ultimatix() + " cambio de rol ", "200"),HttpStatus.OK);
	}
	//-----------------------------------------------------------------------------------//
	//Catalogo habilidades tecnicas
	@GetMapping("habilidades")// añadir nivel
	public ResponseEntity<?> habilidades(){
		List<Habilidades> mis = serviciosPerfil.buscarHabilidades();
		return new ResponseEntity<>(mis,HttpStatus.OK);
	}
	
	@PostMapping("agregarHabilidad")
	public ResponseEntity<?> agregarHabilidad(@RequestBody Habilidades habilidad){
		List<Habilidades> mis = serviciosPerfil.buscarHabilidades();
		for (Habilidades iterante : mis) {
				if  (iterante.getNombre().equals(habilidad.getNombre())){
					return new ResponseEntity<>(respuestas.respuestas("Habilidad técnica ya registrada", "400"),
							HttpStatus.BAD_REQUEST);
				}
		}
		serviciosPerfil.agregarHabilidades(habilidad);
		this.logger.setLoggerInfo("La habilidad técnica "+habilidad.getNombre()+" ha sido agregada del catálogo");
		return new ResponseEntity<>(habilidad,HttpStatus.OK);
	}
	
	@PostMapping("eliminarHabilidad")
	public ResponseEntity<?> eliminarHabilidadId(@RequestBody Habilidades habilidad) {
		serviciosPerfil.eliminarHabilidad(habilidad.getId());
		List<Habilidades> habilidades = serviciosPerfil.buscarHabilidades();
		this.logger.setLoggerInfo("La habilidad técnica "+habilidad.getId()+" ha sido eliminada del catálogo");
		return new ResponseEntity<>(habilidades, HttpStatus.OK);
	}
	
	//Catalogo Habilidades funcionales
	
	@GetMapping("habilidadesFuncionales")
	public ResponseEntity<?> habilidadesFuncionales(){
		List<Habilidades_funcionales> mis = serviciosPerfil.buscarHabilidades_funcionales();
		return new ResponseEntity<>(mis,HttpStatus.OK);
	}
	
	@PostMapping("agregarHabilidadFuncional")
	public ResponseEntity<?> agregarHabilidadFuncional(@RequestBody Habilidades_funcionales habilidad){
		List<Habilidades_funcionales> mis = serviciosPerfil.buscarHabilidades_funcionales();
		for (Habilidades_funcionales iterante : mis) {
				if  (iterante.getNombre().equals(habilidad.getNombre())){
					return new ResponseEntity<>(respuestas.respuestas("Habilidad funcional ya registrada", "400"),
							HttpStatus.BAD_REQUEST);
				}
		}
		serviciosPerfil.agregarHabilidades_funcionales(habilidad);
		this.logger.setLoggerInfo("La habilidad funcional "+habilidad.getNombre()+" ha sido agregada del catálogo");
		return new ResponseEntity<>(habilidad,HttpStatus.OK);
	}
	
	@PostMapping("eliminarHabilidadFuncional")
	public ResponseEntity<?> eliminarHabilidadFuncionalId(@RequestBody Habilidades_funcionales habilidad) {
		serviciosPerfil.eliminarHabilidad_funcional(habilidad.getId());
		List<Habilidades_funcionales> habilidades = serviciosPerfil.buscarHabilidades_funcionales();
		this.logger.setLoggerInfo("La habilidad funcional "+habilidad.getId()+" ha sido eliminada del catálogo");
		return new ResponseEntity<>(habilidades, HttpStatus.OK);
	}
	
	//Catalogo Aplicaciones
	
	@GetMapping("aplicaciones")
	public ResponseEntity<?> aplicaciones(){
		List<Aplicaciones_catalogo> mis = serviciosPerfil.buscarAplicaciones();
		return new ResponseEntity<>(mis,HttpStatus.OK);
	}
		
	@PostMapping("agregarAplicacion")
	public ResponseEntity<?> agregarAplicacion(@RequestBody Aplicaciones_catalogo aplicacion){
		List<Aplicaciones_catalogo> mis = serviciosPerfil.buscarAplicaciones();
		for (Aplicaciones_catalogo iterante : mis) {
				if  (iterante.getNombre().equals(aplicacion.getNombre())){
					return new ResponseEntity<>(respuestas.respuestas("Aplicación ya registrada", "400"),
							HttpStatus.BAD_REQUEST);
				}
		}
		serviciosPerfil.agregarAplicaciones(aplicacion);
		this.logger.setLoggerInfo("La aplicacion "+aplicacion.getNombre()+" ha sido agregada del catálogo");
		return new ResponseEntity<>(aplicacion,HttpStatus.OK);
	}
		
		@PostMapping("eliminarAplicacion")
		public ResponseEntity<?> eliminarAplicacionId(@RequestBody Aplicaciones_catalogo aplicacion) {
			serviciosPerfil.eliminarAplicaciones(aplicacion.getId());
			List<Aplicaciones_catalogo> aplicaciones = serviciosPerfil.buscarAplicaciones();
			this.logger.setLoggerInfo("La aplicacion "+aplicacion.getId()+" ha sido eliminada del catálogo");
			return new ResponseEntity<>(aplicaciones, HttpStatus.OK);
		}	
}