package mx.edu.utez.libros.model.lector;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lectores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Lector {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    private boolean activo;
    private boolean membresiaPremium;
}

