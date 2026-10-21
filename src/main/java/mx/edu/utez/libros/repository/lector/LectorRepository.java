package mx.edu.utez.libros.repository.lector;

import jakarta.validation.constraints.NotBlank;
import mx.edu.utez.libros.model.lector.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {
    Optional<Object> findByUsername(@NotBlank(message = "El nombre es obligatorio") String nombre);
}
