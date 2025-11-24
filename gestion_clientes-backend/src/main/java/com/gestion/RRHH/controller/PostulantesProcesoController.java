package com.gestion.RRHH.controller;

import com.gestion.RRHH.Repository.PostulantesProcesoRepository;
import com.gestion.RRHH.Repository.PostulanteRepository;
import com.gestion.RRHH.Repository.ProcesoSeleccionRepository;
import com.gestion.RRHH.DTO.PostulanteRevisionDTO;
import com.gestion.RRHH.exception.ResourceNotFoundException;
import com.gestion.RRHH.model.Postulante;
import com.gestion.RRHH.model.PostulanteProceso;
import com.gestion.RRHH.model.ProcesoSeleccion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/RRHH/postulantes-proceso")
public class PostulantesProcesoController {

    private final PostulantesProcesoRepository postulantesProcesoRepository;
    private final PostulanteRepository postulanteRepository;
    private final ProcesoSeleccionRepository procesoSeleccionRepository;

    public PostulantesProcesoController(PostulantesProcesoRepository postulantesProcesoRepository,
                                        PostulanteRepository postulanteRepository,
                                        ProcesoSeleccionRepository procesoSeleccionRepository) {
        this.postulantesProcesoRepository = postulantesProcesoRepository;
        this.postulanteRepository = postulanteRepository;
        this.procesoSeleccionRepository = procesoSeleccionRepository;
    }

    // =========================
    //        CONSULTAS
    // =========================

    // Listar todos
    @GetMapping
    public List<PostulanteProceso> listarTodos() {
        return postulantesProcesoRepository.findAll();
    }

    // Obtener uno por ID
    @GetMapping("/{id}")
    public ResponseEntity<PostulanteProceso> obtenerPorId(@PathVariable Integer id)
            throws ResourceNotFoundException {

        PostulanteProceso pp = postulantesProcesoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el vínculo postulante-proceso con id: " + id));

        return ResponseEntity.ok(pp);
    }

    // Listar postulantes de un proceso específico
    @GetMapping("/proceso/{idProceso}")
    public ResponseEntity<List<PostulanteProceso>> listarPorProceso(@PathVariable Integer idProceso)
            throws ResourceNotFoundException {

        // Validar que exista el proceso
        procesoSeleccionRepository.findById(idProceso)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el proceso de selección con id: " + idProceso));

        List<PostulanteProceso> lista =
                postulantesProcesoRepository.findByProceso_IdProceso(idProceso);

        return ResponseEntity.ok(lista);
    }

    // Listar postulantes por puesto y etapa REVISION_CV (para tu bandeja de revisión)
    @GetMapping("/puesto/{idPuesto}/revision-cv")
    public ResponseEntity<List<PostulanteRevisionDTO>> listarEnRevisionPorPuesto(
            @PathVariable Integer idPuesto) {

        List<PostulanteRevisionDTO> lista =
                postulantesProcesoRepository.findPostulantesEnRevisionPorPuesto(idPuesto);

        return ResponseEntity.ok(lista);
    }

    // =========================
    //        CREAR
    // =========================

    // DTO de entrada para crear vínculo postulante-proceso
    public static class CrearPostulanteProcesoRequest {
        public Integer idPostulante;
        public Integer idProcesoActual;
        public String etapaActual;  // REVISION_CV, ENTREVISTA, etc.
        public String estado;       // ACTIVO, DESCARTADO, CONTRATADO
        public BigDecimal calificacion;
        public String motivoRechazo;
    }

