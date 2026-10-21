package mx.edu.utez.libros.service.prestamo;

import mx.edu.utez.libros.controller.prestamo.dto.SolicitarPrestamoDto;
import mx.edu.utez.libros.exception.BadRequestException;
import mx.edu.utez.libros.model.lector.Lector;
import mx.edu.utez.libros.model.libro.Libro;
import mx.edu.utez.libros.model.prestamo.Estado;
import mx.edu.utez.libros.model.prestamo.Prestamo;
import mx.edu.utez.libros.repository.lector.LectorRepository;
import mx.edu.utez.libros.repository.libro.LibroRepository;
import mx.edu.utez.libros.repository.prestamo.PrestamoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class PrestamoServicio {


    private final PrestamoRepository prestamoRepository;
    private final LectorRepository lectorRepository;
    private final LibroRepository libroRepository;

    public PrestamoServicio(PrestamoRepository prestamoRepository, LectorRepository lectorRepository, LibroRepository libroRepository) {
        this.prestamoRepository = prestamoRepository;
        this.lectorRepository = lectorRepository;
        this.libroRepository = libroRepository;
    }


    @Transactional
    public Prestamo solicitarPrestamo(SolicitarPrestamoDto payload) {

        Lector lector = lectorRepository.findById(payload.getLector_id())
                .orElseThrow(() -> new BadRequestException("El lector no existe"));

        if (!lector.isActivo()) {
            throw new BadRequestException("El lector no esta activo");
        }

        Libro libro = libroRepository.findById(payload.getLibro_id())
                .orElseThrow(() -> new BadRequestException("El libro no existe"));

        if (libro.getEjemplaresDisponibles() < 1) {
            throw new BadRequestException("No hay ejemplares disponibles de este libro");
        }

        int prestamosActivos = prestamoRepository.countByLectorIdAndEstadoIn(
                lector.getId(), Arrays.asList(Estado.PRESTADO, Estado.VENCIDO));

        int limite = lector.isMembresiaPremium() ? 5 : 3;

        if (prestamosActivos >= limite) {
            throw new BadRequestException("El lector ha alcanzado su límite de préstamos activos");
        }

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setLector(lector);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucion(LocalDate.now().plusDays(14));
        prestamo.setEstado(Estado.PRESTADO);

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo devolverLibro(Long prestamoId) {


        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new BadRequestException("El prestamo no existe"));

        if (prestamo.getEstado() == Estado.DEVUELTO) {
            throw new BadRequestException("El prestamo ya se encuentra en estado DEVUELTO");
        }

        prestamo.setEstado(Estado.DEVUELTO);

        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        libroRepository.save(libro);


        if (LocalDate.now().isAfter(prestamo.getFechaDevolucion())) {
            Lector lector = prestamo.getLector();
            lector.setActivo(false);
            lectorRepository.save(lector);

        }

        return prestamoRepository.save(prestamo);
    }

    public Prestamo renovarPrestamo(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new BadRequestException("El prestamo no existe"));

        if (prestamo.getEstado() == Estado.VENCIDO || prestamo.getEstado() == Estado.DEVUELTO) {
            throw new BadRequestException("No se puede renovar un prestamo vencido o devuelto");
        }

        prestamo.setFechaDevolucion(prestamo.getFechaDevolucion().plusDays(7));
        return prestamoRepository.save(prestamo);
    }

    public List<Prestamo> listarPrestamosActivos(Long lectorId) {
        if (!lectorRepository.existsById(lectorId)) {
            throw new BadRequestException("El lector no existe");
        }
        return prestamoRepository.findByLectorIdAndEstadoIn(lectorId, Arrays.asList(Estado.PRESTADO, Estado.VENCIDO));
    }
}