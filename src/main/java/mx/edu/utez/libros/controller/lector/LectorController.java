package mx.edu.utez.libros.controller.lector;

import mx.edu.utez.libros.model.lector.Lector;
import mx.edu.utez.libros.service.lector.LectorServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lector")
@CrossOrigin("*")
public class LectorController {

    private final LectorServicio lectorServicio;

    public LectorController(LectorServicio lectorServicio) {
        this.lectorServicio = lectorServicio;
    }

    @PatchMapping("/{id}/rehabilitar")
    public ResponseEntity<Lector> rehabilitarLector(@PathVariable Long id) {
        return ResponseEntity.ok(this.lectorServicio.rehabilitarLector(id));
    }
}