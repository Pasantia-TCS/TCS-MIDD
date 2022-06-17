package com.midd.core.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="aplicaciones_ac")
public class Aplicaciones_catalogo implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_aplicacion",nullable = false,updatable = false)
	private Long id;
	
	@Column(name="nombre_aplicacion",nullable=false)
	private String 	nombre;
	
	public Aplicaciones_catalogo(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	public Aplicaciones_catalogo() {
		super();
	}

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
		return "Aplicaciones_catalogo [id=" + id + ", nombre=" + nombre + "]";
	}
}
