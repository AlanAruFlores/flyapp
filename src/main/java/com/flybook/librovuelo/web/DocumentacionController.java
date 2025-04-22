package com.flybook.librovuelo.web;

import com.flybook.librovuelo.model.AdjuntoDocumentacion;
import com.flybook.librovuelo.model.Documentacion;
import com.flybook.librovuelo.model.TipoDocumentacion;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.service.AdjuntoDocumentacionService;
import com.flybook.librovuelo.service.DocumentacionService;
import com.flybook.librovuelo.service.FileStorageService;
import com.flybook.librovuelo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class DocumentacionController {

    @Autowired
    private DocumentacionService documentacionService;

    @Autowired
    private AdjuntoDocumentacionService adjuntoDocumentacionService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserService userService;

    @GetMapping(value = {"tripulante/midocumentacion","lider/documentaciones"})
    public String mostrarDocumentaciones(Model model, @RequestParam Map<String, Object> params) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());
        List<Documentacion> documentaciones = null;

        if (params.get("id") == null) {
            documentaciones = this.documentacionService.buscarDocumentacionDeUnUsuario(user);
        } else {
            Long idABuscar = Long.parseLong(params.get("id").toString());
            User tripulante = this.userService.findUserById(idABuscar);
            model.addAttribute("tripulante", tripulante);
            model.addAttribute("idABuscar", idABuscar);
            documentaciones = this.documentacionService.buscarDocumentacionDeUnUsuario(user);

        }
        model.addAttribute("user", user);
        model.addAttribute("documentaciones", documentaciones);
        return "documentacion";
    }

    @GetMapping("/documentacion/nueva-documentacion")
    public String nuevaDocumentacion(Model model){
        Documentacion nuevaDocumentacion = new Documentacion();
//        AdjuntoDocumentacion adjunto = new AdjuntoDocumentacion();

        List<TipoDocumentacion> tipoDocumentacion = List.of(TipoDocumentacion.values());

        nuevaDocumentacion.setFechaDeCreacion(LocalDate.now());
        nuevaDocumentacion.setFechaDeVencimiento(LocalDate.now());

        model.addAttribute("documentacion", nuevaDocumentacion);
        model.addAttribute("tipoDocumentacion" , tipoDocumentacion);
//        model.addAttribute("adjunto", adjunto);

        return "formulario-documentacion";
    }

    @PostMapping("/documentacion/registrar-documentacion")
    public String registrarDocumentacion(
            @ModelAttribute("documentacion") Documentacion documentacion,
            Model model,
            RedirectAttributes redirectAttributes){
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        documentacion.setUser(user);

        try {
            documentacionService.updateDocumentacion(documentacion);
        } catch (Exception e){
            model.addAttribute(e.getMessage());
            return "form-documentacion";
        }
        redirectAttributes.addFlashAttribute("mensajeDocumentacion", true);
        return "redirect:/documentacion/editar/"+documentacion.getId();
    }

    @GetMapping("/documentacion/editar/{id}")
    public String actualizarDocumentacion(@PathVariable("id") Long id,
                                          Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        Documentacion documentacion = documentacionService.findDocumentacionById(id);
        List<AdjuntoDocumentacion> adj = adjuntoDocumentacionService.findAllByDocumentacion(documentacion);

        List<TipoDocumentacion> tipoDocumentacion = List.of(TipoDocumentacion.values());

        model.addAttribute("documentacion", documentacion);
        model.addAttribute("tipoDocumentacion" , tipoDocumentacion);
        model.addAttribute("adjuntos", adj);

        //    model.addAttribute("adjuntoNuevo", adj);
        model.addAttribute("user", user);
        return "formulario-documentacion";
    }

    @PostMapping("/documentacion/editar")
    public String actualizarDocumentacion(@ModelAttribute("documentacionActualizada") Documentacion documentacion,
                                          Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());
        documentacion.setUser(user);

            try {
                this.documentacionService.updateDocumentacion(documentacion);
       //     documentacionService.save(documentacion);
         //   fileStorageService.updateStoreFile(file, documentacion);
         //   fileStorageService.storeFileDocumentacion(file,"/documentacion",user);
            } catch (Exception e) {
                System.out.println("Exception: "+e.getMessage());
                model.addAttribute(e.getMessage());
                return "redirect:/documentacion/editar/"+documentacion.getId();
            }
            /*
            try {

            } catch (Exception e) {
                model.addAttribute(e.getMessage());
                return "actualizar-documentacion";
            }*/
        return "redirect:/tripulante/midocumentacion";
    }

    @PostMapping("/documentacion/cargar")
    public String subirArchivo(@RequestParam(name = "documentacion_id") Long id,
                                          Model model,
                                          @RequestParam(name = "file", required = false) MultipartFile file){
        Documentacion documentacion = documentacionService.findDocumentacionById(id);
        try {
            this.documentacionService.updateDocumentacion(documentacion,file);
        } catch (Exception e) {
            System.out.println("Exception: "+e.getMessage());
            model.addAttribute(e.getMessage());
        }

        return "redirect:/documentacion/editar/"+documentacion.getId();
    }

    @GetMapping("/documentacion/borrar-documentacion/{id}")
    public String borrarDocumentacion(@PathVariable("id") Long id, Model model) {
        Documentacion documentacionAEliminar = documentacionService.findDocumentacionById(id);
        this.documentacionService.delete(documentacionAEliminar);
        return "redirect:/tripulante/midocumentacion";
    }

    @GetMapping("/documentacion/ver/{id}")
    public String verDocumentacion(@PathVariable("id") Long id, Model model) {
        Documentacion documentacion = documentacionService.findDocumentacionById(id);

        List<AdjuntoDocumentacion> adj = this.adjuntoDocumentacionService.findAllByDocumentacion(documentacion);
        model.addAttribute("adj", adj);
        model.addAttribute("documentacion", documentacion);
        model.addAttribute("title", "Ver mi documentación");
        return "ver-documentacion";
    }

    @GetMapping("/documentacion/desactivar-notificacion/{id}")
    public String desactivarDocumentacion(@PathVariable("id") Long id , Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());
        Documentacion documentacion = documentacionService.getById(id);

        if(!user.getId().equals(documentacion.getUser().getId()))
            return "redirect:/";

        documentacion.setNotificacionActivada(false);
        this.documentacionService.updateDocumentacion(documentacion);
        return "redirect:/documentacion/editar/"+id;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity descargar(@PathVariable Long id){
        return fileStorageService.download(id);
    }

    @GetMapping(value = "/veradjunto/{id}")
    public void verAdjunto(@PathVariable Long id, HttpServletResponse response) {
        fileStorageService.showAttachment(id, response);
    }

    @GetMapping("/eliminar_adjunto/{id}/{idAdjunto}")
    public String eliminarAdjunto(@PathVariable Long id,@PathVariable Long idAdjunto){
        AdjuntoDocumentacion adjuntoDocumentacion =  this.adjuntoDocumentacionService.findById(idAdjunto).get();
        Documentacion documentacion = this.documentacionService.getById(id);
        this.adjuntoDocumentacionService.delete(adjuntoDocumentacion, documentacion);
        return "redirect:/documentacion/editar/"+id;
    }

//    @PostMapping("/agregar_adjunto/{id}")
//    public String agregarAdjunto(@PathVariable Long id, @RequestParam(name = "file", required = false) MultipartFile file){
//        Documentacion documentacion =  this.documentacionService.findById(id).get();
//        this.fileStorageService.storeFileDocumentacion(file, documentacion);
//        return "redirect:/documentacion/editar/"+id;
//    }
}

