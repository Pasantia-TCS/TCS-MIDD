package com.midd.core.administracion;
import java.util.List;
import org.springframework.stereotype.Service;

import com.midd.core.Exepciones.UserNotFoundException;
import com.midd.core.modelo.Asociado;
import com.midd.core.modelo.Perfil;
import com.midd.core.repositorio.AsociadosRepo;
import com.midd.core.repositorio.PerfilRepo;



@Service
public class ServiciosAsociados {
	private final AsociadosRepo asociadosRepo;
	private final PerfilRepo perfilRepo;
	
	// CONSTRUCTOR
	public ServiciosAsociados(AsociadosRepo asociadosRepo, PerfilRepo perfilRepo) {
		this.asociadosRepo = asociadosRepo;
		this.perfilRepo = perfilRepo;
	}
	
	// FUNCION AGREGAR ASOCIADO
	public Asociado agregarAsociado(Asociado asociado) {
		String[] qwe = new String[0];
		// POR CADA ASOCIADO CREADO SE LE GENERA UN PERFIL
		perfilRepo.save(new Perfil(asociado.getId_numero_Ultimatix(),
						"",qwe,qwe,qwe,qwe,qwe,qwe,"",0,//qwe habilidades 
						asociado.getNombre()+" "+asociado.getApellido(),
						"user",true));
		return asociadosRepo.save(asociado);
	}

	// FUNCION VALIDAD CORREO
	public boolean validarCorreo(Asociado asociado) {
		List<Asociado> nuevo = asociadosRepo.findAll();
		for (Asociado iterante : nuevo) {
			// VALIDACION CORREO REPETIDO
			if (iterante.getCorreo().equals(asociado.getCorreo())) {
				return false;
			}
		}
		
		return true;
	}
	
	// FUNCION VALIDACION DEL CORREO CUANDO SE LO QUIERE ACTUALIZAR
	public boolean validarCorreoActualizar(Asociado asociado) {
		List<Asociado> nuevo = asociadosRepo.findAll();
		for (Asociado iterante : nuevo) {
			// VALIDACION CORREO REPETIDO
			if ((iterante.getCorreo().equals(asociado.getCorreo()))&&(!iterante.getId_numero_Ultimatix().equals(asociado.getId_numero_Ultimatix()))) {
				return false;
			}
		}
		return true;
	}
	
	// FUNCION VALIDAR TELEFONO
	public boolean validarTelefono(Asociado asociado) {
		List<Asociado> nuevo = asociadosRepo.findAll();
		for (Asociado iterante : nuevo) {
			// VALIDACION TELEFONO REPETIDO
			if (iterante.getTelefono().equals(asociado.getTelefono())) {
				return false;
			}
		}
		return true;
	}
	
	// FUNCION VALIDACION DEL TELEFONO CUANDO SE LO QUIERE ACTUALIZAR
	public boolean validarTelefonoActualizar(Asociado asociado) {
		List<Asociado> nuevo = asociadosRepo.findAll();
		for (Asociado iterante : nuevo) {
			// VALIDACION TELEFONO REPETIDO
			if ((iterante.getTelefono().equals(asociado.getTelefono()))&&(!iterante.getId_numero_Ultimatix().equals(asociado.getId_numero_Ultimatix()))) {
				return false;
			}
		}
		return true;
	}
	
	// BUSCAR TODOS LOS ASOCIADOS
	public List<Asociado> buscarTodo(){
		List<Asociado> asociados = asociadosRepo.findAll();
		for (Asociado iterante : asociados) {
			// CAMBIO DE LAS CLAVES A NULL PARA LA PRESENTACION DE LOS DATOS
			iterante.setClave(null);
			iterante.setToken(null);
		}
		return asociados;
	}
	
	// FUNCION ACTUALIZAR ASOCIADO
	public Asociado actualizarAsociado(Asociado asociadoOld) {
		return asociadosRepo.save(asociadoOld);
	}
	
	// FUNCION BUSCAR ASOCIADO POR ID -> RETORNA ASOCIADO
	public Asociado buscarAsociadoPorId(Long Id) {
		return asociadosRepo.findById(Id)
				.orElseThrow(()->new UserNotFoundException("Asociado con id "+Id+" no encontrado"));
	}
	
	// FUNCION BUSCAR ASOCIADO POR ID -> RETORNA BOOLEANO
	public boolean buscarAsociadoId(Long Id) {
		if (asociadosRepo.findById(Id).isEmpty()) {
			return true;
		}
		return false;
	}
	
	// FUNCION ELIMINAR ASOCIADO
	public void eliminarAsociado(Long Id) {
		if (!asociadosRepo.existsById(Id)){
			throw new UserNotFoundException("Asociado con id "+Id+" no encontrado");
		}
		asociadosRepo.deleteById(Id);
	}


}
