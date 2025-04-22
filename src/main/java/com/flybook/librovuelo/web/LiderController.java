package com.flybook.librovuelo.web;

import com.flybook.librovuelo.model.Ausencia;
import com.flybook.librovuelo.model.CicloVacaciones;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Controller
public class LiderController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DocumentacionService documentacionService;

    @Autowired
    private GeneracionService generacionService;

    @Autowired
    private AusenciaService ausenciaService;

    @Autowired
    private ExcelService excelService;

    @GetMapping({"/lider/mistripulantes"})
    public String mostrarTripulantesDelLider(@RequestParam Map<String, Object> params, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());

        String atributoExcel = (String) params.getOrDefault("atributo", "");
        String valorExcel = (String) params.getOrDefault("valor", "");

        try {
            List<User> tripulantes = this.userService.buscarTripulantesPorLiderYParams(user, params);
            model.addAttribute("tripulantes", tripulantes);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("atributoExcel", atributoExcel);
        model.addAttribute("valorExcel", valorExcel);

        return "mistripulantes";
    }

    @GetMapping({"/lider/mistripulantes/exportarTodosAExcel"})
    public String exportarTodosAExcel(@RequestParam Map<String, Object> params, Model model, HttpServletResponse response) throws IOException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        String atributoExcel = (String) params.getOrDefault("atributo", "");
        String valorExcel = (String) params.getOrDefault("valor", "");

        try {
            List<User> tripulantes = this.userService.buscarTripulantesPorLiderYParams(user, params);
            this.excelService.exportarAExcelLaInformacionDeTodos(tripulantes, response);

            model.addAttribute("tripulantes", tripulantes);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/lider/mistripulantes?atributo=" + atributoExcel + "&valor=" + valorExcel;
    }

    @GetMapping("/lider/mistripulantes/ver")
    public String verDetallesDeTripulante(@RequestParam("id")Long id, Model model) {
        User tripulante = this.userService.findUserById(id);
        model.addAttribute("tripulante", tripulante);
        return "verDetalleTripulante";
    }

    @GetMapping("/lider/mistripulantes/exportar")
    public void exportTripulanteToExcel(@RequestParam("id") Long tripulanteId, HttpServletResponse response) throws IOException {
        User tripulante = userService.findUserById(tripulanteId);
        this.excelService.exportarInformacionDelTripulanteAExcel(tripulante,response);
    }

    @GetMapping({"/lider/vacacionesaprobadas", "/tripulante/misvacacionesaprobadas"})
    public String vacacionesAprobadas(Model model, @RequestParam Map<String, Object> params) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        Map<User, Map<CicloVacaciones, List<Ausencia>>> mapaVacacionesDeTodosLosUsuarios = null;

        if (params.get("id") == null) {
            mapaVacacionesDeTodosLosUsuarios = this.ausenciaService.obtenerVacacionesDeUnUsuarioAgrupadasCiclos(user);
            model.addAttribute("tripulante", user);
        } else {
            Long idABuscar = Long.parseLong(params.get("id").toString());
            User tripulante = this.userService.findUserById(idABuscar);
            model.addAttribute("tripulante", tripulante);
            model.addAttribute("idABuscar", idABuscar);
            mapaVacacionesDeTodosLosUsuarios = this.ausenciaService.obtenerVacacionesDeUnUsuarioAgrupadasCiclos(tripulante);
        }

        model.addAttribute("mapaVacacionesDeTodosLosUsuarios", mapaVacacionesDeTodosLosUsuarios);
        return "vacaciones-aprobadas";
    }
}