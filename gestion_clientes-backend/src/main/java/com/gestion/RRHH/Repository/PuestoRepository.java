package com.gestion.RRHH.Repository;

import com.gestion.RRHH.model.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuestoRepository extends JpaRepository<Puesto, Long> {

}