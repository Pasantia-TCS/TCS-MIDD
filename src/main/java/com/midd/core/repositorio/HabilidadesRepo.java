package com.midd.core.repositorio;

import com.midd.core.modelo.Habilidades;

import org.springframework.data.jpa.repository.JpaRepository;


// INNTERFAZ QUE EXTIENDE DEL JPA 
// JPA VINCULADO CON EL MODELO DE HABILIDADES
public interface HabilidadesRepo extends JpaRepository<Habilidades, Long>{

}
