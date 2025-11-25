package com.gestion.RRHH.Repository;

import com.gestion.RRHH.DTO.PostulanteProcesoDTO;
import com.gestion.RRHH.model.PostulanteProceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostulantesProcesoRepository extends JpaRepository<PostulanteProceso, Integer> {

    @Query("""
    SELECT new com.gestion.RRHH.DTO.PostulanteProcesoDTO(
        p.idPostulante,
        p.nombres,
        p.apellidoPaterno,
        p.apellidoMaterno,
        p.email,
        p.telefono,
        p.genero,
        p.direccion,
        p.fechaNacimiento,
        p.estadoCivil,
        pp.estado,
        ps.etapaActual,
        pp.calificacion,
        pu.nombre_puesto
    )
    FROM PostulanteProceso pp
    JOIN pp.postulante p
    JOIN pp.proceso ps
    JOIN ps.puesto pu
    WHERE pu.idPuesto = :idPuesto
      AND ps.etapaActual = 'REVISION_CV'
      AND pp.estado = 'ACTIVO'
    """)
    List<PostulanteProcesoDTO> findPostulantesByPuestoEnRevision(@Param("idPuesto") Integer idPuesto);

}
