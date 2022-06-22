package com.midd.core.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.midd.core.modelo.Aplicaciones_catalogo;


// INNTERFAZ QUE EXTIENDE DEL JPA 
// JPA VINCULADO CON EL MODELO DE APLICACIONES
public interface AplicacionesRepo extends JpaRepository<Aplicaciones_catalogo, Long>{

}
