package mx.edu.utez.libros.controller.prestamo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitarPrestamoDto {
    @NotNull(message = "El id del lector es obligatorio")
    private Long lector_id;

    @NotNull(message = "El id del libro es obligatorio")
    private Long libro_id;
}