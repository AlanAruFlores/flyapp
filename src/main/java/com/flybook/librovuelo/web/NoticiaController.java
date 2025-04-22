package com.flybook.librovuelo.web;

import com.flybook.librovuelo.model.Noticia;
import com.flybook.librovuelo.model.Role;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/noticia/{id}")
    public String verNoticia(@PathVariable("id") Long id, Model model) {
        Noticia noticia = noticiaService.getById(id);
        model.addAttribute("noticia", noticia);
        return "noticia";
    }

    @GetMapping("/admin/crear-noticia")
    public String irARegistrarNoticia(Model model) {
        Noticia noticia = new Noticia();
        model.addAttribute("newNoticia", noticia);
        return "noticia-registration";
    }

    @PostMapping("/admin/registrar-noticia")
    public String registrarNoticia(@ModelAttribute("newNoticia") Noticia noticia,
                                   @RequestParam("descripcion") String descripcion,
                                   BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()){
            return "redirect:/admin/crear-noticia";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());
        LocalDate fecha = LocalDate.now();
        noticia.setAutor(user);
        noticia.setFecha(fecha);
        noticia.setDescripcion(descripcion);

        noticiaService.save(noticia);

        return "redirect:/home";
    }

    @GetMapping("/admin/noticia/eliminar/{id}")
    public String deleteNoticia(@PathVariable("id") Long id, Model model){
        noticiaService.deleteById(id);
        return "redirect:/admin/mostrar-noticias" ;
    }

    @GetMapping("/admin/noticia/editar/{id}")
    public String irAEditarNoticia(@PathVariable("id") Long id, Model model){
        Noticia noticia = noticiaService.getById(id);
        model.addAttribute("noticiaUpdate", noticia);
        return "noticia-update" ;
    }

    @PostMapping("/admin/noticia/update")
    public String editarNoticia(@ModelAttribute("noticiaUpdate") Noticia noticia, Model model){
        noticiaService.save(noticia);
        return "redirect:/admin/mostrar-noticias" ;
    }

    @GetMapping(value = {"/admin/mostrar-noticias", "/lider/mostrar-noticias"})
    public String mostrarNoticias(@RequestParam Map<String, Object> params, Model model) {
        // Selects
        List<String> cantidadDeRegitrosPorPaginas = Arrays.asList( "10","20", "50", "100", "Todos");
        List<String> sortOrientations = Arrays.asList("Desc", "Asc");
        // Fin selects

        //Paginacion

        int recordsQuantity;

        if(params.get("recordsQuantity") != null && params.get("recordsQuantity").toString().equals("Todos")){
            // TODO: Los ejemplos que encontre sugieren usar el valor máximo de int para obtener todos los registros. El tamaño tiene que estar presente para la construcción del objeto PageRequest.
            recordsQuantity = Integer.MAX_VALUE;
        }else{
            recordsQuantity = params.get("recordsQuantity") != null ? Integer.parseInt(params.get("recordsQuantity").toString()) : Integer.parseInt(cantidadDeRegitrosPorPaginas.get(0));
        }

        int page = params.get("page") != null ? (Integer.parseInt(params.get("page").toString()) - 1) : 0;
        String sortField = params.get("sortField") != null ? params.get("sortField").toString() : "fecha";
        String orientation = params.get("sortOrientation") != null ? params.get("sortOrientation").toString() : "Desc";

        Sort sort;
        sort = orientation.equals("Desc") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();

        PageRequest pageRequest = PageRequest.of(page, recordsQuantity,sort);

        //Fin Paginacion

        Page<Noticia> noticiasPage;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());
        Role admin = this.roleService.obtenerRolPorNombre("ROLE_ADMINISTRADOR");

        if(user.getRoles().contains(admin)){
            noticiasPage = noticiaService.findAll(pageRequest);
        } else {
            noticiasPage = noticiaService.findAllByAutor_Id(pageRequest, user.getId());
        }

        int totalPage = noticiasPage.getTotalPages();
        if(totalPage > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }
        List<Noticia> noticias = noticiasPage.getContent();

        model.addAttribute("current", page + 1);
        model.addAttribute("next", page + 2);
        model.addAttribute("prev", page);
        model.addAttribute("last", totalPage);
        model.addAttribute("cantidadDeRegitrosPorPaginas", cantidadDeRegitrosPorPaginas);
        model.addAttribute("sortOrientations", sortOrientations);
        model.addAttribute("recordsQuantity", (recordsQuantity == Integer.MAX_VALUE ? "Todos" : recordsQuantity));
        model.addAttribute("sortField", sortField);
        model.addAttribute("orientation", orientation);

        model.addAttribute("noticias", noticias);

        return "mostrar-noticias";
    }

}
