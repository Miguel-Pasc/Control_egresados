package com.ues.egresados.repository;

import com.ues.egresados.model.Egresado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EgresadoRepository extends JpaRepository<Egresado, Long> {

    Optional<Egresado> findByMatricula(String matricula);

    Optional<Egresado> findByCorreo(String correo);

    List<Egresado> findByCarrera(String carrera);

    boolean existsByMatricula(String matricula);

    boolean existsByCorreo(String correo);

    @Query("SELECT e FROM Egresado e WHERE " +
            "(:query IS NULL OR " +
            "LOWER(e.nombre) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(e.apellidoPaterno) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(e.apellidoMaterno) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "e.matricula LIKE CONCAT('%', :query, '%')) AND " +
            "(:carrera IS NULL OR e.carrera = :carrera) AND " +
            "(:generacion IS NULL OR e.generacion = :generacion)")
    List<Egresado> buscarFiltrado(@Param("query") String query,
                                  @Param("carrera") String carrera,
                                  @Param("generacion") String generacion);

    @Query("SELECT DISTINCT e.carrera FROM Egresado e ORDER BY e.carrera ASC")
    List<String> findDistinctCarreras();

    @Query("SELECT DISTINCT e.generacion FROM Egresado e ORDER BY e.generacion DESC")
    List<String> findDistinctGeneraciones();
}