package com.gestion.RRHH.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "postulantes")
public class Postulante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_postulante")
    private Integer idPostulante;

    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String direccion;
    private String email;
    private Date fechaNacimiento;
    private String genero;
    private String estadoCivil;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_postulacion")
    private EstadoPostulante estadoPostulacion = EstadoPostulante.ACTIVO;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Column(name = "fecha_ultima_actualizacion")
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    @PreUpdate
    public void actualizarFecha() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    public enum EstadoPostulante {
        ACTIVO, DESCARTADO, CONTRATADO
    }
}
