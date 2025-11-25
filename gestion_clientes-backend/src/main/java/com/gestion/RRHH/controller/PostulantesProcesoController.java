package com.gestion.RRHH.controller;

import com.gestion.RRHH.DTO.PostulanteProcesoDTO;
import com.gestion.RRHH.Repository.PostulantesProcesoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/RRHH/postulantes-proceso")
@CrossOrigin(origins = "http://localhost:3000")
public class PostulantesProcesoController {

    @Autowired
    private PostulantesProcesoRepository postulantesProcesoRepository;

    @GetMapping("/puesto/{idPuesto}/revision-cv")
    public List<PostulanteProcesoDTO> listarPorPuestoEnRevision(@PathVariable Integer idPuesto) {
        return postulantesProcesoRepository.findPostulantesByPuestoEnRevision(idPuesto);
    }
}
