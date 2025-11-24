package com.gestion.RRHH.Repository;

import com.gestion.RRHH.DTO.PostulanteRevisionDTO;
import com.gestion.RRHH.model.PostulanteProceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostulantesProcesoRepository extends JpaRepository<PostulanteProceso, Integer> {

    // 🔹 1) Listar todos los postulantes de un proceso (por id_proceso)
    List<PostulanteProceso> findByProceso_IdProceso(Integer idProceso);

    // 🔹 2) Buscar un vínculo específico postulante–proceso
    Optional<PostulanteProceso> findByPostulante_IdPostulanteAndProceso_IdProceso(
            Integer idPostulante,
            Integer idProceso
    );

    // 🔹 3) Listar postulantes de un puesto en etapa REVISION_CV (para tu bandeja)
    @Query(value = """
        SELECT 
            p.id_postulante AS idPostulante,
            p.nombres AS nombres,
            p.apellido_paterno AS apellidoPaterno,
            p.apellido_materno AS apellidoMaterno,
            p.email AS email,
            pu.nombre_puesto AS nombrePuesto,
            pp.estado AS estado,
            pp.calificacion AS calificacion
        FROM postulantes p
        INNER JOIN postulantes_proceso pp 
            ON p.id_postulante = pp.id_postulante
        INNER JOIN procesos_seleccion ps 
            ON pp.id_proceso_actual = ps.id_proceso
        INNER JOIN puestos pu 
            ON ps.id_puesto = pu.id_puesto
        WHERE ps.id_puesto = :idPuesto
          AND ps.etapa_actual = 'REVISION_CV'
        ORDER BY p.apellido_paterno, p.nombres
        """,
            nativeQuery = true)
    List<PostulanteRevisionDTO> findPostulantesEnRevisionPorPuesto(@Param("idPuesto") Integer idPuesto);
}
