package com.midd.core.administracion;
import com.midd.core.repositorio.TipoProyectoRepo;
import java.util.List;
import org.springframework.stereotype.Service;
import com.midd.core.modelo.TipoProyecto;
@Service
public class ServicioCatalogoTipoProyecto {
    private final TipoProyectoRepo tipoProyectoRepo;
    // CONSTRUCTOR
    public ServicioCatalogoTipoProyecto(TipoProyectoRepo tipoProyectoRepo) {
        this.tipoProyectoRepo = tipoProyectoRepo;
    }
    // FUNCION BUSCAR TODOS LOS TIPOS DE PROYECTOS
    public List<TipoProyecto> obtenerTiposProyectos(){
        return this.tipoProyectoRepo.findAll();           
    }    
}