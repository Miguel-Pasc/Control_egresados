package com.ues.egresados.service;

import com.ues.egresados.model.Egresado;
import com.ues.egresados.repository.EgresadoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EgresadoService {

    private final EgresadoRepository egresadoRepository;

    public EgresadoService(EgresadoRepository egresadoRepository) {
        this.egresadoRepository = egresadoRepository;
    }

    public List<Egresado> listarTodos() {
        return egresadoRepository.findAll();
    }

    public Egresado guardar(Egresado egresado) {
        try {
            return egresadoRepository.save(egresado);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("MATRICULA")) {
                throw new RuntimeException("Esa matrícula ya pertenece a otro egresado.");
            } else if (e.getMessage().contains("CORREO")) {
                throw new RuntimeException("Ese correo electrónico ya está registrado.");
            }
            throw new RuntimeException("Error de duplicidad en los datos.");
        }
    }

    public Optional<Egresado> buscarPorId(Long id) {
        return egresadoRepository.findById(id);
    }

    public void eliminar(Long id) {
        egresadoRepository.deleteById(id);
    }

    public List<Egresado> buscarConFiltros(String q, String carrera, String generacion) {
        String queryLimpia = (q != null && !q.trim().isEmpty()) ? q : null;
        String carreraLimpia = (carrera != null && !carrera.trim().isEmpty()) ? carrera : null;
        String genLimpia = (generacion != null && !generacion.trim().isEmpty()) ? generacion : null;

        if (queryLimpia == null && carreraLimpia == null && genLimpia == null) {
            return egresadoRepository.findAll();
        }

        return egresadoRepository.buscarFiltrado(queryLimpia, carreraLimpia, genLimpia);
    }

    public List<String> obtenerCarreras() {
        return egresadoRepository.findDistinctCarreras();
    }

    public List<String> obtenerGeneraciones() {
        return egresadoRepository.findDistinctGeneraciones();
    }
}