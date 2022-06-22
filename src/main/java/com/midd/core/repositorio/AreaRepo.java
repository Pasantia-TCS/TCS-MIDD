package com.midd.core.repositorio;

import com.midd.core.modelo.Area;

import org.springframework.data.jpa.repository.JpaRepository;

// INNTERFAZ QUE EXTIENDE DEL JPA 
// JPA VINCULADO CON EL MODELO DE AREA
public interface AreaRepo extends JpaRepository<Area, Long>{

}
