package com.midd.core.repositorio;

import com.midd.core.modelo.TipoProyecto;

import org.springframework.data.jpa.repository.JpaRepository;


// INNTERFAZ QUE EXTIENDE DEL JPA 
// JPA VINCULADO CON EL MODELO DE TIPO DE PROYECTO
public interface TipoProyectoRepo extends JpaRepository<TipoProyecto, Long>{
    
}
