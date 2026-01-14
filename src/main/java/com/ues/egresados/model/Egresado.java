package com.ues.egresados.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "egresados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Egresado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La matrícula es obligatoria")
    @Pattern(regexp = "\\d{8}", message = "La matrícula debe ser de 8 números")
    @Column(unique = true, nullable = false, length = 8)
    private String matricula;

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo debe contener letras")
    private String nombre;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El apellido solo debe contener letras")
    private String apellidoPaterno;

    @NotBlank(message = "El apellido materno es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$", message = "El apellido solo debe contener letras")
    private String apellidoMaterno;

    @NotBlank(message = "La carrera es obligatoria")
    private String carrera;

    @NotBlank(message = "La generación es obligatoria")
    private String generacion;

    @NotBlank(message = "El estatus es obligatorio")
    private String estatus;

    @NotBlank(message = "El domicilio es obligatorio")
    private String domicilio;

    @NotBlank(message = "El genero es obligatorio")
    private String genero;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\d{10}", message = "El teléfono debe ser de 10 números")
    private String telefono;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo válido")
    @Column(unique = true)
    private String correo;

    public String getNombreCompleto() {
        return nombre + " " + apellidoPaterno + (apellidoMaterno != null ? " " + apellidoMaterno : "");
    }
}
