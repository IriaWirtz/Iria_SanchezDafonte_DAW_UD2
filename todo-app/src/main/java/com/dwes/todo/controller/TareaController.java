package com.dwes.todo.controller;

import com.dwes.todo.dto.TareaCrearDto;
import com.dwes.todo.exception.TareaDuplicadaException;
import com.dwes.todo.service.TareaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TareaController {

    private final TareaService service;

    public TareaController(TareaService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/tareas";
    }

    @GetMapping("/tareas")
    public String listar(Model model) {
        if (!model.containsAttribute("nuevaTarea")) {
            model.addAttribute("nuevaTarea", new TareaCrearDto());
        }
        cargarListado(model);
        return "tareas";
    }

    @PostMapping("/tareas")
    public String crear(@Valid @ModelAttribute("nuevaTarea") TareaCrearDto dto,
                        BindingResult result,
                        Model model,
                        RedirectAttributes redirect) {
        if (!result.hasErrors()) {
            try {
                service.crear(dto);
                redirect.addFlashAttribute("mensaje", "Tarea creada");
                return "redirect:/tareas";
            } catch (TareaDuplicadaException ex) {
                result.rejectValue("titulo", "duplicada", ex.getMessage());
            }
        }
        cargarListado(model);
        return "tareas";
    }

    @PostMapping("/tareas/{id}/completar")
    public String completar(@PathVariable Long id) {
        service.alternarCompletada(id);
        return "redirect:/tareas";
    }

    @PostMapping("/tareas/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        service.eliminar(id);
        redirect.addFlashAttribute("mensaje", "Tarea eliminada");
        return "redirect:/tareas";
    }

    private void cargarListado(Model model) {
        model.addAttribute("tareas", service.listar());
        model.addAttribute("pendientes", service.contarPendientes());
    }
}
