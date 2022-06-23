package com.midd.core.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.midd.core.modelo.Habilidades_funcionales;

// INNTERFAZ QUE EXTIENDE DEL JPA 
// JPA VINCULADO CON EL MODELO DE HABILIDADES FUNCIONALES
public interface HabilidadesFuncionalesRepo extends JpaRepository<Habilidades_funcionales, Long>{

}
