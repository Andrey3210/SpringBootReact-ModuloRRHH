package com.gestion.RRHH.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

import com.gestion.RRHH.model.PostulanteProceso.EstadoPostulanteProceso;
import com.gestion.RRHH.model.ProcesoSeleccion.EtapaProceso;

@Data
@NoArgsConstructor
public class PostulanteProcesoDTO {

    private Integer idPostulante;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String telefono;
    private String genero;
    private String direccion;
    private Date fechaNacimiento;
    private String estadoCivil;
    private EstadoPostulanteProceso estado;
    private EtapaProceso etapaActual;
    private BigDecimal calificacion;
    private String nombrePuesto;

    public PostulanteProcesoDTO(
            Integer idPostulante,
            String nombres,
            String apellidoPaterno,
            String apellidoMaterno,
            String email,
            String telefono,
            String genero,
            String direccion,
            Date fechaNacimiento,
            String estadoCivil,
            EstadoPostulanteProceso estado,
            EtapaProceso etapaActual,
            BigDecimal calificacion,
            String nombrePuesto
    ) {
        this.idPostulante = idPostulante;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.telefono = telefono;
        this.genero = genero;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.estadoCivil = estadoCivil;
        this.estado = estado;
        this.etapaActual = etapaActual;
        this.calificacion = calificacion;
        this.nombrePuesto = nombrePuesto;
    }

    public Integer getEdad() {
        if (fechaNacimiento == null) return null;
        return java.time.Period.between(
                fechaNacimiento.toLocalDate(),
                java.time.LocalDate.now()
        ).getYears();
    }
}
