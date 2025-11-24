package com.gestion.RRHH.Repository;

import com.gestion.RRHH.model.ProcesoSeleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcesoSeleccionRepository extends JpaRepository<ProcesoSeleccion, Integer> {

    // obtener procesos por vacante
    List<ProcesoSeleccion> findByVacante_IdVacante(Integer idVacante);

    // Filtrar por puesto + etapa
    List<ProcesoSeleccion> findByPuesto_IdPuestoAndEtapaActual(Integer idPuesto,
                                                               ProcesoSeleccion.EtapaProceso etapaActual);
}
