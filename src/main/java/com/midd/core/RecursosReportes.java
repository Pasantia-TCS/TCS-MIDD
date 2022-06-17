package com.midd.core;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.midd.core.administracion.*;
import com.midd.core.modelo.*;
import com.midd.core.reportes.*;


@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/reportes")
public class RecursosReportes {
    
    private final ServicioEquipo servicio_equipos;
    private final ServiciosActivos servicio_activos;
    private final ServiciosPerfil servicio_perfil;
    private final ServicosAsignacionProyecto servicio_asignacion_proyecto;
    private final ServiciosAsociados servicio_asociado;

    public RecursosReportes(ServicioEquipo servicio_equipos, ServiciosActivos servicio_activos,
            ServiciosPerfil servicio_perfil, ServicosAsignacionProyecto servicio_asignacion_proyecto,
            ServiciosAsociados servicio_asociado) {
        this.servicio_equipos = servicio_equipos;
        this.servicio_activos = servicio_activos;
        this.servicio_perfil = servicio_perfil;
        this.servicio_asignacion_proyecto = servicio_asignacion_proyecto;
        this.servicio_asociado = servicio_asociado;
    }


    @GetMapping("/equipos-reportes")
    public void reportesEquipos(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormat.format(new Date());
        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=reporte-equipos " + fechaActual +  ".xlsx";
        response.setHeader(cabecera, valor);

        List<Equipo> equipos = servicio_equipos.buscarTodosEquipos();
        
        ReporteEquipos reporteEquipos = new ReporteEquipos(equipos);
        reporteEquipos.export(response);
    }
  

    @GetMapping("/asignaciones-reportes")
    public void reportesAsignaciones(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaActual = dateFormat.format(new Date());
        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=reporte-asignaciones " + fechaActual +  ".xlsx";
        response.setHeader(cabecera, valor);

        List<AsignacionProyecto> asignaciones = servicio_asignacion_proyecto.buscarAsignaciones();
        
        ReporteAsignaciones reporte_asignaciones = new ReporteAsignaciones(this.servicio_perfil, this.servicio_asociado, this.servicio_equipos, asignaciones);
        reporte_asignaciones.export(response);
    }

    @GetMapping("/activos-reportes")
    public void reportesActivos(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaActual = dateFormat.format(new Date());
        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=reporte-activos " + fechaActual +  ".xlsx";
        response.setHeader(cabecera, valor);

        List<Activos> activos = servicio_activos.buscarTodos();
        
        ReporteActivo reporte_activos = new ReporteActivo(this.servicio_perfil, this.servicio_asociado, activos);
        reporte_activos.export(response);
    }

    @GetMapping("/habilidades-reportes")
    public void reportesHabilidades(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaActual = dateFormat.format(new Date());
        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=reporte-habilidades " + fechaActual +  ".xlsx";
        response.setHeader(cabecera, valor);

        List<Perfil> habilidades = servicio_perfil.buscarTodos();
        
        ReporteHabilidades reporte_habilidades = new ReporteHabilidades(habilidades, servicio_asociado);
        reporte_habilidades.export(response);
    }

}
