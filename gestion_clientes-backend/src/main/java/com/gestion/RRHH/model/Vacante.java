package com.gestion.RRHH.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vacantes")
public class Vacante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vacante")
    private Integer idVacante;

    @ManyToOne
    @JoinColumn(name = "id_reclutador", nullable = false)
    private Reclutador reclutador;

    private String nombre;
    private String requisitos;
    private String modalidad;
    private String rangoSalarial;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoVacante estado = EstadoVacante.PAUSADA;

    private String descripcion;
    private String departamento;
    private String prioridad;
    private String tipoContrato;
    private java.sql.Date fechaPublicacion;
    private java.sql.Date fechaCierre;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    public enum EstadoVacante {
        ABIERTA, CERRADA, PAUSADA
    }
}