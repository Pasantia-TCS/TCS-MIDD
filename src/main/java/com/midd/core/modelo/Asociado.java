package com.midd.core.modelo;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// CREACION DE LA ENTIDAD
@Entity
@Table(name="empleados_ac")
public class Asociado implements Serializable{
	@Id
	// CREACION DE COLUMNA
	@Column(name="id_empleados",nullable=false,updatable=false)
	private Long 	id_numero_Ultimatix;
	
	// CREACION DE COLUMNA
	@Column(name="contrasenha_empleados",nullable=false)
	private String 	clave;
	
	// CREACION DE COLUMNA
	@Column(name="nombre_empleados",nullable=false)
	private String 	nombre;
	
	// CREACION DE COLUMNA
	@Column(name="apellido_empleados",nullable=false)
	private String 	apellido;
	
	// CREACION DE COLUMNA
	@Column(name="telefono_empleados",nullable=false)
	private String 	telefono;
	
	// CREACION DE COLUMNA
	@Column(name="correo_empleados",nullable=false)
	private String 	correo;

	// CREACION DE COLUMNA
	@Column(name="fecha_login_empleados", nullable = true)
	private Date fecha_login;

	// CREACION DE COLUMNA
	@Column(name="intentos_empleados", nullable = true)
	private int intentos;

	// CREACION DE COLUMNAV
	@Column(name="codigo_seguridad_empleados", nullable = true)
	private String token;

	// CREACION DE COLUMNA
	@Column(name="estado_empleados", nullable = true)
	private Boolean estado;

	public Asociado() {
	}

	public Asociado(Long id_numero_Ultimatix, String clave, String nombre, String apellido, String telefono,
			String correo, Date fecha_login,int intentos ,String token, boolean estado) {
		this.id_numero_Ultimatix = id_numero_Ultimatix;
		this.clave = clave;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.correo = correo;
		this.fecha_login = fecha_login;
		this.intentos = intentos;
		this.token = token;
		this.estado = estado;
	}

	// GETTERS Y SETTERS
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Long getId_numero_Ultimatix() {
		return id_numero_Ultimatix;
	}

	public void setId_numero_Ultimatix(Long id_numero_Ultimatix) {
		this.id_numero_Ultimatix = id_numero_Ultimatix;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Date getFecha_login() {
		return fecha_login;
	}

	public void setFecha_login(Date fecha_login) {
		this.fecha_login = fecha_login;
	}

	public int getIntentos() {
		return intentos;
	}

	public void setIntentos(int intentos) {
		this.intentos = intentos;
	}



	
}
