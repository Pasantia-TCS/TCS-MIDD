package com.midd.core.repositorio;

import com.midd.core.modelo.Equipo;


import org.springframework.data.jpa.repository.JpaRepository;

// INNTERFAZ QUE EXTIENDE DEL JPA 
// JPA VINCULADO CON EL MODELO DE EQUIPO
public interface EquipoRepo extends JpaRepository<Equipo, Long>{
   
	
}