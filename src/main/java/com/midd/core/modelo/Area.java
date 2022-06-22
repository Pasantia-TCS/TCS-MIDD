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
@Table(name="area_ac")
public class Area implements Serializable{
	
	// CREACION DEL ID DE LA TABLA
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// CREACION DE COLUMNA
	@Column(name="id_area",nullable = false,updatable = false)
	private Long id;
	
	// CREACION DE COLUMNA
	@Column(name="nombre_area",nullable=false)
	private String 	nombre;

	public Area(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	public Area() {
		super();
	}

	//GETTERS Y SETTERS
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
	//toString
	@Override
	public String toString() {
		return "Area [id=" + id + ", nombre=" + nombre + "]";
	}
}
