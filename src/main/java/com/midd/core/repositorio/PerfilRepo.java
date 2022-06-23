package com.midd.core.repositorio;

import com.midd.core.modelo.Perfil;

import org.springframework.data.jpa.repository.JpaRepository;


// INNTERFAZ QUE EXTIENDE DEL JPA 
// JPA VINCULADO CON EL MODELO DE PERFIL
public interface PerfilRepo extends JpaRepository<Perfil, Long>{

}
