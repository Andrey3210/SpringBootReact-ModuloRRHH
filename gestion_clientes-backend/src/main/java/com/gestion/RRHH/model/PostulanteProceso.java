package com.gestion.RRHH.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "postulantes_proceso",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_postulante", "id_proceso_actual"})
)
public class PostulanteProceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_postulante_proceso")
    private Integer idPostulanteProceso;

    // 🔗 FK a postulantes
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_postulante", nullable = false)
    private Postulante postulante;

    // 🔗 FK a procesos_seleccion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proceso_actual", nullable = false)
    private ProcesoSeleccion proceso;

    @Enumerated(EnumType.STRING)
    @Column(name = "etapa_actual", nullable = false)
    private Etapa etapaActual = Etapa.REVISION_CV;

    @Column(name = "calificacion", precision = 5, scale = 2)
    private BigDecimal calificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPostulanteProceso estado = EstadoPostulanteProceso.ACTIVO;

    @Column(name = "fecha_vinculacion", updatable = false)
    private LocalDateTime fechaVinculacion = LocalDateTime.now();

    @Column(name = "fecha_ultima_actualizacion")
    private LocalDateTime fechaUltimaActualizacion = LocalDateTime.now();

    @Column(name = "motivo_rechazo")
    private String motivoRechazo;

    @PreUpdate
    public void actualizarFecha() {
        this.fechaUltimaActualizacion = LocalDateTime.now();
    }

    // 👉 Enum de ETAPA DEL PROCESO
    public enum Etapa {
        REVISION_CV, ENTREVISTA, PRUEBA, OFERTA, CONTRATACION
    }

    // 👉 Enum de ESTADO DEL POSTULANTE
    public enum EstadoPostulanteProceso {
        ACTIVO, DESCARTADO, CONTRATADO
    }
}
