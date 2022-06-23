package com.midd.core.repositorio;

import com.midd.core.modelo.AsignacionProyecto;

import org.springframework.data.jpa.repository.JpaRepository;

// INNTERFAZ QUE EXTIENDE DEL JPA 
// JPA VINCULADO CON EL MODELO DE ASIGNACIONES
public interface AsignacionesProyectoRepo extends JpaRepository<AsignacionProyecto,Long> {
    
}
