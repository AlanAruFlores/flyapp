package com.flybook.librovuelo.web;

import com.flybook.librovuelo.model.CicloVacaciones;
import com.flybook.librovuelo.model.Generacion;
import com.flybook.librovuelo.service.CicloVacacionesService;
import com.flybook.librovuelo.service.GeneracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller

public class CicloVacacionesController {

    @Autowired
    private CicloVacacionesService cicloVacacionesService;
    @Autowired
    private GeneracionService generacionService;

    @GetMapping({"/lider/mostrarciclosvacaciones"})
    public String mostrarCiclosVacaciones(@RequestParam Map<String, Object>  params,Model model){


        // Selects
        List<String> cantidadDeRegitrosPorPaginas = Arrays.asList( "10","20", "50", "100", "Todos");
        Map<String, String> sortFields = this.getSortFields();
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
        String sortField = params.get("sortField") != null ? params.get("sortField").toString() : "numeroDeCiclo";
        String orientation = params.get("sortOrientation") != null ? params.get("sortOrientation").toString() : "Desc";

        Sort sort;
        sort = orientation.equals("Desc") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();

        PageRequest pageRequest = PageRequest.of(page, recordsQuantity,sort);

        //Fin Pagiacion


        Page<CicloVacaciones> cicloVacacionesPage = this.cicloVacacionesService.findAll(pageRequest);

        int totalPage = cicloVacacionesPage.getTotalPages();
        if(totalPage > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }
        List<CicloVacaciones> ciclo=cicloVacacionesPage.getContent();

        model.addAttribute("current", page + 1);
        model.addAttribute("next", page + 2);
        model.addAttribute("prev", page);
        model.addAttribute("last", totalPage);
        model.addAttribute("cantidadDeRegitrosPorPaginas", cantidadDeRegitrosPorPaginas);
        model.addAttribute("sortFields", sortFields);
        model.addAttribute("sortOrientations", sortOrientations);
        model.addAttribute("recordsQuantity", (recordsQuantity == Integer.MAX_VALUE ? "Todos" : recordsQuantity));
        model.addAttribute("sortField", sortField);
        model.addAttribute("orientation", orientation);

        model.addAttribute("cicloVacaciones", ciclo);


        return "mostrarCicloVacaciones";

    }

    private Map<String,String> getSortFields(){
        Map<String, String> sortFields = new LinkedHashMap<String,String>();
        sortFields.put("numeroDeCiclo","Numero de ciclo");
        sortFields.put("comienzoCiclo","Comienzo de Ciclo");
        sortFields.put("finalizacionCiclo","Finalizacion de Ciclo");
        sortFields.put("generacion","Generacion");
        return sortFields;
    }


    @GetMapping({"/lider/ciclovacaciones/iraregistrar"})
    public String irAcrearCicloVacaciones(Model model){
        CicloVacaciones cicloVacaciones= new CicloVacaciones();
        cicloVacaciones.setComienzoCiclo(LocalDate.now());
        cicloVacaciones.setFinalizacionCiclo(LocalDate.now());

        List<Generacion> generaciones =this.generacionService.findAll();
        model.addAttribute("generaciones",generaciones);
        model.addAttribute("cicloVacaciones",cicloVacaciones);
        model.addAttribute("action", "/lider/ciclovacaciones/registrar");
        model.addAttribute("button","Guardar");
        model.addAttribute("title", "Registrar ciclo");
        return "ciclovacaciones-form" ;
    }
    @PostMapping({"/lider/ciclovacaciones/registrar"})
    public  String guardarCicloVacaciones(@ModelAttribute("cicloVacaciones") CicloVacaciones cicloVacaciones ,  Model model){
        List<Generacion> generaciones =this.generacionService.findAll();
        try {
            this.cicloVacacionesService.save(cicloVacaciones);
            model.addAttribute("generaciones",generaciones);
        } catch (Exception e) {

            model.addAttribute("generaciones",generaciones);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("cicloVacaciones",cicloVacaciones);
            model.addAttribute("action", "/lider/ciclovacaciones/registrar");
            model.addAttribute("button","Guardar");
            model.addAttribute("title", "Registrar ciclo");
            return "ciclovacaciones-form" ;
        }




        return "redirect:/lider/mostrarciclosvacaciones" ;
    }

     @GetMapping("/lider/ciclovacaciones/eliminar/{id}")
    public String deleteCicloVacaciones(@PathVariable("id") Long id,Model model){
            this.cicloVacacionesService.deleteId(id);
        return "redirect:/lider/mostrarciclosvacaciones" ;
    }

    @GetMapping("/lider/ciclovacaciones/editar/{id}")
    public String irAEditarCicloVacaciones(@PathVariable("id") Long id,Model model){

        CicloVacaciones cicloVacaciones=  this.cicloVacacionesService.getById(id);


        List<Generacion> generaciones =this.generacionService.findAll();
        model.addAttribute("generaciones",generaciones);
        model.addAttribute("cicloVacaciones",cicloVacaciones);
        model.addAttribute("action", "/lider/ciclovacaciones/registrar");
        model.addAttribute("title", "Modificar ciclo");;
        model.addAttribute("button","Modificar");
        return "ciclovacaciones-form" ;
    }
    @PostMapping("/lider/ciclovacaciones/editar")
    public String editar(@ModelAttribute("cicloVacaciones") CicloVacaciones cicloVacaciones, Model model){


        try {
            this.cicloVacacionesService.save(cicloVacaciones);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            List<Generacion> generaciones =this.generacionService.findAll();
            model.addAttribute("generaciones",generaciones);
            model.addAttribute("cicloVacaciones",cicloVacaciones);
            model.addAttribute("action", "/lider/ciclovacaciones/registrar");
            model.addAttribute("title", "Modificar ciclo");;
            model.addAttribute("button","Modificar");
            return "ciclovacaciones-form" ;
        }

        return "redirect:/lider/mostrarciclosvacaciones" ;
    }

}
