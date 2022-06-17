package com.midd.core.administracion;

import com.midd.core.Exepciones.AsignacionProyectoNoEncontrada;
import com.midd.core.modelo.AsignacionProyecto;
import com.midd.core.modelo.Equipo;
import com.midd.core.modelo.Perfil;
import com.midd.core.repositorio.AsignacionesProyectoRepo;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ServicosAsignacionProyecto {
    private final AsignacionesProyectoRepo asignaciones_proyecto_repo;
    private final ServicioEquipo servicioEquipo;
    private final ServiciosPerfil serviciosPerfil;

    public ServicosAsignacionProyecto(AsignacionesProyectoRepo asignacion_proyecto_repo,
            ServicioEquipo servicioEquipo,
            ServiciosPerfil serviciosPerfil) {
        this.asignaciones_proyecto_repo = asignacion_proyecto_repo;
        this.servicioEquipo = servicioEquipo;
        this.serviciosPerfil = serviciosPerfil;
    }

    public AsignacionProyecto agregarAsignacionProyecto(AsignacionProyecto asignacionNueva) {
        return asignaciones_proyecto_repo.save(asignacionNueva);
    }

    public AsignacionProyecto actualizarFechaFin(AsignacionProyecto asignacionNueva) {
        AsignacionProyecto fecha_fin = this.buscarAsigancionProyectoId(asignacionNueva.getId_asignacion_proyecto_asg());
        fecha_fin.setFecha_fin(asignacionNueva.getFecha_fin());
        return asignaciones_proyecto_repo.save(asignacionNueva);
    }

    public AsignacionProyecto actualizarEstado(AsignacionProyecto asignacionNueva) {
        AsignacionProyecto estado = this.buscarAsigancionProyectoId(asignacionNueva.getId_asignacion_proyecto_asg());
        estado.setEstado(asignacionNueva.getEstado());
        return asignaciones_proyecto_repo.save(asignacionNueva);
    }

    public AsignacionProyecto buscarAsigancionProyectoId(Long id) {
        return asignaciones_proyecto_repo.findById(id)
                .orElseThrow(() -> new AsignacionProyectoNoEncontrada("Asignacion " + id + " no encontrada"));
    }


    public List<AsignacionProyecto> buscarAsignacionUltimatix(Long ultimatix) {
        List<AsignacionProyecto> asignaciones_proyecto = new ArrayList<>();
        List<AsignacionProyecto> asignaciones_proyectos = asignaciones_proyecto_repo.findAll();

        for (AsignacionProyecto iterante : asignaciones_proyectos) {
            if (iterante.getUltimatix_asi().equals(ultimatix)) {
                asignaciones_proyecto.add(iterante);
            }
        }

        return asignaciones_proyecto;
    }

    public List<AsignacionProyecto> buscarTodasAsignacionesProyecto() {
        List<AsignacionProyecto> asignaciones_proyectos = asignaciones_proyecto_repo.findAll();
        return asignaciones_proyectos;
    }

    public boolean validarMiembro(Long ultimatix, Long id_equipo) {
        Equipo mio = servicioEquipo.buscarEquipoMio(id_equipo);
        Long[] miembros_ultimatix = mio.getMiembros_ultimatix_asi();
        for (int i = 0; i < miembros_ultimatix.length; i++) {
            if (miembros_ultimatix[i].equals(ultimatix)) {             
                return true;
            }
        }
        return false;
    }
   
    public List<AsignacionProyecto> retornaAsignacionesVencidasUltimatix(Long ultimatix, Long id_equipo){
        List<AsignacionProyecto> mis = new ArrayList<>();
        List<AsignacionProyecto> todos = asignaciones_proyecto_repo.findAll();
        for (AsignacionProyecto iterante : todos) {
            if((iterante.getUltimatix_asi().equals(ultimatix))&&
                (iterante.getEstado().equals(false))&&
                (iterante.getId_equipo_asi().equals(id_equipo))){
                    mis.add(iterante);
                }
        }
        return mis;
    }

    public boolean validarFechaPrevia(Long ultimatix, Long id_equipo, Date fecha_nueva){
        List<AsignacionProyecto> mis = this.retornaAsignacionesVencidasUltimatix(ultimatix,id_equipo);
        if(mis.isEmpty()){
            return false;
        }
        if(mis.get(mis.size()-1).getFecha_baja().after(fecha_nueva)){
            return true;
        }
        return false;
    }

    public void agregarMiembroEquipo(Long ultimatix, Long id_equipo, int asignacion){
        Equipo mi = servicioEquipo.buscarEquipoMio(id_equipo);
        Perfil mio = serviciosPerfil.buscarPerfilMio(ultimatix);
        Long[] miembros_ultimatix = mi.getMiembros_ultimatix_asi();
        String[] nombres_ultimatix = mi.getMiembros_nombres_asi();

        List<Long> listaUltimatix = new ArrayList<>(Arrays.asList(miembros_ultimatix));
        List<String> nombresUltimatix = new ArrayList<>(Arrays.asList(nombres_ultimatix));

        listaUltimatix.add(ultimatix);
        nombresUltimatix.add(mio.getNombres_completos());

        Long[] arr1 = new Long[ listaUltimatix.size() ];
        String[] arr2 = new String[ nombresUltimatix.size() ];
        listaUltimatix.toArray(arr1);
        nombresUltimatix.toArray(arr2);
        mi.setMiembros_ultimatix_asi(arr1);
        mi.setMiembros_nombres_asi(arr2);
        mio.setAsignacion_usuario(mio.getAsignacion_usuario()+asignacion);
        servicioEquipo.actilizarMiembros(mi);
        serviciosPerfil.actualizarAsignacion(mio);
    }

    public void restarAsignacion(Long ultimatix, int asignacion){
        Perfil miPerfil = serviciosPerfil.perfilUltimatix(ultimatix);
        miPerfil.setAsignacion_usuario(miPerfil.getAsignacion_usuario()-asignacion);
        serviciosPerfil.actualizarAsignacion(miPerfil);
    }

    public void quitarMiembroEquipo(Long ultimatix, Long id_equipo){
        Equipo mi = servicioEquipo.buscarEquipoMio(id_equipo);
        Long[] misUltimatix = mi.getMiembros_ultimatix_asi();
        String[] misNombres = mi.getMiembros_nombres_asi();
        List<Long> listaUltimatix = new ArrayList<>();
        List<String> listaNombres = new ArrayList<>();
        for (int i=0;i<misUltimatix.length;i++){
            if (!misUltimatix[i].equals(ultimatix)){
                listaUltimatix.add(misUltimatix[i]);
                listaNombres.add(misNombres[i]);
            }
        }

        Long[] arr1 = new Long[ listaUltimatix.size() ];
        String[] arr2 = new String[ listaNombres.size() ];

        listaUltimatix.toArray(arr1);
        listaNombres.toArray(arr2);

        mi.setMiembros_ultimatix_asi(arr1);
        mi.setMiembros_nombres_asi(arr2);
 
        servicioEquipo.actilizarMiembros(mi);        
    }

    public List<AsignacionProyecto> listaAsignaciones(Long ultimatix){
        List<AsignacionProyecto> listaProyectos = buscarTodasAsignacionesProyecto();
        List<AsignacionProyecto> listaUltimatix = new ArrayList<>();
        for (AsignacionProyecto asignacionProyecto : listaProyectos) {
            if (asignacionProyecto.getUltimatix_asi().equals(ultimatix)) {
                listaUltimatix.add(asignacionProyecto);
            }
        }
        return listaProyectos;
    }

    public List<AsignacionProyecto> buscarAsignaciones(){
        return asignaciones_proyecto_repo.findAll();
    }
}
