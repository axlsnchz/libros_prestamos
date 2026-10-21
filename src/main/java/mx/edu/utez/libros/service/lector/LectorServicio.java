package mx.edu.utez.libros.service.lector;

import mx.edu.utez.libros.exception.BadRequestException;
import mx.edu.utez.libros.model.lector.Lector;
import mx.edu.utez.libros.repository.lector.LectorRepository;
import org.springframework.stereotype.Service;

@Service
public class LectorServicio {

    private final LectorRepository lectorRepository;

    public LectorServicio(LectorRepository lectorRepository) {
        this.lectorRepository = lectorRepository;
    }

    public Lector rehabilitarLector(Long lectorId) {
        Lector lector = lectorRepository.findById(lectorId)
                .orElseThrow(() -> new BadRequestException("El lector no existe"));

        lector.setActivo(true);
        return lectorRepository.save(lector);
    }
}