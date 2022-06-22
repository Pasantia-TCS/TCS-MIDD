package com.midd.core.repositorio;

import com.midd.core.modelo.Asociado;

import org.springframework.data.jpa.repository.JpaRepository;

// INNTERFAZ QUE EXTIENDE DEL JPA 
// JPA VINCULADO CON EL MODELO DE ASOCIADOS
public interface AsociadosRepo extends JpaRepository<Asociado, Long>{
}
