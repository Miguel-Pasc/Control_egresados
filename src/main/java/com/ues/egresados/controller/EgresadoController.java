package com.ues.egresados.controller;

import com.ues.egresados.model.Egresado;
import com.ues.egresados.service.EgresadoService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/egresados")
public class EgresadoController {

    private final EgresadoService egresadoService;

    public EgresadoController(EgresadoService egresadoService) {
        this.egresadoService = egresadoService;
    }

    @GetMapping
    public String listarEgresados(@RequestParam(required = false) String q,
                                  @RequestParam(required = false) String carrera,
                                  @RequestParam(required = false) String generacion,
                                  Model model, Authentication auth) {

        String queryProcesada = q;

        if (auth != null && auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            if (q != null && q.matches(".*\\d.*")) {
                queryProcesada = "";
                model.addAttribute("info", "La búsqueda por matrícula está reservada para administradores.");
            }
        }

        model.addAttribute("egresados", egresadoService.buscarConFiltros(queryProcesada, carrera, generacion));

        List<Egresado> egresados = egresadoService.buscarConFiltros(q, carrera, generacion);

        model.addAttribute("egresados", egresados);
        model.addAttribute("listaCarreras", egresadoService.obtenerCarreras());
        model.addAttribute("listaGeneraciones", egresadoService.obtenerGeneraciones());

        model.addAttribute("q", q);
        model.addAttribute("carreraSel", carrera);
        model.addAttribute("genSel", generacion);

        model.addAttribute("titulo", "Lista de egresados");
        model.addAttribute("contenido", "egresados/listar");

        return "layout";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("titulo", "Registrar egresado");
        model.addAttribute("egresado", new Egresado());
        model.addAttribute("contenido", "egresados/formulario");
        return "layout";
    }

    @PostMapping("/guardar")
    public String guardarEgresado(@Valid @ModelAttribute("egresado") Egresado egresado,
                                  BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Error en el registro");
            model.addAttribute("contenido", "egresados/formulario");
            return "layout";
        }

        try {
            egresadoService.guardar(egresado);
            flash.addFlashAttribute("mensajeExito", "Egresado guardado con éxito");
            return "redirect:/egresados";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("titulo", "Corregir datos");
            model.addAttribute("contenido", "egresados/formulario");
            return "layout";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarEgresado(@PathVariable Long id, Model model) {
        Egresado egresado = egresadoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("titulo", "Editar egresado");
        model.addAttribute("egresado", egresado);
        model.addAttribute("contenido", "egresados/formulario");
        return "layout";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEgresado(@PathVariable Long id, RedirectAttributes flash) {
        egresadoService.eliminar(id);
        flash.addFlashAttribute("mensajeExito", "Egresado eliminado correctamente");
        return "redirect:/egresados";
    }
}