    @PostMapping
    public ResponseEntity<PostulanteProceso> crearVinculo(
            @RequestBody CrearPostulanteProcesoRequest request) throws ResourceNotFoundException {

        Postulante postulante = postulanteRepository.findById(request.idPostulante)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el postulante con id: " + request.idPostulante));

        ProcesoSeleccion proceso = procesoSeleccionRepository.findById(request.idProcesoActual)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el proceso de selección con id: " + request.idProcesoActual));

        // Validar que no exista ya el vínculo (por la UNIQUE)
        postulantesProcesoRepository.findByPostulante_IdPostulanteAndProceso_IdProceso(
                        request.idPostulante, request.idProcesoActual)
                .ifPresent(pp -> {
                    throw new RuntimeException("El postulante ya está vinculado a este proceso.");
                });

        PostulanteProceso nuevo = new PostulanteProceso();
        nuevo.setPostulante(postulante);
        nuevo.setProceso(proceso);

        if (request.etapaActual != null) {
            nuevo.setEtapaActual(PostulanteProceso.Etapa.valueOf(request.etapaActual));
        }
        if (request.estado != null) {
            nuevo.setEstado(PostulanteProceso.EstadoPostulanteProceso.valueOf(request.estado));
        }
        nuevo.setCalificacion(request.calificacion);
        nuevo.setMotivoRechazo(request.motivoRechazo);

        PostulanteProceso guardado = postulantesProcesoRepository.save(nuevo);
        return ResponseEntity.ok(guardado);
    }

    // =========================
    //        ACTUALIZAR
    // =========================

    // Actualizar todo el registro (estado, etapa, calificación, motivo)
    public static class ActualizarPostulanteProcesoRequest {
        public String etapaActual;
        public String estado;
        public BigDecimal calificacion;
        public String motivoRechazo;
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostulanteProceso> actualizarCompleto(
            @PathVariable Integer id,
            @RequestBody ActualizarPostulanteProcesoRequest request)
            throws ResourceNotFoundException {

        PostulanteProceso pp = postulantesProcesoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el vínculo postulante-proceso con id: " + id));

        if (request.etapaActual != null) {
            pp.setEtapaActual(PostulanteProceso.Etapa.valueOf(request.etapaActual));
        }
        if (request.estado != null) {
            pp.setEstado(PostulanteProceso.EstadoPostulanteProceso.valueOf(request.estado));
        }
        pp.setCalificacion(request.calificacion);
        pp.setMotivoRechazo(request.motivoRechazo);

        PostulanteProceso actualizado = postulantesProcesoRepository.save(pp);
        return ResponseEntity.ok(actualizado);
    }

    // PATCH solo etapa
    @PatchMapping("/{id}/etapa")
    public ResponseEntity<PostulanteProceso> actualizarEtapa(
            @PathVariable Integer id,
            @RequestParam String etapa)
            throws ResourceNotFoundException {

        PostulanteProceso pp = postulantesProcesoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el vínculo postulante-proceso con id: " + id));

        pp.setEtapaActual(PostulanteProceso.Etapa.valueOf(etapa));
        PostulanteProceso actualizado = postulantesProcesoRepository.save(pp);
        return ResponseEntity.ok(actualizado);
    }

    // PATCH solo estado y calificación (por ejemplo, al evaluarlo)
    @PatchMapping("/{id}/evaluacion")
    public ResponseEntity<PostulanteProceso> actualizarEvaluacion(
            @PathVariable Integer id,
            @RequestParam(required = false) BigDecimal calificacion,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String motivoRechazo)
            throws ResourceNotFoundException {

        PostulanteProceso pp = postulantesProcesoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el vínculo postulante-proceso con id: " + id));

        if (calificacion != null) {
            pp.setCalificacion(calificacion);
        }
        if (estado != null) {
            pp.setEstado(PostulanteProceso.EstadoPostulanteProceso.valueOf(estado));
        }
        if (motivoRechazo != null) {
            pp.setMotivoRechazo(motivoRechazo);
        }

        PostulanteProceso actualizado = postulantesProcesoRepository.save(pp);
        return ResponseEntity.ok(actualizado);
    }

    // =========================
    //        ELIMINAR
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id)
            throws ResourceNotFoundException {

        PostulanteProceso pp = postulantesProcesoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el vínculo postulante-proceso con id: " + id));

        postulantesProcesoRepository.delete(pp);
        return ResponseEntity.noContent().build();
    }
}
