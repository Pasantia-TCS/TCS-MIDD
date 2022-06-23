package com.midd.core.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// CREACION DE LA ENTIDAD
@Entity
@Table(name="perfil_asi")
public class Perfil implements Serializable{
	// CREACION DEL ID DE LA TABLA
	@Id
	// CREACION DE COLUMNA
	@Column(name="id_ultimatix_perfil",nullable = false,updatable = false)
	private Long id_ultimatix;

	// CREACION DE COLUMNA
	@Column(name="sobreMi_perfil",nullable=true)
	private String sobreMi;
	
	// CREACION DE COLUMNA
	@Column(name="habilidades_tecnica_perfil",nullable=true)
	private String[] habilidades;
	
	// CREACION DE COLUMNA
	@Column(name = "nivel_habilidad_tecnica_perfil",nullable=true)
	private String[] nivel_habilidad;
	
	// CREACION DE COLUMNA
	@Column(name="habilidades_funcional_perfil",nullable=true)
	private String[] habilidades_funcionales;

	// CREACION DE COLUMNA
	@Column(name = "nivel_habilidad_funcional_perfil",nullable=true)
	private String[] nivel_habilidad_funcional;
	
	// CREACION DE COLUMNA
	@Column(name="aplicaciones_perfil",nullable=true)
	private String[] aplicaciones;

	// CREACION DE COLUMNA
	@Column(name = "nivel_aplicacion_perfil",nullable=true)
	private String[] nivel_aplicaciones;
	
	// CREACION DE COLUMNA
	@Column(name="usuario_red_perfil",nullable=true)
	private String usuario_red;

	// CREACION DE COLUMNA
	@Column(name="asignacion_perfil",nullable=true)
	private int asignacion_usuario;

	// CREACION DE COLUMNA
	@Column(name="nombres_perfil",nullable=true)
	private String nombres_completos;

	// CREACION DE COLUMNA
	@Column(name="rol_perfil")
	private String  rol;

	// CREACION DE COLUMNA
	@Column(name="estado_empleados", nullable = true)
	private Boolean estado;
	
	public Perfil() {
	}

	public Perfil(Long id_ultimatix, String sobreMi, String[] habilidades, String[] nivel_habilidad,
			String[] habilidades_funcionales, String[] nivel_habilidad_funcional, String[] aplicaciones,
			String[] nivel_aplicaciones, String usuario_red, int asignacion_usuario, String nombres_completos,
			String rol, Boolean estado) {
		super();
		this.id_ultimatix = id_ultimatix;
		this.sobreMi = sobreMi;
		this.habilidades = habilidades;
		this.nivel_habilidad = nivel_habilidad;
		this.habilidades_funcionales = habilidades_funcionales;
		this.nivel_habilidad_funcional = nivel_habilidad_funcional;
		this.aplicaciones = aplicaciones;
		this.nivel_aplicaciones = nivel_aplicaciones;
		this.usuario_red = usuario_red;
		this.asignacion_usuario = asignacion_usuario;
		this.nombres_completos = nombres_completos;
		this.rol = rol;
		this.estado = estado;
	}

	// GETTERS Y SETTERS
	public Long getId_ultimatix() {
		return id_ultimatix;
	}

	public void setId_ultimatix(Long id_ultimatix) {
		this.id_ultimatix = id_ultimatix;
	}

	public String getSobreMi() {
		return sobreMi;
	}

	public void setSobreMi(String sobreMi) {
		this.sobreMi = sobreMi;
	}

	public String[] getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(String[] habilidades) {
		this.habilidades = habilidades;
	}

	public String[] getNivel_habilidad() {
		return nivel_habilidad;
	}

	public void setNivel_habilidad(String[] nivel_habilidad) {
		this.nivel_habilidad = nivel_habilidad;
	}

	public String[] getHabilidades_funcionales() {
		return habilidades_funcionales;
	}

	public void setHabilidades_funcionales(String[] habilidades_funcionales) {
		this.habilidades_funcionales = habilidades_funcionales;
	}

	public String[] getNivel_habilidad_funcional() {
		return nivel_habilidad_funcional;
	}

	public void setNivel_habilidad_funcional(String[] nivel_habilidad_funcional) {
		this.nivel_habilidad_funcional = nivel_habilidad_funcional;
	}

	public String[] getAplicaciones() {
		return aplicaciones;
	}

	public void setAplicaciones(String[] aplicaciones) {
		this.aplicaciones = aplicaciones;
	}

	public String[] getNivel_aplicaciones() {
		return nivel_aplicaciones;
	}

	public void setNivel_aplicaciones(String[] nivel_aplicaciones) {
		this.nivel_aplicaciones = nivel_aplicaciones;
	}

	public String getUsuario_red() {
		return usuario_red;
	}

	public void setUsuario_red(String usuario_red) {
		this.usuario_red = usuario_red;
	}

	public int getAsignacion_usuario() {
		return asignacion_usuario;
	}

	public void setAsignacion_usuario(int asignacion_usuario) {
		this.asignacion_usuario = asignacion_usuario;
	}

	public String getNombres_completos() {
		return nombres_completos;
	}

	public void setNombres_completos(String nombres_completos) {
		this.nombres_completos = nombres_completos;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
}
