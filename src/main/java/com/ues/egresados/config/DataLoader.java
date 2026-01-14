package com.ues.egresados.config;

import com.ues.egresados.model.Egresado;
import com.ues.egresados.repository.EgresadoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(EgresadoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Egresado e1 = new Egresado();
                e1.setMatricula("13170017");
                e1.setNombre("Juan");
                e1.setApellidoPaterno("Pérez");
                e1.setApellidoMaterno("Hernandez");
                e1.setCarrera("Ingeniería en Sistemas Computacionales");
                e1.setGeneracion("2019-2024");
                e1.setEstatus("Titulado");
                e1.setDomicilio("Calle Falsa 123, Toluca");
                e1.setGenero("Masculino");
                e1.setTelefono("7221234567");
                e1.setCorreo("juan@correo.com");

                repository.save(e1);

                Egresado e2 = new Egresado();
                e2.setMatricula("13200020");
                e2.setNombre("Maria");
                e2.setApellidoPaterno("García");
                e2.setApellidoMaterno("Rodriguez");
                e2.setCarrera("Licenciatura en Contaduría");
                e2.setGeneracion("2020-2025");
                e2.setEstatus("Egresado");
                e2.setDomicilio("Av. Central 45, San José");
                e2.setGenero("Femenino");
                e2.setTelefono("7129876543");
                e2.setCorreo("maria@correo.com");

                repository.save(e2);
            }
            System.out.println("Base de datos lista. Total: " + repository.count());
        };
    }
}
