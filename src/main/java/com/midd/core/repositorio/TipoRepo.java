package com.midd.core.repositorio;

import com.midd.core.modelo.Tipo;

import org.springframework.data.jpa.repository.JpaRepository;


// INNTERFAZ QUE EXTIENDE DEL JPA 
// JPA VINCULADO CON EL MODELO DE TIPO DE ACTIVO
public interface TipoRepo extends JpaRepository<Tipo, Long>{

}
