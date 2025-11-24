package com.gestion.clientes.controller;

import com.gestion.clientes.Repository.PuestoRepository;
import com.gestion.clientes.exception.ResourceNotFoundException;
import com.gestion.clientes.model.Puesto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/RRHH/puestos")
public class PuestoController {

    private final PuestoRepository puestoRepository;

    public PuestoController(PuestoRepository puestoRepository) {
        this.puestoRepository = puestoRepository;
    }

    // 🔹 LISTAR TODOS
    @GetMapping
    public List<Puesto> listarPuestos() {
        return puestoRepository.findAll();
    }

    // 🔹 OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Puesto> obtenerPuestoPorId(@PathVariable Long id)
            throws ResourceNotFoundException {

        Puesto puesto = puestoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No existe el puesto con id: " + id));

        return ResponseEntity.ok(puesto);
    }

    // 🔹 CREAR NUEVO PUESTO
    @PostMapping
    public Puesto crearPuesto(@RequestBody Puesto puesto) {
        return puestoRepository.save(puesto);
    }

    // 🔹 ACTUALIZAR PUESTO
    @PutMapping("/{id}")
    public ResponseEntity<Puesto> actualizarPuesto(@PathVariable Long id,
                                                   @RequestBody Puesto detallesPuesto)
            throws ResourceNotFoundException {

        Puesto puesto = puestoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No existe el puesto con id: " + id));

        puesto.setNombre_puesto(detallesPuesto.getNombre_puesto());
        puesto.setDepartamento(detallesPuesto.getDepartamento());
        puesto.setArea(detallesPuesto.getArea());
        puesto.setDescripcion(detallesPuesto.getDescripcion());
        puesto.setNivel_jerarquico(detallesPuesto.getNivel_jerarquico());
        puesto.setSalario_minimo(detallesPuesto.getSalario_minimo());
        puesto.setSalario_maximo(detallesPuesto.getSalario_maximo());
        puesto.setActivo(detallesPuesto.getActivo());

        Puesto puestoActualizado = puestoRepository.save(puesto);
        return ResponseEntity.ok(puestoActualizado);
    }

    // 🔹 ELIMINAR PUESTO
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarPuesto(@PathVariable Long id)
            throws ResourceNotFoundException {

        Puesto puesto = puestoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No existe el puesto con id: " + id));

        puestoRepository.delete(puesto);

        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
