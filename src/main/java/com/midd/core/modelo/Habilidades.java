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
@Table(name="habilidades_tecnicas_ac")
public class Habilidades implements Serializable{
	
	// CREACION DEL ID
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// CREACION DE COLUMNA
	@Column(name="id_habilidad_tecnica",nullable = false,updatable = false)
	private Long id;
	
	// CREACION DE COLUMNA
	@Column(name="nombre_habilidad_tecnica",nullable=false)
	private String 	nombre;

	public Habilidades(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Habilidades() {
		super();
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
		return "Habilidades [id=" + id + ", nombre=" + nombre + "]";
	}
}
