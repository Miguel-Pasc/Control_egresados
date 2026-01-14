package com.ues.egresados.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginasController {

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("titulo", "Bienvenido - Seguimiento de Egresados UES");
        model.addAttribute("contenido", "inicio");
        return "layout";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("titulo", "Acceso al Sistema");
        model.addAttribute("contenido", "login");
        return "layout";
    }

    @GetMapping("/acerca")
    public String acercaDe(Model model) {
        model.addAttribute("titulo", "Acerca de nosotros");
        model.addAttribute("contenido", "acerca");
        return "layout";
    }
}