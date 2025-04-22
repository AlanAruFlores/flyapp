package com.flybook.librovuelo.web;

import com.flybook.librovuelo.dto.DatosNotificacionUsuario;
import com.flybook.librovuelo.model.*;
import com.flybook.librovuelo.service.*;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.VerticalPositionMark;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/tripulante")
public class LibroVueloController extends PdfPageEventHelper {

    @Autowired
    private VueloRealizadoService vueloRealizadoService;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private AvionService avionService;

    @Autowired
    private AeropuertoService aeropuertoService;

    @Autowired
    private CalculoDeHorasService calculoDeHorasService;
    @Autowired
    private FoliadoService foliadoService;

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping({"/programacion"})
    public String programacion(Model model) {
        return "programacion";
    }

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.0");

    @GetMapping("/librovuelo")
    public String mostrarLibroVuelo(@RequestParam Map<String, Object> params, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());
        // Selects
        List<String> cantidadDeRegitrosPorPaginas = Arrays.asList("10", "20", "50", "100", "Todos");
        Map<String, String> sortFields = this.getSortFields();
        List<String> sortOrientations = Arrays.asList("Desc", "Asc");
        // Fin selects

        int recordsQuantity;

        if (params.get("recordsQuantity") != null && params.get("recordsQuantity").toString().equals("Todos")) {
            // TODO: Los ejemplos que encontre sugieren usar el valor máximo de int para obtener todos los registros. El tamaño tiene que estar presente para la construcción del objeto PageRequest.
            recordsQuantity = Integer.MAX_VALUE;
        } else {
            recordsQuantity = params.get("recordsQuantity") != null ? Integer.parseInt(params.get("recordsQuantity").toString()) : Integer.parseInt(cantidadDeRegitrosPorPaginas.get(0));
        }

        int page = params.get("page") != null ? (Integer.parseInt(params.get("page").toString()) - 1) : 0;
        String sortField = params.get("sortField") != null ? params.get("sortField").toString() : "fechahoraDespegue";
        String orientation = params.get("sortOrientation") != null ? params.get("sortOrientation").toString() : "Desc";

        Sort sort;
        sort = orientation.equals("Desc") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();

        PageRequest pageRequest = PageRequest.of(page, recordsQuantity, sort);

        Page<VueloRealizado> pageVueloRealizado = this.vueloRealizadoService.findAllByUser(user, pageRequest);

        int totalPage = pageVueloRealizado.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }


        Integer cantidadNotidicacionesNoLeidas=this.notificacionService.obtenerCatidadDeNotificacionesNoLeidas(user);
        model.addAttribute("cantidadNotidicacionesNoLeidas",cantidadNotidicacionesNoLeidas);
        model.addAttribute("vuelosRealizados", pageVueloRealizado.getContent());
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



        Double totalTvDiurnoIniciales = this.vueloRealizadoService.obtenerTotalDeHorasFoliadaDiurnas(user);
        Double totalTvNocturnaIniciales =  this.vueloRealizadoService.obtenerTotalDeHorasFoliadaNocturna(user) ;
        Double totalTvIniciales = this.vueloRealizadoService.obtenerTotalDeHorasFoliada(user) ;
        Integer totalAterizajesIniciales = this.vueloRealizadoService.obtenerTotalDeAterrizajeFoliada(user);
