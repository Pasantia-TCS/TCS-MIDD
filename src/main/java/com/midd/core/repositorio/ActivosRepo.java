package com.midd.core.repositorio;

import com.midd.core.modelo.Activos;

import org.springframework.data.jpa.repository.JpaRepository;

// INNTERFAZ QUE EXTIENDE DEL JPA 
// JPA VINCULADO CON EL MODELO DE ACTIVOS
public interface ActivosRepo extends JpaRepository<Activos, Long>{

}
