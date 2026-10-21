package mx.edu.utez.libros.controller.prestamo;

import jakarta.validation.Valid;
import mx.edu.utez.libros.controller.prestamo.dto.SolicitarPrestamoDto;
import mx.edu.utez.libros.model.prestamo.Prestamo;
import mx.edu.utez.libros.service.prestamo.PrestamoServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamo")
@CrossOrigin("*")
public class PrestamoController {

    private final PrestamoServicio prestamoServicio;

    public PrestamoController(PrestamoServicio prestamoServicio) {
        this.prestamoServicio = prestamoServicio;
    }

    @PostMapping("/solicitar")
    public ResponseEntity<Prestamo> solicitarPrestamo(@RequestBody @Valid SolicitarPrestamoDto payload) {
        return ResponseEntity.status(201).body(prestamoServicio.solicitarPrestamo(payload));
    }

    @PatchMapping("/{id}/devolver")
    public ResponseEntity<Prestamo> devolverLibro(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoServicio.devolverLibro(id));
    }

    @PatchMapping("/{id}/renovar")
    public ResponseEntity<Prestamo> renovarPrestamo(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoServicio.renovarPrestamo(id));
    }

    @GetMapping("/lector/{lectorId}/activos")
    public ResponseEntity<List<Prestamo>> listarPrestamosActivos(@PathVariable Long lectorId) {
        return ResponseEntity.ok(prestamoServicio.listarPrestamosActivos(lectorId));
    }
}