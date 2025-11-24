package com.gestion.RRHH.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "puestos")
public class Puesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_puesto")
    private Integer idPuesto;

    @Column(name = "nombre_puesto", nullable = false, length = 120)
    private String nombre_puesto;

    @Column(name = "departamento", nullable = false, length = 120)
    private String departamento;

    @Column(name = "area", nullable = false, length = 120)
    private String area;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "nivel_jerarquico", length = 50)
    private String nivel_jerarquico;

    @Column(name = "salario_minimo")
    private Double salario_minimo;

    @Column(name = "salario_maximo")
    private Double salario_maximo;

    @Column(name = "activo")
    private Integer activo = 1;

    @Column(name = "fecha_creacion")
    private LocalDateTime fecha_creacion;

    // ============================
    //   CONSTRUCTORES
    // ============================

    public Puesto() {
    }

    public Puesto(String nombre_puesto, String departamento, String area, String descripcion,
                  String nivel_jerarquico, Double salario_minimo, Double salario_maximo, Integer activo) {
        this.nombre_puesto = nombre_puesto;
        this.departamento = departamento;
        this.area = area;
        this.descripcion = descripcion;
        this.nivel_jerarquico = nivel_jerarquico;
        this.salario_minimo = salario_minimo;
        this.salario_maximo = salario_maximo;
        this.activo = activo;
    }

    // ============================
    //   GETTERS Y SETTERS
    // ============================

    public int getId_puesto() {
        return idPuesto;
    }

    public void setId_puesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }

    public String getNombre_puesto() {
        return nombre_puesto;
    }

    public void setNombre_puesto(String nombre_puesto) {
        this.nombre_puesto = nombre_puesto;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivel_jerarquico() {
        return nivel_jerarquico;
    }

    public void setNivel_jerarquico(String nivel_jerarquico) {
        this.nivel_jerarquico = nivel_jerarquico;
    }

    public Double getSalario_minimo() {
        return salario_minimo;
    }

    public void setSalario_minimo(Double salario_minimo) {
        this.salario_minimo = salario_minimo;
    }

    public Double getSalario_maximo() {
        return salario_maximo;
    }

    public void setSalario_maximo(Double salario_maximo) {
        this.salario_maximo = salario_maximo;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDateTime fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    @PrePersist
    public void prePersist() {
        this.fecha_creacion = LocalDateTime.now();
    }
}
