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
@Table(name="tipo_ac")
public class Tipo implements Serializable{

	// CREACION DEL ID DE LA TABLA
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// CREACION DE COLUMNA
	@Column(name="id_tipo",nullable = false,updatable = false)
	private Long id;
	
	// CREACION DE COLUMNA
	@Column(name="nombre_tipo",nullable=false)
	private String 	nombre;
	
	public Tipo(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	public Tipo() {
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
		return "Tipo [id=" + id + ", nombre=" + nombre + "]";
	}
}