//

        Double totalTvDiurno = this.vueloRealizadoService.obtenerCantidadTotalDeHorasDiurnas(user) ;
        Double totalTvNocturno = this.vueloRealizadoService.obtenerCantidadTotalDeHorasNocturnas(user) ;
        Double totalTv = this.vueloRealizadoService.obtenerCantidadTotalDeHoras(user) ;
        Integer totalAterrizajes = this.vueloRealizadoService.obtenerCantidadTotalDeAterrizaje(user);


        model.addAttribute("user", user);
        model.addAttribute("totalTvDiurno", totalTvDiurno);
        model.addAttribute("totalTvNocturno", totalTvNocturno);
        model.addAttribute("totalTv", totalTv);
        model.addAttribute("totalAterrizajes", totalAterrizajes);
        model.addAttribute("totalTvDiurnoIniciales", totalTvDiurnoIniciales);
        model.addAttribute("totalTvNocturnaIniciales", totalTvNocturnaIniciales);
        model.addAttribute("totalTvIniciales", totalTvIniciales);
        model.addAttribute("totalAterizajesIniciales", totalAterizajesIniciales);

        //Historial de foliados

        Sort sortFoliado = Sort.by("fechaFoliado");
        List<Foliado> foliados = this.foliadoService.obtenerTodosLosFoliados(user);
        Foliado lastFoliado = foliados.stream().max(Comparator.comparing(Foliado::getFechaFoliado)).orElse(new Foliado());
        model.addAttribute("foliados", foliados);
        model.addAttribute("lastFoliado", lastFoliado);

        List <User> tripulantes = this.userService.findAll();
        model.addAttribute("tripulantes", tripulantes);

        DatosNotificacionUsuario datosNotificacionUsuario = new DatosNotificacionUsuario();
        model.addAttribute("datosNotificacionUsuario", datosNotificacionUsuario);

        return "librovuelo";
    }

    @GetMapping("/librovuelo/registrar")
    public String create(Model model) {
        VueloRealizado vueloRealizado = new VueloRealizado();
        vueloRealizado.setFechahoraDespegue(LocalDateTime.now());
        vueloRealizado.setFechahoraAterrizaje(LocalDateTime.now());
        List<Aeropuerto> aeropuertos = this.aeropuertoService.findAll();
        List<Avion> aviones = this.avionService.findAll();
        model.addAttribute("vueloRealizado", vueloRealizado);
        model.addAttribute("aeropuertos", aeropuertos);
        model.addAttribute("aviones", aviones);
        model.addAttribute("action", "/fbtripulantes/tripulante/librovuelo/registrar");
        model.addAttribute("title", "Registrar vuelo");
        return "librovuelo-form";
    }

    /**
     * Obtiene un map con los campos para ordenar la lista de vuelos
     *
     * @return Map<String, String>
     */
    private Map<String, String> getSortFields() {
        Map<String, String> sortFields = new LinkedHashMap<String, String>();
        sortFields.put("fechahoraDespegue", "Fecha y hora de despegue");
        sortFields.put("aeropuertoOrigen", "Desde aeropuerto");
        sortFields.put("aeropuertoDestino", "Hasta aeropuerto");
        sortFields.put("fechahoraAterrizaje", "Fecha y hora de aterrizaje");
        sortFields.put("finalidadDelVuelo", "Finalidad del vuelo");
//        sortFields.put("marca","Marca del avión");
//        sortFields.put("matricula","Matrícula del avión");
        sortFields.put("folioRVA", "Folio RVA");
        sortFields.put("horasDiurnas", "Horas de día");
        sortFields.put("horasNocturnas", "Horas de noche");
        sortFields.put("totalDeHoras", "Total de horas");
        sortFields.put("aterrizajes", "Aterrizajes");
        sortFields.put("instructorTcp", "Instructor del TCP");
        sortFields.put("tipoAeronave", "Tipo de aeronave");
        return sortFields;
    }

    /*@PostMapping("/librovuelo")
    public String libroVueloFilter(HttpServletRequest request){
        return "librovuelo";
    }*/

    @PostMapping("/librovuelo/registrar")

    public String create(@ModelAttribute("vueloRealizado") VueloRealizado vueloRealizado, BindingResult bindingResult,  Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());
        if (bindingResult.hasErrors()){

            // TODO: Manejar alguna variable para mostrar un mensaje de error
            return "/librovuelo/registrar";
        }
        try {

            this.vueloRealizadoService.validarHorasDeVuelosRelizados(user,vueloRealizado);
        } catch (Exception e) {


            List<Aeropuerto> aeropuertos = this.aeropuertoService.findAll();
            List<Avion> aviones = this.avionService.findAll();
            model.addAttribute("vueloRealizado", vueloRealizado);
            model.addAttribute("aeropuertos", aeropuertos);
            model.addAttribute("aviones", aviones);
           // model.addAttribute("action", "/fbtripulantes/tripulante/librovuelo/registrar");
            model.addAttribute("title", "Registrar vuelo");
            model.addAttribute("error", e.getMessage());
            return "librovuelo-form";
        }

        vueloRealizado.setUser(user);
        this.calculoDeHorasService.calcularTodasLasHorasDeUnVuelo(vueloRealizado);
        this.vueloRealizadoService.save(vueloRealizado);
        return "redirect:/tripulante/librovuelo";
    }

    @GetMapping("/librovuelo/editar/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        VueloRealizado vueloRealizado = this.vueloRealizadoService.getById(id);
        List<Aeropuerto> aeropuertos = this.aeropuertoService.findAll();
        List<Avion> aviones = this.avionService.findAll();

        model.addAttribute("vueloRealizado", vueloRealizado);
        model.addAttribute("aeropuertos", aeropuertos);
        model.addAttribute("aviones", aviones);
        model.addAttribute("action", "/fbtripulantes/tripulante/librovuelo/editar");
        model.addAttribute("title", "Editar vuelo");

        return "librovuelo-form";
    }

    @PostMapping("/librovuelo/editar")

    public String edit(@ModelAttribute("vueloRealizado") VueloRealizado vueloRealizado, BindingResult bindingResult,  Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        if (bindingResult.hasErrors()){

            // TODO: Manejar alguna variable para mostrar un mensaje de error
            return "/librovuelo/editar";
        }

        try {

            this.vueloRealizadoService.validarHorasDeVuelosRelizados(user,vueloRealizado);
        } catch(Exception e){

            List<Aeropuerto> aeropuertos = this.aeropuertoService.findAll();
            List<Avion> aviones = this.avionService.findAll();
            model.addAttribute("vueloRealizado", vueloRealizado);
            model.addAttribute("aeropuertos", aeropuertos);
            model.addAttribute("aviones", aviones);
            model.addAttribute("action", "/tripulante/librovuelo/registrar");
            model.addAttribute("title", "Registrar vuelo");
            model.addAttribute("error", e.getMessage());
            return "librovuelo-form";
        }

        vueloRealizado.setUser(user);
        this.calculoDeHorasService.calcularTodasLasHorasDeUnVuelo(vueloRealizado);
        this.vueloRealizadoService.save(vueloRealizado);

        return "redirect:/tripulante/librovuelo";
    }

    @GetMapping("/librovuelo/{id}/eliminar")
    public String delete(@PathVariable("id") Long id, Model model,RedirectAttributes redirectAttributes) {
        VueloRealizado vueloRealizado = this.vueloRealizadoService.getById(id);

try{
    this.vueloRealizadoService.delete(vueloRealizado);
}catch(Exception e){
    redirectAttributes.addFlashAttribute("error", "no se pudo eliminar el vuelo con aeropuerto origen " + vueloRealizado.getAeropuertoOrigen().getCodigo() + " y destino  " + vueloRealizado.getAeropuertoDestino().getCodigo());

}






        return "redirect:/tripulante/librovuelo";
    }

    @GetMapping("/foliados/{foliado_id}/eliminar")
    public String deleteFoliado(@PathVariable("foliado_id") Long foliado_id) {
        Optional<Foliado> foliadoOpTional = this.foliadoService.findById(foliado_id);
        if (foliadoOpTional.isPresent()) {
            Foliado foliado = foliadoOpTional.get();

            // Elimino los vuelo relacionados
            List<VueloRealizado> vuelosRelacionados = this.vueloRealizadoService.findAllByFoliado(foliado);
            vuelosRelacionados.forEach(x -> {
                x.setFoliado(null);
            });

            this.foliadoService.delete(foliadoOpTional.get());
        }

        return "redirect:/tripulante/librovuelo";
    }

    @GetMapping("/foliados/{foliado_id}/pdf")
    public void exportToPdf(@RequestParam Map<String, Object> params, @PathVariable("foliado_id") Long foliado_id, HttpServletResponse response) throws DocumentException, IOException {
        Optional<Foliado> foliadoOpTional = this.foliadoService.findById(foliado_id);

        if (foliadoOpTional.isPresent()) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = this.userService.findByUsername(authentication.getName());

            Foliado foliado = foliadoOpTional.get();

            List<VueloRealizado> vuelosRelacionados = this.vueloRealizadoService.findAllByUserAndFoliado(user, foliado);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


            // Exporta a PDF
            response.setContentType("application/pdf");
//            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//            String currentDateTime = dateFormatter.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=foliado_" + foliado.getId() + "_" + formatter.format(foliado.getFechaFoliado()) + ".pdf";
            response.setHeader(headerKey, headerValue);
            this.exportFoliadoToPdf(response, foliado, vuelosRelacionados, user, formatter);
            //       page++;
            //    } while (page <= totalPage);


        }
    }


    private void exportFoliadoToPdf(HttpServletResponse response, Foliado foliado, List<VueloRealizado> vuelosRealizados, User user, DateTimeFormatter formatter) throws DocumentException, IOException {

       // List<VueloRealizado> vuelosRealizados = this.vueloRealizadoService.findAllByUserAndFoliado(user, foliado);

        Document document = new Document(PageSize.A4.rotate());

        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Integer pagina = 1;
        Integer cantidadDeRegistrosPorPaginas = 17;
        Integer cantidadDePaginas = (vuelosRealizados.size() / cantidadDeRegistrosPorPaginas) +1;
        Double cantidadDeHorasDiurnasInicialesDePagina = foliado.getHorasDiurnasEnFoliadoPrevio();
        Double cantidadDeHorasNocturnasInicialesDePAgina = foliado.getHorasNocturnasEnFoliadoPrevio();
        Double cantidadTotalDeHorasInicialesDePAgina = cantidadDeHorasDiurnasInicialesDePagina +cantidadDeHorasNocturnasInicialesDePAgina;
        Integer cantidadDeAterrizajeInicialesDePagina = foliado.getTotalAterrizajeEnFoliadoPrevio();

        Double cantidadDeHorasDiurnasFinalDePagina = cantidadDeHorasDiurnasInicialesDePagina;
        Double cantidadDeHorasNocturnasFinalDePAgina = cantidadDeHorasNocturnasInicialesDePAgina;
        Double cantidadTotalDeHorasFinalDePAgina = cantidadTotalDeHorasInicialesDePAgina;
        Integer cantidadDeAterrizajeFinalDePagina = cantidadDeAterrizajeInicialesDePagina;

        for (int i = pagina ; i <= cantidadDePaginas; i++) {

            onStartPage(writer, document, foliado, vuelosRealizados,pagina);

            // Tabla para mostrar los resultados


            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setSize(9);
            font.setColor(Color.BLACK);
            Paragraph paragraph;



            // Filiado ID y Fecha
            Chunk glue = new Chunk(new VerticalPositionMark());
            paragraph = new Paragraph( "Cantiidad de Horas Diurnas iniciales " + (String.format("%.1f",cantidadDeHorasDiurnasInicialesDePagina)) + " Cantiidad de Horas Nocturnas iniciales " + (String.format("%.1f",cantidadDeHorasNocturnasInicialesDePAgina)) + " Cantiidad de Aterrizajes  iniciales " + cantidadDeAterrizajeInicialesDePagina, font);
            //paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
//            paragraph.add(new Chunk(glue));

             document.add(paragraph);


            PdfPTable table = new PdfPTable(15);
            ImprimirEncabezadoTabla(table);


            List<VueloRealizado> vuelosAImprimir = new  ArrayList<>();

            for (int j=(pagina - 1) * cantidadDeRegistrosPorPaginas;  j < (pagina - 1) * cantidadDeRegistrosPorPaginas+ cantidadDeRegistrosPorPaginas && j < vuelosRealizados.size();j++){

                VueloRealizado vueloAImprimi = vuelosRealizados.get(j);
                vuelosAImprimir.add(vueloAImprimi);
                cantidadDeHorasDiurnasFinalDePagina += vueloAImprimi.getHorasDiurnas();
                cantidadDeHorasNocturnasFinalDePAgina += vueloAImprimi.getHorasNocturnas();
                cantidadTotalDeHorasFinalDePAgina+=vueloAImprimi.getTotalDeHoras();
                cantidadDeAterrizajeFinalDePagina += 1 ;


            }

            writeTableData(table, vuelosAImprimir);
            document.add(table);


             cantidadDeHorasDiurnasInicialesDePagina = cantidadDeHorasDiurnasFinalDePagina;
             cantidadDeHorasNocturnasInicialesDePAgina = cantidadDeHorasNocturnasFinalDePAgina ;
             cantidadTotalDeHorasInicialesDePAgina =cantidadTotalDeHorasFinalDePAgina;
             cantidadDeAterrizajeInicialesDePagina = cantidadDeAterrizajeFinalDePagina;



            onEndPage(writer, document,cantidadDeHorasDiurnasFinalDePagina, cantidadDeHorasNocturnasFinalDePAgina,cantidadTotalDeHorasFinalDePAgina, cantidadDeAterrizajeFinalDePagina);
            pagina ++;
            document.newPage();
        }




        document.close();

    }


    private void ImprimirEncabezadoTabla(PdfPTable table) {
        table.setWidthPercentage(100f);

        table.setWidths(new float[]{2.5f, 2.5f, 1.5f, 1.5f, 2.0f, 2.0f, 2.0f, 2.0f, 1.5f, 1.5f, 2.0f, 1.5f, 2.3f, 2.2f, 2.0f});
        table.setSpacingBefore(8);

        writeTableHeader(table);
    }


    /**
     * Crea el encabezado en el reporte PDF
     *
     * @param table
     */

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.YELLOW);
        cell.setHorizontalAlignment(1);  //0=Left, 1=Centre, 2=Right
        cell.setPadding(4);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.BLACK);
        font.setSize(10);

        List<String> headerFields = this.getFoliadoHeaderFields();

        for (String hf : headerFields) {
            cell.setPhrase(new Phrase(hf, font));
            table.addCell(cell);
        }
    }

    public void onStartPage(PdfWriter writer, Document document,Foliado foliado, List<VueloRealizado> vuelosRelacionados, Integer pagina) {

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(9);
        font.setColor(Color.BLACK);
        Paragraph paragraph;

        // Filiado ID y Fecha
        Chunk glue = new Chunk(new VerticalPositionMark());
        paragraph = new Paragraph(foliado.getUser().getNombre() + " " + foliado.getUser().getApellido() + " Legajo: " + foliado.getUser().getLegajo(), font);
        //paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
        paragraph.add(new Chunk(glue));

        paragraph.add("CERTIFICADO DE COMPETENCIA: TCP ");
        paragraph.add(new Chunk(glue));

//        paragraph.add("Fecha: " + formatter.format(foliado.getFechaFoliado()));

        paragraph.add("Hoja : " + pagina);
        document.add(paragraph);



    }

    private void onEndPage(PdfWriter writer, Document document, Double cantidadDeHorasDiurnasFinalDePagina, Double cantidadDeHorasNocturnasFinalDePAgina, Double cantidadTotalDeHorasFinalDePAgina, Integer cantidadDeAterrizajeFinalDePagina) {

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.BLACK);
        font.setSize(10);

        PdfContentByte cb = writer.getDirectContent();
        document.add(Chunk.NEWLINE);
        Phrase footer = new Phrase("Totales fin de página --> " + " Horas Diurnas: " + (String.format("%.1f", cantidadDeHorasDiurnasFinalDePagina)) + " - " + " Horas Nocturnas: "  + (String.format("%.1f", cantidadDeHorasNocturnasFinalDePAgina)) + " - " + " horas Totales " + (String.format("%.1f", cantidadTotalDeHorasFinalDePAgina) + "Total de Aterizajes: " + cantidadDeAterrizajeFinalDePagina), font);
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                footer,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom() - 0, 0);

        document.add(Chunk.NEWLINE);
        Phrase firmaTitular = new Phrase("FIRMA TITULAR ", font);
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                firmaTitular,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom() - 30, 0);

    }


    /**
     * Agrega las celdas de resultado por cada fila
     *
     * @param table
     * @param vuelosRealizados
     */
    private void writeTableData(PdfPTable table, List<VueloRealizado> vuelosRealizados) {
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);
        font.setSize(8);
        PdfPCell cell = new PdfPCell();
        cell.setHorizontalAlignment(1);  //0=Left, 1=Centre, 2=Right
        cell.setPadding(5);

        for (VueloRealizado vr : vuelosRealizados) {
            cell.setPhrase(new Phrase(formatterFecha.format(vr.getFechahoraDespegue()), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(formatterHora.format(vr.getFechahoraDespegue()), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(vr.getAeropuertoOrigen().getCodigo(), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(vr.getAeropuertoDestino().getCodigo(), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(formatterHora.format(vr.getFechahoraAterrizaje()), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(vr.getFinalidadDelVuelo(), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(vr.getAvion().getMarca(), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(vr.getAvion().getMatricula(), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(vr.getFolioRVA(), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(String.valueOf(decimalFormat.format(vr.getHorasDiurnas())), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(String.valueOf(decimalFormat.format(vr.getHorasNocturnas())), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(String.valueOf(decimalFormat.format(vr.getTotalDeHoras())), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("1", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(vr.getInstructorTcp(), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(vr.getTipoAeronave(), font));
            table.addCell(cell);

        }
    }

    /**
     * Obtiene los campos para el encabezado
     *
     * @return List<String>
     */
    private List<String> getFoliadoHeaderFields() {
        List<String> headerFields = new ArrayList<>();
        headerFields.add("Fecha de despegue");
        headerFields.add("Hora de despegue");
        headerFields.add("Desde");
        headerFields.add("Hasta");
        headerFields.add("Hora de aterrizaje");
        headerFields.add("Finalidad del vuelo");
        headerFields.add("Marca");
        headerFields.add("Matrícula");
        headerFields.add("Folio RVA");
        headerFields.add("Horas de día");
        headerFields.add("Horas de noche");
        headerFields.add("Total");
        headerFields.add("Aterrizajes");
        headerFields.add("Instructor del TCP");
        headerFields.add("Tipo de aeronave");
        return headerFields;
    }

    @GetMapping("/foliados/verhorasnofoliadas")
    public String mostrarHorasNoFoliadas(@RequestParam Map<String, Object> params, Model model) {
        return "redirect:/tripulante/foliados/verhorasfoliadas";

    }


    @GetMapping("/foliados/verhorasfoliadas")
    public String mostrarHorasFoliadas(@RequestParam Map<String, Object> params, Model model) {


        //se reciben los  Parametros desde las vistas page y  fechafoliado
        //tripulante/mostrarhorasfoliada?page=3&fechafoliado=fechafoliado

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());
        int cantidadDeRegitrosPorPaginas = 20;


        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
        Sort sort = Sort.by("fechahoraDespegue");
        PageRequest pageRequest = PageRequest.of(page, cantidadDeRegitrosPorPaginas, sort);

        Double totalTvDiurnoIniciales = 0.0;
        Double totalTvNocturnaIniciales = 0.0;
        Double totalTvIniciales = 0.0;
        Integer totalAterizajesIniciales = 0;
        Double totalHorasFoliadasDiurnas = this.foliadoService.obtenerTotalDeHorasFoliadaDiurnas(user);
        Double totalHorasFoliadaNocturnas = this.foliadoService.obtenerTotalDeHorasFoliadaNocturna(user);
        Double totalHorasFoliadas = totalHorasFoliadasDiurnas + totalHorasFoliadaNocturnas;

        Integer totalAterrizajesFoliados = this.foliadoService.obtenerTotalDeAterrizajeFoliada(user);
        Foliado foliado = null;

        Page<VueloRealizado> pageHorasFoliadas = null;
        String fechafoliado = null;
        if ( params.get("id") == null || params.get("id").toString().equals("sinId")  ) {
            pageHorasFoliadas = this.vueloRealizadoService.buscarHorasNofoliadas(user, pageRequest);
            model.addAttribute("idFoliado", "sinId");

        } else {
            //  fechafoliado=params.get("fechafoliado").toString();
            //LocalDate fechaFoliadoAbuscar=LocalDate.parse(fechafoliado);
            Long id = Long.parseLong(params.get("id").toString());
            foliado = this.foliadoService.buscarFoliadoPorId(id);
            fechafoliado = foliado.getFechaFoliado().toString();
            pageHorasFoliadas = this.vueloRealizadoService.buscarHorasfoliadas(user, foliado, pageRequest);
            model.addAttribute("idFoliado", foliado.getId());
             totalHorasFoliadasDiurnas = foliado.obtenerTotalDeHoraDiurnasFoliadas();
             totalHorasFoliadaNocturnas = foliado.obtenerTotalDeHoraNocturnasFoliadas();
             totalHorasFoliadas = totalHorasFoliadasDiurnas + totalHorasFoliadaNocturnas;

             totalAterrizajesFoliados = foliado.obtenerTotalAterrizajesFoliadas();


        }


        int totalPage = pageHorasFoliadas.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }
        List<VueloRealizado> vuelosRealizados = pageHorasFoliadas.getContent();
        model.addAttribute("vuelosRealizados", vuelosRealizados);
        model.addAttribute("foliado", foliado);

        model.addAttribute("current", page + 1);
        model.addAttribute("next", page + 2);
        model.addAttribute("prev", page);
        model.addAttribute("last", totalPage);


        //Double totalDeHorasFoliadas = this.foliadoService.obtenerTotalDeHorasFoliada(user);




        model.addAttribute("user", user);

        model.addAttribute("fechafoliado", fechafoliado);

        model.addAttribute("totalHorasFoliadasDiurnas", totalHorasFoliadasDiurnas);
        model.addAttribute("totalHorasFoliadaNocturnas", totalHorasFoliadaNocturnas);
        model.addAttribute("totalHorasFoliadas", totalHorasFoliadas);
        model.addAttribute("totalAterrizajesFoliados", totalAterrizajesFoliados);
        model.addAttribute("totalTvDiurnoIniciales", totalTvDiurnoIniciales);
        model.addAttribute("totalTvNocturnaIniciales", totalTvNocturnaIniciales);
        model.addAttribute("totalTvIniciales", totalTvIniciales);
        model.addAttribute("totalAterizajesIniciales", totalAterizajesIniciales);

        return "muestradehorasafoliar";
    }

    @GetMapping("/foliado/generarfoliado")
    public String generarfoliado(@RequestParam Map<String, Object> params, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());
        int cantidadDeRegitrosPorPaginas = Integer.MAX_VALUE;

        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
        Sort sort = Sort.by("fechahoraDespegue");
        PageRequest pageRequest = PageRequest.of(page, cantidadDeRegitrosPorPaginas, sort);


        //Page<VueloRealizado> pageHorasFoliadas= this.vueloRealizadoService.buscarHorasNofoliadas(user,pageRequest);

        //   List<VueloRealizado> vuelosRealizados = pageHorasFoliadas.getContent();

        Foliado foliado = null;
        try {
            foliado = this.foliadoService.generarNuevoFoliado(user, pageRequest);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/tripulante/foliados/verhorasfoliadas";
        }


        return "redirect:/tripulante/foliados/verhorasfoliadas?id=" + foliado.getId();
    }


}
