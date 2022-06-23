package com.midd.core.modelo;

import javax.persistence.*;
import java.io.Serializable;


// CREACION DE LA ENTIDAD
@Entity
@Table(name = "tipo_proyecto_asi")
public class TipoProyecto implements Serializable {

    // CREACION DEL ID DE LA TABLA
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // CREACION DE COLUMNA
    @Column(name="id_tipo_proyecto",nullable=false,updatable=false)
    private Long id_tipo_proyecto;

    // CREACION DE COLUMNA
    @Column(name="nombre_proyecto",nullable=false,updatable=false)
    private String nombre_tipo_proyecto;

    public TipoProyecto() {
    }

    public TipoProyecto(long id_tipo_proyecto, String nombre_tipo_proyecto) {
        this.id_tipo_proyecto = id_tipo_proyecto;
        this.nombre_tipo_proyecto = nombre_tipo_proyecto;
    }

    // GETTERS Y SETTERS
    public long getId_tipo_proyecto() {
        return id_tipo_proyecto;
    }

    public void setId_tipo_proyecto(long id_tipo_proyecto) {
        this.id_tipo_proyecto = id_tipo_proyecto;
    }

    public String getNombre_tipo_proyecto() {
        return nombre_tipo_proyecto;
    }

    public void setNombre_tipo_proyecto(String nombre_tipo_proyecto) {
        this.nombre_tipo_proyecto = nombre_tipo_proyecto;
    }
}
