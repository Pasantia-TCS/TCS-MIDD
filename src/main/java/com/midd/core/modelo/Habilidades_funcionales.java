package com.midd.core.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// CREACION DE LA ENTIDAD
@Entity
@Table(name="habilidades_funcionales_ac")
public class Habilidades_funcionales implements Serializable{
	
	// CREACION DEL ID
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// CREACION DE COLUMNA
	@Column(name="id_habilidad",nullable = false,updatable = false)
	private Long id;
	
	// CREACION DE COLUMNA
	@Column(name="nombre_habilidad",nullable=false)
	private String 	nombre;

	public Habilidades_funcionales(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public Habilidades_funcionales() {
	}

	// GETTERS Y SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Habilidades_funcionales [id=" + id + ", nombre=" + nombre + "]";
	}
}