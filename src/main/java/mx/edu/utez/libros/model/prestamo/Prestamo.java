package mx.edu.utez.libros.model.prestamo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.libros.model.lector.Lector;
import mx.edu.utez.libros.model.libro.Libro;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lector_id")
    private Lector lector;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private LocalDate fechaPrestamo;

    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private LocalDate fechaDevolucion;

    @Enumerated(EnumType.STRING)
    private Estado estado;




}
